package com.example.quotekeeper.controller;

import com.example.quotekeeper.model.QuoteForm;
import com.example.quotekeeper.model.User;
import com.example.quotekeeper.payload.UserResponse;
import com.example.quotekeeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id).get();
        return ResponseEntity.ok(new UserResponse(user.getUserId(), user.getName(), user.getFavoriteQuote(), user.getProfileImageURL()));
    }

    // Add quote
    @PostMapping("/add")
    public ResponseEntity<?> addQuote(@RequestBody QuoteForm quoteForm){return userService.addQuote(quoteForm);}

    // User liked a quote
    @PostMapping("/like")
    public ResponseEntity<?> likeAQuote(@RequestBody Map<String, Long> quoteId){return userService.likeQuote(quoteId.get("quoteId"));}

    // User unliked a quote
    @PostMapping("/unlike")
    public ResponseEntity<?> unlike(@RequestBody Map<String, Long> quoteId){return userService.unlikeQuote(quoteId.get("quoteId"));}

    // Change favorite quote
    @PostMapping("/change-favorite-quote")
    public ResponseEntity<?> changeFavoriteQuote(@RequestBody Map<String, Long> quoteId){return userService.changeFavoriteQuote(quoteId.get("quoteId"));}

    // User change profile image
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadProfileImage(@RequestParam("file")MultipartFile file){
        return userService.uploadProfileImage(file);
    }

}
