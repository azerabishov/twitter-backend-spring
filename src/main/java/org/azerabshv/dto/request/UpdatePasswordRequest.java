package org.azerabshv.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdatePasswordRequest {
    private String currentPass;
    private String newPassword;
    private String newPasswordConfirmation;
}
