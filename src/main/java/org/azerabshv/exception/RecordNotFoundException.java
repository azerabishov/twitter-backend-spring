package org.azerabshv.exception;

import static org.azerabshv.constants.ErrorMessageConstants.RECORD_NOT_FOUND;

public class RecordNotFoundException extends NotFoundException{
    public RecordNotFoundException() {
        super(RECORD_NOT_FOUND);
    }
}
