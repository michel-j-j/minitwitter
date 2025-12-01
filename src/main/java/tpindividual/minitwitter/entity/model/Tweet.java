package tpindividual.minitwitter.entity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import tpindividual.minitwitter.entity.model.User;

@Getter
@Entity
@Table(name = "tweets")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tweet_type", discriminatorType = DiscriminatorType.STRING)
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 280)
    @Size(min = 1, max = 280, message = "Tweet text must be between 1 and 280 characters")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected Tweet() {
        // Constructor sin argumentos para JPA
    }

    public Tweet(String text, User user) {
        validateText(text);
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.text = text;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    private void validateText(String text) {
        if (text == null || text.isEmpty() || text.length() > 280) {
            throw new IllegalArgumentException("Tweet text must be between 1 and 280 characters");
        }
    }

    public boolean isRetweet() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return Objects.equals(id, tweet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}