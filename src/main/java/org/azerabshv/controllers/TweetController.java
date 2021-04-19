package org.azerabshv.controllers;

import org.azerabshv.dto.response.MessageResponse;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.models.Tweet;
import org.azerabshv.models.User;
import org.azerabshv.repository.tweet.TweetRepository;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.security.UserDetailsImpl;
import org.azerabshv.services.FileService;
import org.azerabshv.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.temporal.Temporal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/tweet")
public class TweetController {
    @Autowired
    TweetService tweetService;

    @Autowired
    FileService fileService;

    @Autowired
    TweetRepository tweetRepository;


    @Autowired
    UserRepository userRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void createTweet(@RequestParam(value = "mediaUrl", required = false) MultipartFile contentFile, @RequestParam("content") String content){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        tweetService.createTweet(contentFile, content, userPrincipal.getId());
    }

    @GetMapping("/get/{id}")
    public TweetDetailDto getTweet(@PathVariable("id") long id){
        return tweetService.getTweetDetail(id);
    }

    @GetMapping("/get/{id}/replies")
    public List<TweetDetailDto> getTweetResponses(@PathVariable("id") Long id){
        return tweetService.getTweetReplies(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/reply")
    public void responseToTweet(
            @RequestParam(value = "mediaUrl", required = false) MultipartFile contentFile,
            @RequestParam(value = "content") String content,
            @PathVariable("id") Long tweetId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        tweetService.replyTweet(userPrincipal.getId(), tweetId, contentFile, content);
    }


    @GetMapping("like/{id}")
    public ResponseEntity<?> likeTweet(@PathVariable("id") long tweetId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        return tweetService.likeTweet(userPrincipal.getId(), tweetId);
    }

    @PostMapping("retweet/{id}")
    public ResponseEntity<?> retweetTweet(@PathVariable("id") long tweetId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        return tweetService.retweetTweet(userPrincipal.getId(), tweetId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("quote/{id}")
    public void likeTweet(
            @RequestParam(value = "mediaUrl", required = false) MultipartFile contenFile,
            @RequestParam(value = "content") String content,
            @PathVariable("id") Long tweetId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        tweetService.quoteTweet(userPrincipal.getId(), tweetId, contenFile, content);
    }
}
