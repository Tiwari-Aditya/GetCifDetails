package in.co.indusnet.java21poc.exceptionHandler;

import in.co.indusnet.java21poc.response.ResponseData;
import in.co.indusnet.java21poc.utility.GetErrorMessage;
import in.co.indusnet.java21poc.utility.ResponseHelper;
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
