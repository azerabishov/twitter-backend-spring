package org.azerabshv.exception;

import static org.azerabshv.constants.ErrorMessageConstants.RESOURCE_ACCESS_FORBIDDEN;


public class NotAllowedException extends RuntimeException{
    public NotAllowedException() {super(RESOURCE_ACCESS_FORBIDDEN);}
}
