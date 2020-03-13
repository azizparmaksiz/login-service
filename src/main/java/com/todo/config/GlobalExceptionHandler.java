package com.todo.config;


import com.todo.constraints.MessageConstraints;
import com.todo.dto.ValidationErrorDTO;
import com.todo.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {ArithmeticException.class, IllegalArgumentException.class, NoSuchAlgorithmException.class, UnsupportedEncodingException.class})
    @ResponseBody
    protected ResponseEntity<ExceptionMessage> handleCustomerLimitExceedException(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionMessage(HttpStatus.BAD_REQUEST.name()), HttpStatus.BAD_REQUEST);
    }

    @Override
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage());
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError fieldError : fieldErrors) {
            dto.addFieldError(fieldError.getField(), "not valid or null");
        }

        return super.handleExceptionInternal(ex, dto, headers, status, request);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class, ConstraintViolationException.class})
    @ResponseBody
    protected ResponseEntity<ExceptionMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionMessage(HttpStatus.BAD_REQUEST.name()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {IllegalOperationException.class})
    @ResponseBody
    protected ResponseEntity<ExceptionMessage> illegalOperationFound(IllegalOperationException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getExceptionMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseBody
    protected ResponseEntity<ExceptionMessage> notFoundException(NotFoundException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getExceptionMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DuplicateException.class})
    @ResponseBody
    protected ResponseEntity<ExceptionMessage> duplicatedException(DuplicateException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getExceptionMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UnAuthorizedOperationException.class})
    @ResponseBody
    protected ResponseEntity<ExceptionMessage> unAuthorized(UnAuthorizedOperationException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getExceptionMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {UserNotActivatedException.class})
    @ResponseBody
    protected ResponseEntity<ExceptionMessage> usernotActivated(UserNotActivatedException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getExceptionMessage(), HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler(value = {SQLException.class})
    @ResponseBody
    protected ResponseEntity<ExceptionMessage> handleSQLException(SQLException ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ExceptionMessage(MessageConstraints.BAD_SQL, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
