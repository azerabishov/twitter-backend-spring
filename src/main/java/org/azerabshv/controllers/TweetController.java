package org.azerabshv.controllers;

import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.request.CreateTweetRequest;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.services.TweetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/tweet")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public void createTweet(@ModelAttribute CreateTweetRequest createTweetRequest){
        tweetService.createTweet(createTweetRequest.getMediaFile(), createTweetRequest.getContent());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable("id") long tweetId){
        tweetService.deleteTweet(tweetId);
    }

    @GetMapping("/{id}")
    public TweetDetailDto getTweet(@PathVariable("id") long tweetId){
        return tweetService.getTweetDetail(tweetId);
    }

    @GetMapping("/{id}/replies")
    public List<TweetDetailDto> getTweetResponses(@PathVariable("id") Long tweetId, @RequestParam int page){
        return tweetService.getTweetReplies(tweetId, page);
    }

    @GetMapping("/{id}/likes")
    public List<UserDetailDto> getLikes(@PathVariable("id") Long tweetId){
        return tweetService.getTweetLikes(tweetId);
    }

    @GetMapping("/{id}/retweets")
    public List<UserDetailDto> getRetweets(@PathVariable("id") Long tweetId){
        return tweetService.getTweetRetweets(tweetId);
    }

    @GetMapping("/{id}/quotes")
    public List<TweetDetailDto> getQuotes(@PathVariable("id") Long tweetId, @RequestParam int page){
        return tweetService.getTweetQuotes(tweetId, page);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/reply")
    public void responseToTweet( @ModelAttribute CreateTweetRequest createTweetRequest, @PathVariable("id") Long tweetId){
        tweetService.replyTweet(tweetId, createTweetRequest.getMediaFile(), createTweetRequest.getContent());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("{id}/like")
    public void likeTweet(@PathVariable("id") long tweetId) {
        tweetService.likeTweet(tweetId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("{id}/like/undo")
    public void undoLikeTweet(@PathVariable("id") long tweetId) {
        tweetService.undoLike(tweetId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("{id}/retweet")
    public void retweetTweet(@PathVariable("id") long tweetId) {
        tweetService.retweetTweet(tweetId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("{id}/retweet/undo")
    public void undoRetweetTweet(@PathVariable("id") long tweetId) {
        tweetService.undoRetweet(tweetId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/quote")
    public void likeTweet(@ModelAttribute CreateTweetRequest createTweetRequest, @PathVariable("id") Long tweetId){
        tweetService.quoteTweet(tweetId, createTweetRequest.getMediaFile(), createTweetRequest.getContent());
    }
}
