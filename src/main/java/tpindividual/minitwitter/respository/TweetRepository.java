package tpindividual.minitwitter.respository;

import tpindividual.minitwitter.entity.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tpindividual.minitwitter.entity.model.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    // Obtener tweets que NO son retweets
    @Query("SELECT t FROM Tweet t WHERE TYPE(t) = Tweet ORDER BY t.createdAt DESC")
    Page<Tweet> findAllOriginalTweets(Pageable pageable);

    // Obtener todos los tweets de un usuario (incluyendo retweets)
    Page<Tweet> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}