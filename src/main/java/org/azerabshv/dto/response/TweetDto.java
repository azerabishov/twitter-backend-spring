package org.azerabshv.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.azerabshv.models.Tweet;

import java.util.List;

import java.util.Date;


@Data
@RequiredArgsConstructor
public class TweetDto {
    private String mediaUrl;
    private String content;
    private Date createdAt;
}