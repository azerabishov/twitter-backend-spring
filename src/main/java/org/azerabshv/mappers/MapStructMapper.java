package org.azerabshv.mappers;


import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.dto.response.UserProfileDto;
import org.azerabshv.models.Tweet;
import org.azerabshv.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    @Mapping(source = "tweet.user.screenName", target = "screenName")
    @Mapping(source = "tweet.user.username", target = "username")
    @Mapping(source = "tweet.user.avatarUrl", target = "avatarUrl")
    @Mapping(source = "tweet.user.userId", target = "userId")
    TweetDetailDto tweetToTweetDetailDto(Tweet tweet);
    List<TweetDetailDto> tweetsToTweetDetailsDto(List<Tweet> tweets);
    UserDetailDto userToUserDetailDto(User user);
    List<UserDetailDto> usersToUserDetailsDto(List<User> users);
    UserProfileDto userToUserProfileDto(User user);
}
