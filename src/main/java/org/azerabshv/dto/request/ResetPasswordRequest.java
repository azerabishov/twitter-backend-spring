package org.azerabshv.dto.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResetPasswordRequest {
    private String token;
    private String password;
}
