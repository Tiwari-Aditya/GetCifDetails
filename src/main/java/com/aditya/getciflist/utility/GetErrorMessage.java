package com.aditya.getciflist.utility;

import com.aditya.getciflist.model.TblErrorMessages;
import com.aditya.getciflist.repository.TblErrorMessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetErrorMessage {
    @Autowired
    private TblErrorMessagesRepository tblErrorMesRepository;

    /*
     * this method purpose to get the error messages by error_code return
     * 'error_message' otherwise return null .
     */
    public String getErrorMessages(String errorCode) {

        // to get the object with errorCode
        TblErrorMessages tblErrorMessage = tblErrorMesRepository.findByErrorCode(errorCode);

        // if condition for validate data
        if (tblErrorMessage != null) {
            String errorMessage = tblErrorMessage.getErrorMessage();
            return errorMessage;
        } else {
            return null;
        }


    }
}
