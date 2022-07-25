package com.example.quotekeeper.controller;

import com.example.quotekeeper.payload.QuoteResponse;
import com.example.quotekeeper.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/quotes")
public class QuoteController {
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService){this.quoteService=quoteService;}

    // Get all quotes
    @GetMapping("/all")
    public List<QuoteResponse> getAllQuotes(){
        return quoteService.findAllQuotes();
    }

    // Get User's quotes
    @GetMapping("/user-quotes/{userid}")
    public List<QuoteResponse> getUserQuotes(@PathVariable Long userid) {return quoteService.findUserQuotes(userid);}

     // Get a random quote
    @GetMapping("/random")
    public QuoteResponse getRandomQuote(){return quoteService.findRandomQuote();}
}
