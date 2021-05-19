package org.azerabshv.services.impl;

import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.request.UpdatePasswordRequest;
import org.azerabshv.dto.request.UpdateProfileRequest;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.dto.response.UserProfileDto;
import org.azerabshv.exception.InvalidUsernameOrPasswordException;
import org.azerabshv.exception.NotAllowedException;
import org.azerabshv.exception.UserNotFoundException;
import org.azerabshv.mappers.MapStructMapper;
import org.azerabshv.models.Follow;
import org.azerabshv.models.Tweet;
import org.azerabshv.models.User;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.services.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final AuthService authService;

    private final FileService fileService;

    private final TweetService tweetService;

    private final FollowService followService;

    private final PasswordEncoder encoder;

    private final MapStructMapper mapstructMapper;



    @Override
    public UserProfileDto getUserProfile(Long userId) {
        User authenticatedUser = authService.getAuthenticatedUser();
        return userRepository.findById(userId)
                .map(user -> checkUserAllowedToSeeTweet(user, authenticatedUser))
                .map(mapstructMapper::userToUserProfileDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void updateUserProfile(UpdateProfileRequest updateProfileRequest) {
        long userId = authService.getAuthenticatedUserId();
        userRepository.findById(userId)
                .map(user -> updateUserPorfile(user, updateProfileRequest))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void lockProfile() {
        User user = authService.getAuthenticatedUser();
        user.setProtected(true);
        userRepository.save(user);
    }

    @Override
    public void unlockProfile() {
        User user = authService.getAuthenticatedUser();
        user.setProtected(false);
        userRepository.save(user);
    }

    @Override
    public void updateUserPassword(UpdatePasswordRequest passwordRequest) {
        long userId = authService.getAuthenticatedUserId();
        userRepository.findById(userId)
                .map(user -> updateUserPassword(user, passwordRequest))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<TweetDetailDto> getAllTweets() {
        List<Tweet> tweets = tweetService.getTweetByUser();
        return mapstructMapper.tweetsToTweetDetailsDto(tweets);
    }

    @Override
    public List<TweetDetailDto> getLikedTweets() {
        List<Tweet> likedTweets = tweetService.getTweetByUserLikes();
        return mapstructMapper.tweetsToTweetDetailsDto(likedTweets);
    }

    @Override
    public List<TweetDetailDto> getTweetsWithMedia() {
        List<Tweet> tweetsWithMedia = tweetService.getTweetWithMedia();
        return mapstructMapper.tweetsToTweetDetailsDto(tweetsWithMedia);
    }

    @Override
    public void addTweetToBookmark(long tweetId) {
        User user = authService.getAuthenticatedUser();
        Tweet tweet = tweetService.getTweet(tweetId);
        user.getBookmarks().add(tweet);
        userRepository.save(user);
    }

    @Override
    public void removeTweetFromBookmark(long tweetId) {
        User user = authService.getAuthenticatedUser();
        Tweet tweet = tweetService.getTweet(tweetId);
        user.getBookmarks().remove(tweet);
        userRepository.save(user);
    }

    @Override
    public List<TweetDetailDto> getUserBookmarks() {
        List<Tweet> bookmarkedTweets = tweetService.getTweetFromBookmarks();
        return mapstructMapper.tweetsToTweetDetailsDto(bookmarkedTweets);
    }



    private User updateUserPorfile(User user, UpdateProfileRequest updateProfileRequest) {
        user.setScreenName(updateProfileRequest.getScreenName());
        user.setBio(updateProfileRequest.getBio());
        user.setLocation(updateProfileRequest.getLocation());
        user.setWebsite(updateProfileRequest.getWebsite());
        user.setBirthdate(updateProfileRequest.getBirthdate());
        if (!updateProfileRequest.getAvatarUrl().getOriginalFilename().isEmpty()) {
            String filename = fileService.save(updateProfileRequest.getAvatarUrl());
            user.setAvatarUrl(filename);
        }
        if (!updateProfileRequest.getProfileBackgroundImageUrl().getOriginalFilename().isEmpty()) {
            String filename = fileService.save(updateProfileRequest.getProfileBackgroundImageUrl());
            user.setAvatarUrl(filename);
        }
        return userRepository.save(user);
    }

    private User updateUserPassword(User user, UpdatePasswordRequest passwordRequest) {
        if (encoder.matches(passwordRequest.getCurrentPass(), user.getPassword())){
            if (passwordRequest.getNewPassword().equals(passwordRequest.getNewPasswordConfirmation())) {
                user.setPassword(encoder.encode(passwordRequest.getNewPassword()));
                return userRepository.save(user);
            }else {
                throw new InvalidUsernameOrPasswordException();
            }
        } else {
            throw new InvalidUsernameOrPasswordException();
        }
    }


    private User checkUserAllowedToSeeTweet(User user, User authenticatedUser) {
        if(user.isProtected()) {
            followService.checkRecordExist(user.getUserId(), authenticatedUser.getUserId())
                    .orElseThrow(NotAllowedException::new);
            return user;
        } else{
            return user;
        }

    }




}
