package org.azerabshv.services.impl;

import org.azerabshv.dto.response.MessageResponse;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.exception.RecordNotFoundException;
import org.azerabshv.exception.UserNotFoundException;
import org.azerabshv.models.Tweet;
import org.azerabshv.models.User;
import org.azerabshv.repository.tweet.TweetRepository;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.services.FileService;
import org.azerabshv.services.TweetService;
import org.azerabshv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {
    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    @Autowired
    TweetRepository tweetRepository;

    
    @Autowired
    UserRepository userRepository;

    @Override
    public void createTweet(MultipartFile contentFile, String content, long userId) {
        User user = userService.getUser(userId);
        String filename;
        if(contentFile != null) {
            filename = fileService.save(contentFile);
        }else{
            filename = null;
        }
        Tweet tweet = new Tweet( filename, content, new Date(), user );
        tweetRepository.save(tweet);
    }

    @Override
    public TweetDetailDto getTweetDetail(long tweetId) {
        return tweetRepository.findById(tweetId)
                .map(tweet -> TweetDetailDto.builder()
                        .screenName(tweet.getUser().getScreenName())
                        .username(tweet.getUser().getUsername())
                        .avatarUrl(tweet.getUser().getAvatarUrl())
                        .content(tweet.getContent())
                        .mediaUrl(tweet.getMediaUrl())
                        .createdAt(tweet.getCreatedAt())
                        .likeCount(tweet.getLikeCount())
                        .retweetCount(tweet.getRetweetCount())
                        .quoteCount(tweet.getQuoteCount())
                        .replyCount(tweet.getReplyCount())
                        .build())
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public List<TweetDetailDto> getTweetReplies(long tweetId) {
        List<Tweet> tweets = tweetRepository.findByReplyTo(tweetId);
        List<TweetDetailDto> replies = new ArrayList<>();
        for (Tweet tweet: tweets) {
            replies.add(TweetDetailDto.builder()
                            .screenName(tweet.getUser().getScreenName())
                            .username(tweet.getUser().getUsername())
                            .avatarUrl(tweet.getUser().getAvatarUrl())
                            .content(tweet.getContent())
                            .mediaUrl(tweet.getMediaUrl())
                            .createdAt(tweet.getCreatedAt())
                            .likeCount(tweet.getLikeCount())
                            .retweetCount(tweet.getRetweetCount())
                            .quoteCount(tweet.getQuoteCount())
                            .replyCount(tweet.getReplyCount())
                            .build());
        }

        return replies;

    }

    @Override
    public void replyTweet(long userId, long tweetId, MultipartFile contentFile, String content) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(RecordNotFoundException::new);
        User user = userService.getUser(userId);

        String filename;
        if(contentFile != null) {
            filename = fileService.save(contentFile);
        }else{
            filename = null;
        }

        tweet.setReplyCount(tweet.getReplyCount()+1);
        Tweet replyTweet = new Tweet( filename, content, new Date(), user, tweetId, null );
        tweetRepository.save(replyTweet);
    }

    @Override
    public ResponseEntity<?> retweetTweet(long userId, long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(RecordNotFoundException::new);
        User user = userService.getUser(userId);
        tweet.setRetweetCount(tweet.getRetweetCount()+1);
        user.getRetweets().add(tweet);
        userRepository.save(user);
        tweetRepository.save(tweet);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Success!"));

    }

    @Override
    public ResponseEntity<?> likeTweet(long userId, long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(RecordNotFoundException::new);
        User user = userService.getUser(userId);
        tweet.setLikeCount(tweet.getLikeCount()+1);
        user.getLikes().add(tweet);
        userRepository.save(user);
        tweetRepository.save(tweet);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Success!"));
    }

    @Override
    public void quoteTweet(long userId, long tweetId, MultipartFile contentFile, String content) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(RecordNotFoundException::new);
        User user = userService.getUser(userId);

        String filename;
        if(contentFile != null) {
            filename = fileService.save(contentFile);
        }else{
            filename = null;
        }
        tweet.setQuoteCount(tweet.getQuoteCount()+1);
        Tweet quoteTweet = new Tweet( filename, content, new Date(), user, null, tweetId );
        tweetRepository.save(quoteTweet);
    }
}
