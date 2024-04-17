package com.aditya.getciflist.exceptionHandler;

import com.aditya.getciflist.response.ResponseData;
import com.aditya.getciflist.utility.GetErrorMessage;
import com.aditya.getciflist.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @Autowired
    private GetErrorMessage getErrorMessage;

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseData> globalExceptionHandler(Exception e) {
        ResponseData response = ResponseHelper.responseSender(
                getErrorMessage.getErrorMessages("UNABLE_TO_PROCESS_ERR"),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<ResponseData>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
