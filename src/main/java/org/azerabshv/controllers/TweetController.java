package org.azerabshv.controllers;

import org.azerabshv.dto.response.MessageResponse;
import org.azerabshv.dto.response.TweetDto;
import org.azerabshv.models.Tweet;
import org.azerabshv.models.User;
import org.azerabshv.repository.tweet.TweetRepository;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.security.UserDetailsImpl;
import org.azerabshv.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/v1/tweet")
public class TweetController {

    @Autowired
    FileService fileService;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createTweet(@RequestParam("mediaUrl") MultipartFile file, @RequestParam("content") String content){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        Optional<User> user = userRepository.findById( userPrincipal.getId());
        String message = "";
        try {
            fileService.save(file);
            Tweet tweet = new Tweet(
                    file.getOriginalFilename(),
                    content,
                    new Date(),
                    user.get()
            );
            tweetRepository.save(tweet);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @GetMapping("/get/:id")
    public ResponseEntity<?> getTweet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        Optional<User> user = userRepository.findById( userPrincipal.getId());
        List<TweetDto> tweets = new ArrayList<>();
        for (Tweet tweet : user.get().getTweets()) {
            tweets.add(new TweetDto(tweet.getMediaUrl(), tweet.getContent(), tweet.getCreatedAt()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(tweets);
    }

    @GetMapping("/get/responses")
    public ResponseEntity<?> getTweetResponses(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        Optional<User> user = userRepository.findById( userPrincipal.getId());
        List<TweetDto> tweets = new ArrayList<>();
        for (Tweet tweet : user.get().getTweets()) {
            tweets.add(new TweetDto(tweet.getMediaUrl(), tweet.getContent(), tweet.getCreatedAt()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(tweets);
    }
//    @GetMapping("/get")
//    public ResponseEntity<?> getTweets(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
//        Optional<User> user = userRepository.findById( userPrincipal.getId());
//        List<TweetDto> tweets = new ArrayList<>();
//        for (Tweet tweet : user.get().getTweets()) {
//            tweets.add(new TweetDto(tweet.getMediaUrl(), tweet.getContent(), tweet.getCreatedAt()));
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(tweets);
//    }

    @PostMapping("/reply")
    public ResponseEntity<?> responseToTweet(
            @RequestParam(value = "mediaUrl", required = false) MultipartFile file,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "replyTo") Long replyTo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        Optional<User> user = userRepository.findById( userPrincipal.getId());
        String message = "";
        String filename;
        try {
            if(file != null) {
                fileService.save(file);
                filename = file.getOriginalFilename();
            }else{
                filename = null;
            }
            Tweet tweet = new Tweet(
                    filename,
                    content,
                    new Date(),
                    user.get(),
                    replyTo
            );

            tweetRepository.save(tweet);
            message = "Uploaded the file successfully: " + filename;
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: !";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }





}
