package tpindividual.minitwitter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tpindividual.minitwitter.entity.dto.CreateTweetRequest;
import tpindividual.minitwitter.entity.dto.CreateRetweetRequest;
import tpindividual.minitwitter.entity.dto.TweetResponse;
import tpindividual.minitwitter.entity.model.Retweet;
import tpindividual.minitwitter.entity.model.Tweet;
import tpindividual.minitwitter.entity.model.User;
import tpindividual.minitwitter.respository.TweetRepository;
import tpindividual.minitwitter.respository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public TweetResponse createTweet(CreateTweetRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Tweet tweet = user.createTweet(request.getText());
        tweet = tweetRepository.save(tweet);

        return convertToResponse(tweet);
    }

    public TweetResponse createRetweet(CreateRetweetRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Tweet originalTweet = tweetRepository.findById(request.getOriginalTweetId())
                .orElseThrow(() -> new IllegalArgumentException("Original tweet not found"));

        Retweet retweet = user.createRetweet(originalTweet);
        retweet = tweetRepository.save(retweet);

        return convertToResponse(retweet);
    }

    @Transactional(readOnly = true)
    public Page<TweetResponse> getAllOriginalTweets(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tweetRepository.findAllOriginalTweets(pageable)
                .map(this::convertToResponse);
    }

    @Transactional(readOnly = true)
    public List<TweetResponse> getUserTweets(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Pageable pageable = PageRequest.of(page, size);
        return tweetRepository.findByUserOrderByCreatedAtDesc(user, pageable)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private TweetResponse convertToResponse(Tweet tweet) {
        TweetResponse response = new TweetResponse();
        response.setId(tweet.getId());
        response.setText(tweet.getText());
        response.setCreatedAt(tweet.getCreatedAt());
        response.setRetweet(tweet.isRetweet());

        if (tweet.isRetweet()) {
            Retweet retweet = (Retweet) tweet;
            response.setRetweetedAt(retweet.getRetweetedAt());
            response.setRetweetedByUserName(retweet.getUser().getUserName());
            response.setRetweetedByUserId(retweet.getUser().getId());
            response.setOriginalUserName(retweet.getOriginalUser().getUserName());
            response.setOriginalUserId(retweet.getOriginalUser().getId());
            response.setOriginalCreatedAt(retweet.getOriginalCreatedAt());
            response.setUserName(retweet.getOriginalUser().getUserName());
            response.setUserId(retweet.getOriginalUser().getId());
        } else {
            response.setUserName(tweet.getUser().getUserName());
            response.setUserId(tweet.getUser().getId());
        }

        return response;
    }
}

