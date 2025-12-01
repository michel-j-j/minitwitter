package tpindividual.minitwitter.entity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_name")
})
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "user_name", nullable = false, unique = true)
    @Size(min = 5, max = 25, message = "Username must be between 5 and 25 characters")
    private String userName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tweet> tweets = new ArrayList<>();

    protected User() {
        // Constructor sin argumentos para JPA
    }

    public User(String userName) {
        validateUserName(userName);
        this.userName = userName;
    }

    private void validateUserName(String userName) {
        if (userName == null || userName.length() < 5 || userName.length() > 25) {
            throw new IllegalArgumentException("Username must be between 5 and 25 characters");
        }
    }

    public Tweet createTweet(String text) {
        Tweet tweet = new Tweet(text, this);
        tweets.add(tweet);
        return tweet;
    }

    public Retweet createRetweet(Tweet originalTweet) {
        if (originalTweet.getUser().equals(this)) {
            throw new IllegalArgumentException("Cannot retweet your own tweet");
        }
        Retweet retweet = new Retweet(originalTweet, this);
        tweets.add(retweet);
        return retweet;
    }

    public List<Tweet> getTweets() {
        return new ArrayList<>(tweets);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}