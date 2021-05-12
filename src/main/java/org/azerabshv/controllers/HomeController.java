package org.azerabshv.controllers;


import lombok.RequiredArgsConstructor;
import org.azerabshv.dto.response.SearchResponse;
import org.azerabshv.dto.response.TweetDetailDto;
import org.azerabshv.services.TweetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HomeController {

    private final TweetService tweetService;

    @GetMapping("home")
    public List<TweetDetailDto> getHomePageTweets() {
        return tweetService.getTweetsByUserPreference();
    }

    @GetMapping("search")
    public SearchResponse searchTweets(@RequestParam String searchKey) {
        return tweetService.search(searchKey);
    }
}
