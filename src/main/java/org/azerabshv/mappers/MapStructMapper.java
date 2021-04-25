package org.azerabshv.mappers;


import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.dto.response.UserProfileDto;
import org.azerabshv.models.Tweet;
import org.azerabshv.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    TweetDetailDto tweetToTweetDetailDto(Tweet tweet);
    List<TweetDetailDto> tweetsToTweetDetailsDto(List<Tweet> tweets);
    UserDetailDto userToUserDetailDto(User user);
    List<UserDetailDto> usersToUserDetailsDto(List<User> users);
    UserProfileDto userToUserProfileDto(User user);
}
