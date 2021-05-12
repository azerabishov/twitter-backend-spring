package org.azerabshv.services.impl;

import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.response.MessageResponse;
import org.azerabshv.dto.response.SearchResponse;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.enums.TweetTypeEnum;
import org.azerabshv.exception.RecordNotFoundException;
import org.azerabshv.mappers.MapStructMapper;
import org.azerabshv.models.Tweet;
import org.azerabshv.models.User;
import org.azerabshv.repository.tweet.TweetRepository;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.services.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final AuthService authService;

    private final FileService fileService;

    private final FollowService followService;

    private final TweetRepository tweetRepository;

    private final MapStructMapper mapstructMapper;

    private final UserRepository userRepository;

    @Override
    public Tweet getTweet(long tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public void createTweet(MultipartFile contentFile, String content) {
        User user = authService.getAuthenticatedUser();
        String filename = fileService.save(contentFile);
        Tweet tweet = new Tweet( filename, content, new Date(), user );
        tweetRepository.save(tweet);
    }

    @Override
    public void deleteTweet(Long tweetId) {
        Tweet tweet = getTweet(tweetId);
        tweetRepository.delete(tweet);
    }

    @Override
    public TweetDetailDto getTweetDetail(long tweetId) {
        return tweetRepository.findById(tweetId)
                .map(mapstructMapper::tweetToTweetDetailDto)
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public List<TweetDetailDto> getTweetReplies(long tweetId, int pageNo) {
        Pageable paging = PageRequest.of(pageNo, 20);
        List<Tweet> tweets = tweetRepository.findByReplyTo(tweetId, paging);
        return mapstructMapper.tweetsToTweetDetailsDto(tweets);
    }

    @Override
    public List<TweetDetailDto> getTweetQuotes(long tweetId, int pageNo) {
        Pageable paging = PageRequest.of(pageNo, 20);
        List<Tweet> tweets = tweetRepository.findByQuoteTo(tweetId, paging);
        return mapstructMapper.tweetsToTweetDetailsDto(tweets);

    }


    @Override
    public List<UserDetailDto> getTweetLikes(long tweetId) {
        Tweet tweet = getTweet(tweetId);
        List<User> likedUsers = tweet.getLikedUsers();
        return mapstructMapper.usersToUserDetailsDto(likedUsers);
     }

    @Override
    public List<UserDetailDto> getTweetRetweets(long tweetId) {
        Tweet tweet = getTweet(tweetId);
        List<User> retweetedUsers = tweet.getRetweetedUsers();
        return mapstructMapper.usersToUserDetailsDto(retweetedUsers);
    }



    @Override
    public void replyTweet(long tweetId, MultipartFile contentFile, String content) {
        Tweet tweet = getTweet(tweetId);
        User user = authService.getAuthenticatedUser();
        String filename = fileService.save(contentFile);
        tweet.setReplyCount(tweet.getReplyCount()+1);
        Tweet replyTweet = new Tweet( filename, content, new Date(), user, tweetId, null );
        tweetRepository.save(replyTweet);
    }

    @Override
    public void retweetTweet(long tweetId) {
        Tweet tweet = getTweet(tweetId);
        User user = authService.getAuthenticatedUser();
        tweet.setRetweetCount(tweet.getRetweetCount()+1);
        user.getRetweets().add(tweet);
        userRepository.save(user);
        tweetRepository.save(tweet);
    }

    @Override
    public void undoRetweet(long tweetId) {
        User user = authService.getAuthenticatedUser();
        Tweet tweet = getTweet(tweetId);
        user.getRetweets().remove(tweet);
        userRepository.save(user);
    }

    @Override
    public void likeTweet(long tweetId) {
        Tweet tweet = getTweet(tweetId);
        User user = authService.getAuthenticatedUser();
        tweet.setLikeCount(tweet.getLikeCount()+1);
        user.getLikes().add(tweet);
        userRepository.save(user);
        tweetRepository.save(tweet);
    }

    @Override
    public void undoLike(long tweetId) {
        User user = authService.getAuthenticatedUser();
        Tweet tweet = getTweet(tweetId);
        user.getLikes().remove(tweet);
        userRepository.save(user);
    }

    @Override
    public void quoteTweet(long tweetId, MultipartFile contentFile, String content) {
        Tweet tweet = getTweet(tweetId);
        User user = authService.getAuthenticatedUser();
        String filename = fileService.save(contentFile);
        tweet.setQuoteCount(tweet.getQuoteCount()+1);
        Tweet quoteTweet = new Tweet( filename, content, new Date(), user, null, tweetId );
        tweetRepository.save(quoteTweet);
    }

    @Override
    public List<TweetDetailDto> getTweetsByUserPreference() {
        List<Long> friendsIds = followService.getFriendsIds();
        List<Tweet> tweets = tweetRepository.getAllByUserIds(friendsIds);
        return mapstructMapper.tweetsToTweetDetailsDto(tweets);
    }

    @Override
    public SearchResponse search(String searchKey) {
        List<Tweet> tweets = tweetRepository.findTweetByContent(searchKey);
        List<User> users = userRepository.findUserByContent(searchKey);
        List<TweetDetailDto> tweetDetails = mapstructMapper.tweetsToTweetDetailsDto(tweets);
        List<UserDetailDto> userProfiles = mapstructMapper.usersToUserDetailsDto(users);
        return SearchResponse.builder()
                .userProfiles(userProfiles)
                .tweets(tweetDetails)
                .build();
    }

    @Override
    public List<Tweet> getTweetByUser(int offset) {
        long userId = authService.getAuthenticatedUserId();
        return tweetRepository.findByUser(userId, 20, offset);
    }

    @Override
    public List<Tweet> getTweetByUserLikes(int offset) {
        long userId = authService.getAuthenticatedUserId();
        return tweetRepository.findTweetsByUserLikes(userId, 20, offset);
    }

    @Override
    public List<Tweet> getTweetWithMedia(int offset) {
        long userId = authService.getAuthenticatedUserId();
        return tweetRepository.findUserTweetByType(userId, 20, offset, TweetTypeEnum.TWEET_WITH_MEDIA.toString());
    }

    @Override
    public List<Tweet> getTweetFromBookmarks(int offset) {
        long userId = authService.getAuthenticatedUserId();
        return tweetRepository.findTweetsFromBookmarks(userId, 20, offset);
    }
}
