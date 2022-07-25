package com.example.quotekeeper.service;

import com.example.quotekeeper.model.Quote;
import com.example.quotekeeper.payload.QuoteResponse;
import com.example.quotekeeper.repository.QuoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuoteService {
    private final QuoteRepo quoteRepo;

    @Autowired
    public QuoteService(QuoteRepo quoteRepo){
        this.quoteRepo=quoteRepo;
    }

    public Optional<Quote> findById(Long quote_id) {
        return quoteRepo.findById(quote_id);
    }

    public List<QuoteResponse> findAllQuotes() {
        return quoteRepo.findAllQuotes();
    }

    public List<QuoteResponse> findUserQuotes(Long userId) {
        List<QuoteResponse> allQuotes = quoteRepo.findAllQuotes();
        List<Long> userLikedQuotes = quoteRepo.findUserQuotes(userId);

        List<QuoteResponse> result = allQuotes.stream().filter(q -> userLikedQuotes.contains(q.getQuoteId())).collect(Collectors.toList());

        return result;
    }

    public QuoteResponse findRandomQuote() {
        long maxQuoteId = quoteRepo.maxQuoteId() == null ? 0:quoteRepo.maxQuoteId();
        long randomQuoteId = (long)(Math.random()*maxQuoteId);
        QuoteResponse randomQuote = quoteRepo.findRandomQuote(randomQuoteId);

        while(randomQuote==null){
            randomQuoteId = (long)(Math.random()*maxQuoteId);
            randomQuote = quoteRepo.findRandomQuote(randomQuoteId);
        }
        return randomQuote;
    }

    public Quote save(Quote newQuote) {
        return quoteRepo.save(newQuote);
    }

    public void updateQuote(Long quoteId, String quote, String sourceType, String sourceTitle, String sourceAuthor) {
        Quote existingQuote = quoteRepo.findById(quoteId).get();
        existingQuote.setQuote(quote);
        existingQuote.setSourceAuthor(sourceAuthor);
        existingQuote.setSourceType(sourceType);
        existingQuote.setSourceTitle(sourceTitle);
        quoteRepo.save(existingQuote);
    }
}
