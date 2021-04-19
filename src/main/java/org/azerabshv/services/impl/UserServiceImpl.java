package org.azerabshv.services.impl;

import org.azerabshv.dto.request.UpdatePasswordRequest;
import org.azerabshv.dto.request.UpdateProfileRequest;
import org.azerabshv.dto.response.MessageResponse;
import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.dto.response.UserProfileDto;
import org.azerabshv.exception.InvalidUsernameOrPasswordException;
import org.azerabshv.exception.RecordNotFoundException;
import org.azerabshv.exception.UserNotFoundException;
import org.azerabshv.models.Follower;
import org.azerabshv.models.User;
import org.azerabshv.repository.follower.FollowerRepository;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.services.FileService;
import org.azerabshv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    FileService fileService;

    @Autowired
    FollowerRepository followerRepository;

    @Autowired
    PasswordEncoder encoder;


    @Override
    public User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserProfileDto getUserProfile(long userId) {
        return userRepository.findById(userId)
                .map(this::getUserProfile)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void updateUserProfile(long userId, UpdateProfileRequest updateProfileRequest) {
        userRepository.findById(userId)
                .map(user -> updateUserPorfile(user, updateProfileRequest))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void updateUserPassword(long userId, UpdatePasswordRequest passwordRequest) {
        userRepository.findById(userId)
                .map(user -> updateUserPassword(user, passwordRequest))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void followUser(long userId, long targetUserId) {
        User targetUser;
        Optional<User> targetUserData = userRepository.findById(targetUserId);
        if (targetUserData.isPresent()) {
            targetUser = targetUserData.get();
        } else {
            throw new UserNotFoundException();
        }
        userRepository.findById(userId)
                .map(user -> followUser(user, targetUser))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void unfollowUser(long userId, long targetUserId) {
        User targetUser;
        Optional<User> targetUserData = userRepository.findById(targetUserId);
        if (targetUserData.isPresent()) {
            targetUser = targetUserData.get();
        } else {
            throw new UserNotFoundException();
        }
        userRepository.findById(userId)
                .map(user -> unfollowUser(user, targetUser))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public ResponseEntity<?> getFollowerList(long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    List<UserDetailDto> followingsList = followerRepository.findAllFollowings(userId);
                    return ResponseEntity.status(HttpStatus.OK).body(followingsList);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public ResponseEntity<?> getFollowingList(long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    List<UserDetailDto> followersList = followerRepository.findAllFollowers(userId);
                    return ResponseEntity.status(HttpStatus.OK).body(followersList);
                })
                .orElseThrow(UserNotFoundException::new);
    }


    private UserProfileDto getUserProfile(User user) {
        return UserProfileDto.builder()
                .username(user.getUsername())
                .screenName(user.getScreenName())
                .avatarUrl(user.getAvatarUrl())
                .profileBackgroundImageUrl(user.getProfileBackgroundImageUrl())
                .bio(user.getBio())
                .location(user.getLocation())
                .website(user.getWebsite())
                .birthday(user.getBirthdate())
                .followerCount(user.getFollowerCount())
                .followingCount(user.getFollowingCount())
                .build();
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

    private Follower followUser(User user, User targetUser) {
        targetUser.setFollowerCount(targetUser.getFollowerCount()+1);
        user.setFollowingCount(user.getFollowingCount()+1);
        userRepository.save(targetUser);
        userRepository.save(user);
        return followerRepository.save(new Follower(user.getUserId(), targetUser.getUserId()));
    }


    private Follower unfollowUser(User user, User targetUser) {
        targetUser.setFollowerCount(targetUser.getFollowerCount()-1);
        user.setFollowingCount(user.getFollowingCount()-1);
        userRepository.save(targetUser);
        userRepository.save(user);
        return followerRepository.findRecord(user.getUserId(), targetUser.getUserId())
                .map(follower -> {
                    followerRepository.delete(follower);
                    return follower;
                })
                .orElseThrow(RecordNotFoundException::new);
    }

}
