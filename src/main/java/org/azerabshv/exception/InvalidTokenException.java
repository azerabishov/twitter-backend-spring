package org.azerabshv.exception;

import static org.azerabshv.constants.ErrorMessageConstants.INVALID_TOKEN;

public class InvalidTokenException extends InvalidStateException{
    public InvalidTokenException() {super(INVALID_TOKEN);}
}
