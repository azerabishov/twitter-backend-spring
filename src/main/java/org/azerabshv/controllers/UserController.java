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

    @GetMapping("profile/{id}")
    public UserProfileDto getProfile(@PathVariable Long userId){
        return userService.getUserProfile(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("profile/update")
    public void updateProfile(@ModelAttribute UpdateProfileRequest updateProfileRequest) throws ParseException {
        userService.updateUserProfile(updateProfileRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/profile/lock")
    public void lockProfile() {
        userService.lockProfile();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/profile/unlock")
    public void unlockProfile() {
        userService.unlockProfile();
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

    @GetMapping("bookmark/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTweetToBookmark(@PathVariable("id") Long tweetId){
        userService.addTweetToBookmark(tweetId);
    }

    @DeleteMapping("bookmark/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTweetFromBookmark(@PathVariable("id") Long tweetId){
        userService.removeTweetFromBookmark(tweetId);
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





}
