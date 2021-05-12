package org.azerabshv.services;

import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.models.Tweet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TweetService {
    Tweet getTweet(long tweetId);
    void createTweet(MultipartFile contentFile, String content);
    void deleteTweet(Long tweetId);
    TweetDetailDto getTweetDetail(long tweetId);
    List<TweetDetailDto> getTweetReplies(long tweetId, int pageNo);
    List<TweetDetailDto> getTweetQuotes(long tweetId, int pageNo);
    List<UserDetailDto> getTweetLikes(long tweetId);
    List<UserDetailDto> getTweetRetweets(long tweetId);
    void replyTweet(long tweetId, MultipartFile contentFile, String content);
    void retweetTweet(long tweetId);
    void undoRetweet(long tweetId);
    void likeTweet(long tweetId);
    void undoLike(long tweetId);
    void quoteTweet(long tweetId, MultipartFile contentFile, String content);
    List<TweetDetailDto> getTweetsByUserPreference();
    List<Tweet> getTweetByUser(int offset);
    List<Tweet> getTweetByUserLikes(int offset);
    List<Tweet> getTweetWithMedia(int offset);
    List<Tweet> getTweetFromBookmarks(int offset);
}
