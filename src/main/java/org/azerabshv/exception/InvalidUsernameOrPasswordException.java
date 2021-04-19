package org.azerabshv.exception;

import static org.azerabshv.constants.ErrorMessageConstants.INVALID_USERNAME_OR_PASSWORD;

public class InvalidUsernameOrPasswordException extends InvalidStateException{
    public InvalidUsernameOrPasswordException() {
        super(INVALID_USERNAME_OR_PASSWORD);
    }
}
