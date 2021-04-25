package org.azerabshv.controllers;


import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.security.UserDetailsImpl;
import org.azerabshv.services.TweetService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final TweetService tweetService;

    @GetMapping("home")
    public List<TweetDetailDto> getHomePageTweets() {
        return tweetService.getTweetsByUserPreference();
    }
}
