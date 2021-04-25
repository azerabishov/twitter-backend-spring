package org.azerabshv.services.impl;

import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.request.UpdatePasswordRequest;
import org.azerabshv.dto.request.UpdateProfileRequest;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.dto.response.UserProfileDto;
import org.azerabshv.exception.InvalidUsernameOrPasswordException;
import org.azerabshv.exception.UserNotFoundException;
import org.azerabshv.mappers.MapStructMapper;
import org.azerabshv.models.Tweet;
import org.azerabshv.models.User;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.services.AuthService;
import org.azerabshv.services.FileService;
import org.azerabshv.services.TweetService;
import org.azerabshv.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final AuthService authService;

    private final FileService fileService;

    private final TweetService tweetService;

    private final PasswordEncoder encoder;

    private final MapStructMapper mapstructMapper;



    @Override
    public UserProfileDto getUserProfile() {
        long userId = authService.getAuthenticatedUserId();
        return userRepository.findById(userId)
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
    public void updateUserPassword(UpdatePasswordRequest passwordRequest) {
        long userId = authService.getAuthenticatedUserId();
        userRepository.findById(userId)
                .map(user -> updateUserPassword(user, passwordRequest))
                .orElseThrow(UserNotFoundException::new);
    }


    @Override
    public List<TweetDetailDto> getAllTweets(int offset) {
        List<Tweet> tweets = tweetService.getTweetByUser( offset);
        return mapstructMapper.tweetsToTweetDetailsDto(tweets);
    }

    @Override
    public List<TweetDetailDto> getLikedTweets(int offset) {
        List<Tweet> likedTweets = tweetService.getTweetByUserLikes(offset);
        return mapstructMapper.tweetsToTweetDetailsDto(likedTweets);
    }

    @Override
    public List<TweetDetailDto> getTweetsWithMedia(int offset) {
        List<Tweet> tweetsWithMedia = tweetService.getTweetWithMedia(offset);
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
    public List<TweetDetailDto> getUserBookmarks(int offset) {
        List<Tweet> bookmarkedTweets = tweetService.getTweetByUserLikes(offset);
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




}
