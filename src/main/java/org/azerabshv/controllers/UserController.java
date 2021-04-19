package org.azerabshv.controllers;

import org.azerabshv.dto.request.UpdatePasswordRequest;
import org.azerabshv.dto.request.UpdateProfileRequest;
import org.azerabshv.dto.response.MessageResponse;
import org.azerabshv.dto.response.UserDetailDto;
import org.azerabshv.dto.response.UserProfileDto;
import org.azerabshv.models.User;
import org.azerabshv.repository.follower.FollowerRepository;
import org.azerabshv.security.UserDetailsImpl;
import org.azerabshv.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;


    @Autowired
    FollowerRepository followerRepository;

    @GetMapping("profile")
    public UserProfileDto getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        return userService.getUserProfile(userPrincipal.getId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("profile/update")
    public void updateProfile(@RequestParam UpdateProfileRequest updateProfileRequest){
//        @RequestParam(value = "avatarUrl", required = false) MultipartFile avatarUrl,
//        @RequestParam(value = "profileBackgroundImageUrl", required = false) MultipartFile profileBackgroundImageUrl,
//        @RequestParam(value = "screenName", required = false) String screenName,
//        @RequestParam(value = "bio", required = false) String bio,
//        @RequestParam(value = "location", required = false) String location,
//        @RequestParam(value = "website", required = false) String website,
//        @RequestParam(value = "birthdate", required = false) Date birthdate

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        userService.updateUserProfile(userPrincipal.getId(), updateProfileRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("password/update")
    public void updateProfile(@RequestBody UpdatePasswordRequest passwordRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        userService.updateUserPassword(userPrincipal.getId(), passwordRequest);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("follow/{id}")
    public void followUser(@PathVariable("id") long targetUserId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        userService.followUser(userPrincipal.getId(), targetUserId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("unfollow/{id}")
    public void unfollowUser(@PathVariable("id") long targetUserId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        userService.unfollowUser(userPrincipal.getId(), targetUserId);
    }


    @GetMapping("following/get")
    public ResponseEntity<?> getFollowerList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        return userService.getFollowerList(userPrincipal.getId());
    }

    @GetMapping("follower/get")
    public ResponseEntity<?> getFollowingList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
        return userService.getFollowingList(userPrincipal.getId());
    }
}
