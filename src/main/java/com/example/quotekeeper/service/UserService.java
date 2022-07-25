package com.example.quotekeeper.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.quotekeeper.aws.Filestore;
import com.example.quotekeeper.model.Quote;
import com.example.quotekeeper.model.QuoteForm;
import com.example.quotekeeper.model.User;
import com.example.quotekeeper.payload.MessageResponse;
import com.example.quotekeeper.repository.UserRepo;
import com.example.quotekeeper.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import static org.apache.http.entity.ContentType.*;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {
    private final Filestore filestore;
    private final UserRepo userRepo;
    private final QuoteService quoteService;
    private final AmazonS3 s3;

    @Autowired
    public UserService(UserRepo userRepo, QuoteService quoteService, AmazonS3 s3, Filestore filestore){
        this.userRepo=userRepo;
        this.quoteService=quoteService;
        this.s3=s3;
        this.filestore=filestore;
    }

    public Optional<User> getUserById(Long id){
        return userRepo.findById(id);
    }

    public Optional<User> findByEmail(String email){return userRepo.findByEmail(email);}

    @Transactional
    public ResponseEntity<?> addQuote(QuoteForm quoteForm) {
        try{
            // Logged in user
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = getUserById(userDetails.getId()).get();

            // New quote info submitted by user
            Long quoteId = quoteForm.getQuoteId();
            String quote = quoteForm.getQuote();
            String sourceType = quoteForm.getSourceType();
            String sourceTitle = quoteForm.getSourceTitle();
            String sourceAuthor = quoteForm.getSourceAuthor();

            // If quote id is provided, updated existing quote. Else, add a new quote
            if(quoteId==-1){
                Quote newQuote = new Quote(quote, user, sourceType, sourceTitle, sourceAuthor);
                Quote savedQuote = quoteService.save(newQuote);
                userRepo.likeQuote(user.getUserId(), savedQuote.getQuoteId());
            }else{
                quoteService.updateQuote(quoteId, quote, sourceType, sourceTitle, sourceAuthor);
            }

            return ResponseEntity.ok(new MessageResponse("OK"));

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> likeQuote(Long quoteId){
        try{
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetails.getId();
            userRepo.likeQuote(userId, quoteId);
            return ResponseEntity.ok(new MessageResponse("OK"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(e.getMessage()));
        }
    }

    @Transactional
    public ResponseEntity<?> unlikeQuote(Long quote_id) {
        try{
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = userDetails.getId();
            userRepo.unlikeQuote(userId, quote_id);
            return ResponseEntity.ok(new MessageResponse("OK"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(e.getMessage()));
        }
    }

    public ResponseEntity<?> changeFavoriteQuote(Long quote_id) {
        try{
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long user_id = userDetails.getId();
            User user = userRepo.findById(user_id).get();

            // If quote_id == -1, user wants to remove the favorite quote. Else, user wants to change the favorite quote
            if(quote_id==-1){
                user.setFavoriteQuote(null);
                userRepo.save(user);
            }else{
                Quote quote = quoteService.findById(quote_id).get();
                user.setFavoriteQuote(quote);
                userRepo.save(user);

                try{
                    likeQuote(quote_id);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            return ResponseEntity.ok(new MessageResponse("OK"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(e.getMessage()));
        }
    }

    public ResponseEntity<?> uploadProfileImage(MultipartFile file) {
        try{
            // Check if image is not empty
            isEmpty(file);

            // Check if file is an image
            isImage(file);

            // Grab some metadata if any
            Map<String, String> metadata = extractMetadata(file);

            // Store the image in S3 and update database with S3 image link
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long user_id = userDetails.getId();
            User user = userRepo.findById(user_id).get();

            String BUCKET = "quote-keeper";
            String path = String.format("%s", BUCKET);

            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String fileName = String.format("%s.%s", user_id, extension);

            filestore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            String fileLink = s3.getUrl(BUCKET, fileName).toString();
            user.setProfileImageURL(fileLink);
            userRepo.save(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok(new MessageResponse("OK"));
    }


    /******************* Helper methods **************************/
    private void isEmpty(MultipartFile file){
        if(file.isEmpty()){
            throw new IllegalStateException("Cannot upload empty file.");
        }
    }

    private void isImage(MultipartFile file){
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType()).contains(file.getContentType())){
            throw new IllegalStateException("File must be an image.");
        }
    }

    private Map<String, String> extractMetadata(MultipartFile file){
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        metadata.put("ACL", "public-read");
        return metadata;
    }
}
