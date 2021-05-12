package org.azerabshv.dto.response;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginResponse {
    private long id;
    private String email;
    private String username;
    private String token;
}
