package com.example.quotekeeper.payload;

public interface QuoteResponse {
    Long getQuoteId();
    String getQuote();
    Long getUserId();
    String getName();
    String getSourceType();
    String getSourceTitle();
    String getSourceAuthor();
    int getLikes();
}
