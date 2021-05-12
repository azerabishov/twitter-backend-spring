package org.azerabshv.services;

import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.models.Follow;
import org.azerabshv.models.User;

import java.util.List;
import java.util.Optional;

public interface FollowService {
    List<UserDetailDto> getFriendsList();
    List<Long> getFriendsIds();
    List<UserDetailDto> getFollowerList();
    List<Long> getFollowerIds();
    void followUser(long targetUserId);
    void unfollowUser(long targetUserId);
    Optional<Follow> checkRecordExist(long followerId, long followingId);

}
