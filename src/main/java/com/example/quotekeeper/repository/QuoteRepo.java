package com.example.quotekeeper.repository;

import com.example.quotekeeper.model.Quote;
import com.example.quotekeeper.payload.QuoteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuoteRepo extends JpaRepository<Quote, Long> {
    @Query(value = "SELECT q.quote_id as quoteId, q.quote, q.source_type as sourceType, q.source_title as sourceTitle, " +
                    "q.source_author as sourceAuthor, u.user_id as userId, u.name, COUNT(uq.user_id) AS likes " +
                    "FROM QUOTE q " +
                    "LEFT JOIN USER u ON q.user_id = u.user_id " +
                    "LEFT JOIN USERS_QUOTES uq ON q.quote_id=uq.quote_id " +
                    "GROUP BY q.quote_id ORDER BY q.date_added DESC", nativeQuery = true)
    List<QuoteResponse> findAllQuotes();


    @Query(value = "SELECT q.quote_id as quoteId, q.quote, q.source_type as sourceType, q.source_title as sourceTitle, " +
                    "q.source_author as sourceAuthor, u.user_id as userId, u.name, COUNT(uq.user_id) AS likes " +
                    "FROM QUOTE q " +
                    "LEFT JOIN USER u ON q.user_id = u.user_id " +
                    "LEFT JOIN USERS_QUOTES uq ON q.quote_id=uq.quote_id " +
                    "WHERE q.quote_id = :id", nativeQuery = true)
    QuoteResponse findRandomQuote(@Param("id") long id);


    @Query(value = "SELECT quote_id from USERS_QUOTES WHERE user_id=:userId", nativeQuery = true)
    List<Long> findUserQuotes(@Param("userId") Long userId);

    @Query(value = "SELECT quote_id FROM QUOTE WHERE quote_id=(SELECT MAX(quote_id) FROM QUOTE)", nativeQuery = true)
    Long maxQuoteId();
}