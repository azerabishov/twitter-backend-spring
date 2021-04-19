package org.azerabshv.services;

import org.azerabshv.dto.request.UpdatePasswordRequest;
import org.azerabshv.dto.request.UpdateProfileRequest;
import org.azerabshv.dto.response.UserProfileDto;
import org.azerabshv.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User getUser(long userId);
    UserProfileDto getUserProfile(long userId);
    void updateUserProfile(long userId, UpdateProfileRequest updateProfileRequest);
    void updateUserPassword(long userId, UpdatePasswordRequest passwordRequest);
    void followUser(long userId, long targetUserId);
    void unfollowUser(long userId, long targetUserId);
    ResponseEntity<?> getFollowerList(long userId);
    ResponseEntity<?> getFollowingList(long userId);
}
