package tpindividual.minitwitter.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpindividual.minitwitter.entity.dto.CreateRetweetRequest;
import tpindividual.minitwitter.entity.dto.CreateTweetRequest;
import tpindividual.minitwitter.entity.dto.TweetResponse;
import tpindividual.minitwitter.service.TweetService;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
@CrossOrigin(origins = "*")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public ResponseEntity<TweetResponse> createTweet(@Valid @RequestBody CreateTweetRequest request) {
        try {
            TweetResponse response = tweetService.createTweet(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/retweet")
    public ResponseEntity<TweetResponse> createRetweet(@Valid @RequestBody CreateRetweetRequest request) {
        try {
            TweetResponse response = tweetService.createRetweet(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<TweetResponse>> getAllOriginalTweets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TweetResponse> tweets = tweetService.getAllOriginalTweets(page, size);
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TweetResponse>> getUserTweets(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        try {
            List<TweetResponse> tweets = tweetService.getUserTweets(userId, page, size);
            return ResponseEntity.ok(tweets);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}