package org.azerabshv.exception;

import static org.azerabshv.constants.ErrorMessageConstants.EMAIL_NOT_VERIFIED;


public class EmailNotVerifiedException extends RuntimeException{
    public EmailNotVerifiedException() {super(EMAIL_NOT_VERIFIED);}
}
