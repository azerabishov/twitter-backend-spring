package org.azerabshv.exception;


import static org.azerabshv.constants.ErrorMessageConstants.USER_NOT_FOUND;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}
