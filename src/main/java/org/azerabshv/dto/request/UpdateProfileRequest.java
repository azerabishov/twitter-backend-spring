package org.azerabshv.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileRequest {
    MultipartFile avatarUrl;
    MultipartFile profileBackgroundImageUrl;
    String screenName;
    String bio;
    String location;
    String website;
    Date birthdate;
}
