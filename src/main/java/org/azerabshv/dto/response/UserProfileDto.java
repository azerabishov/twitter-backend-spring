package org.azerabshv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
    String username;
    String screenName;
    String avatarUrl;
    String profileBackgroundImageUrl;
    String bio;
    String location;
    String website;
    Date createdAt;
    Date birthday;
    int followerCount;
    int friendCount;
}
