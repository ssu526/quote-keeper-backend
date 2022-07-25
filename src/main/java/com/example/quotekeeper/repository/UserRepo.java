package com.example.quotekeeper.repository;

import com.example.quotekeeper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query(value = "INSERT INTO USERS_QUOTES (user_id, quote_id) VALUES (:user_id, :quote_id)", nativeQuery = true)
    @Modifying
    void likeQuote(@Param("user_id") Long userId, @Param("quote_id") Long quoteId);

    @Query(value = "DELETE FROM USERS_QUOTES uq WHERE uq.user_id=:user_id AND uq.quote_id=:quote_id", nativeQuery = true)
    @Modifying
    void unlikeQuote(@Param("user_id") Long user_id, @Param("quote_id") Long quote_id);
}
