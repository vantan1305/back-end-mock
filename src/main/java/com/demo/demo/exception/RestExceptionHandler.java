package com.demo.demo.exception;

import com.demo.demo.model.ResponseMessage;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(MultipartException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseMessage("Unable to upload. File is too large!"));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseMessage("Unable to upload. File is too large!"));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(FileUploadException exc) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseMessage(exc.getMessage()));
    }
}
