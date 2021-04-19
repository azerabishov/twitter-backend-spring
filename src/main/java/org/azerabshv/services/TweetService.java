package org.azerabshv.services;

import org.azerabshv.dto.response.TweetDetailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TweetService {
    void createTweet(MultipartFile contentFile, String content, long userId);
    TweetDetailDto getTweetDetail(long tweetId);
    List<TweetDetailDto> getTweetReplies(long tweetId);
    void replyTweet(long userId, long tweetId, MultipartFile contentFile, String content);
    ResponseEntity<?> retweetTweet(long userId, long tweetId);
    ResponseEntity<?> likeTweet(long userId, long tweetId);
    void quoteTweet(long userId, long tweetId, MultipartFile contentFile, String content);

}
