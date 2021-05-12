package org.azerabshv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponse {
    List<UserDetailDto> userProfiles;
    List<TweetDetailDto> tweets;
}
