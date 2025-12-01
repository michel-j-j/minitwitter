package tpindividual.minitwitter.entity.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class TweetResponse {

    private Long id;
    private String text;
    private String userName;
    private Long userId;

    private LocalDateTime createdAt;
    private boolean isRetweet;

    // Para retweets
    private LocalDateTime retweetedAt;
    private String retweetedByUserName;
    private Long retweetedByUserId;
    private String originalUserName;
    private Long originalUserId;
    private LocalDateTime originalCreatedAt;

    public TweetResponse() {}

    public boolean isRetweet() { return isRetweet; }
    public void setRetweet(boolean retweet) { isRetweet = retweet; }

}