package org.azerabshv.services;

import org.azerabshv.dto.request.UpdatePasswordRequest;
import org.azerabshv.dto.request.UpdateProfileRequest;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.dto.response.UserProfileDto;
import org.azerabshv.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserProfileDto getUserProfile(Long userId);
    void updateUserProfile(UpdateProfileRequest updateProfileRequest);
    void lockProfile();
    void unlockProfile();
    void updateUserPassword(UpdatePasswordRequest passwordRequest);
    List<TweetDetailDto> getAllTweets(int page);
    List<TweetDetailDto> getLikedTweets(int offset);
    List<TweetDetailDto> getTweetsWithMedia(int offset);
    void addTweetToBookmark( long tweetId);
    void removeTweetFromBookmark( long tweetId);
    List<TweetDetailDto> getUserBookmarks(int offset);
}
