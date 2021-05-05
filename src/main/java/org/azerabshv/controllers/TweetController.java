package org.azerabshv.controllers;

import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.services.TweetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/v1/tweet")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void createTweet(@RequestParam(value = "mediaUrl", required = false) MultipartFile contentFile, @RequestParam("content") String content){
        tweetService.createTweet(contentFile, content);
    }

    @GetMapping("/get/{id}")
    public TweetDetailDto getTweet(@PathVariable("id") long id){
        return tweetService.getTweetDetail(id);
    }

    @GetMapping("/get/{id}/replies")
    public List<TweetDetailDto> getTweetResponses(@PathVariable("id") Long id, @RequestParam int page){
        return tweetService.getTweetReplies(id, page);
    }

    @GetMapping("/get/{id}/likes")
    public List<UserDetailDto> getLikes(@PathVariable("id") Long id){
        return tweetService.getTweetLikes(id);
    }

    @GetMapping("/get/{id}/retweets")
    public List<UserDetailDto> getRetweets(@PathVariable("id") Long id){
        return tweetService.getTweetRetweets(id);
    }

    @GetMapping("/get/{id}/quotes")
    public List<TweetDetailDto> getQuotes(@PathVariable("id") Long id, @RequestParam int page){
        return tweetService.getTweetQuotes(id, page);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/reply")
    public void responseToTweet(
            @RequestParam(value = "mediaUrl", required = false) MultipartFile contentFile,
            @RequestParam(value = "content") String content,
            @PathVariable("id") Long tweetId){
        tweetService.replyTweet(tweetId, contentFile, content);
    }


    @GetMapping("like/{id}")
    public ResponseEntity<?> likeTweet(@PathVariable("id") long tweetId) {
        return tweetService.likeTweet(tweetId);
    }

    @GetMapping("retweet/{id}")
    public ResponseEntity<?> retweetTweet(@PathVariable("id") long tweetId) {
        return tweetService.retweetTweet(tweetId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("quote/{id}")
    public void likeTweet(
            @RequestParam(value = "mediaUrl", required = false) MultipartFile contenFile,
            @RequestParam(value = "content") String content,
            @PathVariable("id") Long tweetId){
        tweetService.quoteTweet(tweetId, contenFile, content);
    }
}
