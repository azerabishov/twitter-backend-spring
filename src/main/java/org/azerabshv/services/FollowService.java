package org.azerabshv.services;

import org.azerabshv.dto.response.UserDetailDto;

import java.util.List;

public interface FollowService {
    List<UserDetailDto> getFriendsList();
    List<Long> getFriendsIds();
    List<UserDetailDto> getFollowerList();
    List<Long> getFollowerIds();
    void followUser(long targetUserId);
    void unfollowUser(long targetUserId);

}
