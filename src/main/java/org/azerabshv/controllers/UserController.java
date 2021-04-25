package org.azerabshv.controllers;

import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.request.UpdatePasswordRequest;
import org.azerabshv.dto.request.UpdateProfileRequest;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.dto.response.UserProfileDto;
import org.azerabshv.services.FollowService;
import org.azerabshv.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final FollowService followService;

    @GetMapping("profile")
    public UserProfileDto getProfile(){
        return userService.getUserProfile();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("profile/update")
    public void updateProfile(
            @RequestParam(value = "avatarUrl", required = false) MultipartFile avatarUrl,
            @RequestParam(value = "profileBackgroundImageUrl", required = false) MultipartFile profileBackgroundImageUrl,
            @RequestParam(value = "screenName", required = false) String screenName,
            @RequestParam(value = "bio", required = false) String bio,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "website", required = false) String website
    ) throws ParseException {
        UpdateProfileRequest updateProfileRequest = UpdateProfileRequest.builder()
                .avatarUrl(avatarUrl)
                .profileBackgroundImageUrl(profileBackgroundImageUrl)
                .screenName(screenName)
                .bio(bio)
                .location(location)
                .website(website)
                .build();

        userService.updateUserProfile(updateProfileRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("password/update")
    public void updateProfile(@RequestBody UpdatePasswordRequest passwordRequest){
        userService.updateUserPassword(passwordRequest);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("follow/{id}")
    public void followUser(@PathVariable("id") long targetUserId){
        followService.followUser(targetUserId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("unfollow/{id}")
    public void unfollowUser(@PathVariable("id") long targetUserId){
        followService.unfollowUser( targetUserId);
    }


    @GetMapping("friends/list")
    public List<UserDetailDto> getFriendsList(){
        return followService.getFriendsList();
    }

    @GetMapping("followers/list")
    public List<UserDetailDto> getFollowerList(){
        return followService.getFollowerList();
    }

    @GetMapping("tweets")
    public List<TweetDetailDto> getTweets(@RequestParam int page){
        int offset;
        if(page > 0) {
            offset = (page-1)*10;
        }else{
            offset = 1;
        }
        return userService.getAllTweets(offset);
    }

    @GetMapping("likes")
    public List<TweetDetailDto> getLikes(@RequestParam int page){
        int offset;
        if(page > 0) {
            offset = (page-1)*10;
        }else{
            offset = 1;
        }
        return userService.getLikedTweets(offset);
    }


    @GetMapping("medias")
    public List<TweetDetailDto> getMedias(@RequestParam int page){
        int offset;
        if(page > 0) {
            offset = (page-1)*10;
        }else{
            offset = 1;
        }
        return userService.getTweetsWithMedia(offset);
    }

    @GetMapping("bookmark/:id")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTweetToBookmark(@PathVariable Long tweetId){
        userService.addTweetToBookmark(tweetId);
    }

    @GetMapping("bookmarks")
    @ResponseStatus(HttpStatus.CREATED)
    public List<TweetDetailDto> getBookMarks(@RequestParam int page){
        int offset;
        if(page > 0) {
            offset = (page-1)*10;
        }else{
            offset = 1;
        }
        return userService.getUserBookmarks(offset);
    }

    @DeleteMapping("bookmark/:id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTweetFromBookmark(@PathVariable Long tweetId){
        userService.removeTweetFromBookmark(tweetId);
    }


}
