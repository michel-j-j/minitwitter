package tpindividual.minitwitter.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import tpindividual.minitwitter.entity.model.User;
import java.time.LocalDateTime;

@Getter
@Entity
@DiscriminatorValue("RETWEET")
public class Retweet extends Tweet {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_tweet_id")
    private Tweet originalTweet;

    @Column(name = "retweeted_at")
    private LocalDateTime retweetedAt;

    protected Retweet() {
        // Constructor sin argumentos para JPA
        super();
    }

    public Retweet(Tweet originalTweet, User user) {
        super(originalTweet.getText(), user);
        if (originalTweet.getUser().equals(user)) {
            throw new IllegalArgumentException("Cannot retweet your own tweet");
        }
        this.originalTweet = originalTweet;
        this.retweetedAt = LocalDateTime.now();
    }

    @Override
    public boolean isRetweet() {
        return true;
    }

    public User getOriginalUser() {
        return originalTweet.getUser();
    }

    public LocalDateTime getOriginalCreatedAt() {
        return originalTweet.getCreatedAt();
    }
}