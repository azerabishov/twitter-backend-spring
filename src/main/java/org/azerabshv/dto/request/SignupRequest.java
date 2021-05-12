package org.azerabshv.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class SignupRequest {
    private String email;
    private String username;
    private String screen_name;
    private String phone_number;
    private Date birthday;
    private String password;
}
