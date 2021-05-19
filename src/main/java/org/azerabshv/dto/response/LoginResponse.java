package org.azerabshv.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginResponse {
    private long id;
    private String email;
    private String username;
    private String token;

    public LoginResponse(long id, String email, String username, String token) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.token = token;
    }
}
