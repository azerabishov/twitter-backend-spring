package org.azerabshv.exception;


import static org.azerabshv.constants.ErrorMessageConstants.USERNAME_ALREADY_EXISTS;

public class UsernameAlreadyExistsException extends AlreadyExistsException{
    public UsernameAlreadyExistsException() {
        super(USERNAME_ALREADY_EXISTS);
    }
}
