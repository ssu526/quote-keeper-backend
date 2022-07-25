package com.example.quotekeeper.model;

public class QuoteForm {
    private Long quoteId;
    private String quote;
    private String sourceType;
    private String sourceTitle;
    private String sourceAuthor;

    public QuoteForm(Long quoteId, String quote, String sourceType, String sourceTitle, String sourceAuthor) {
        this.quoteId = quoteId;
        this.quote = quote;
        this.sourceType = sourceType;
        this.sourceTitle = sourceTitle;
        this.sourceAuthor = sourceAuthor;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public String getSourceAuthor() {
        return sourceAuthor;
    }

    public void setSourceAuthor(String sourceAuthor) {
        this.sourceAuthor = sourceAuthor;
    }

    @Override
    public String toString() {
        return "QuoteForm{" +
                "quoteId=" + quoteId +
                ", quote='" + quote + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", sourceTitle='" + sourceTitle + '\'' +
                ", sourceAuthor='" + sourceAuthor + '\'' +
                '}';
    }
}
