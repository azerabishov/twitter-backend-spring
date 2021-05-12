package org.azerabshv.services.impl;

import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.exception.RecordNotFoundException;
import org.azerabshv.exception.UserNotFoundException;
import org.azerabshv.models.Follow;
import org.azerabshv.models.User;
import org.azerabshv.repository.follower.FollowRepository;
import org.azerabshv.repository.user.UserRepository;
import org.azerabshv.services.AuthService;
import org.azerabshv.services.FollowService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final AuthService authService;

    private final FollowRepository followRepository;

    private final UserRepository userRepository;

    @Override
    public List<UserDetailDto> getFriendsList() {
        long userId = authService.getAuthenticatedUserId();
        System.out.println(userId);
        return followRepository.findAllFriends(userId);
    }


    @Override
    public List<Long> getFriendsIds() {
        long userId = authService.getAuthenticatedUserId();
        return followRepository.findFriendsIds(userId);
    }


    @Override
    public List<UserDetailDto> getFollowerList() {
        long userId = authService.getAuthenticatedUserId();
        System.out.println(userId);
        return followRepository.findAllFollowers(userId);
    }

    @Override
    public List<Long> getFollowerIds() {
        long userId = authService.getAuthenticatedUserId();
        return followRepository.findFollowersIds(userId);
    }


    @Override
    public void followUser(long targetUserId) {
        long userId = authService.getAuthenticatedUserId();
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(UserNotFoundException::new);

        userRepository.findById(userId)
                .map(user -> followUser(user, targetUser))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void unfollowUser(long targetUserId) {
        long userId = authService.getAuthenticatedUserId();
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(UserNotFoundException::new);

        userRepository.findById(userId)
                .map(user -> unfollowUser(user, targetUser))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<Follow> checkRecordExist(long followerId, long followingId) {
        return followRepository.findRecord(followerId, followingId);
    }


    private Follow followUser(User user, User targetUser) {
        targetUser.setFollowerCount(targetUser.getFollowerCount()+1);
        user.setFriendCount(user.getFriendCount()+1);
        userRepository.save(targetUser);
        userRepository.save(user);
        return followRepository.save(new Follow(user.getUserId(), targetUser.getUserId()));
    }


    private Follow unfollowUser(User user, User targetUser) {
        targetUser.setFollowerCount(targetUser.getFollowerCount()-1);
        user.setFriendCount(user.getFriendCount()-1);
        userRepository.save(targetUser);
        userRepository.save(user);
        return followRepository.findRecord(user.getUserId(), targetUser.getUserId())
                .map(follower -> {
                    followRepository.delete(follower);
                    return follower;
                })
                .orElseThrow(RecordNotFoundException::new);
    }
}
