package org.azerabshv.exception;


import static org.azerabshv.constants.ErrorMessageConstants.EMAIL_ALREADY_EXISTS;

public class EmailAlreadyExistsException extends AlreadyExistsException{
    public EmailAlreadyExistsException() {
        super(EMAIL_ALREADY_EXISTS);
    }
}
