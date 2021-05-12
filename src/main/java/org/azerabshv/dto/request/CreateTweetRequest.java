package org.azerabshv.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
public class CreateTweetRequest {
    private String content;
    private MultipartFile mediaFile;
}
