package com.pismo.paymentpractice.exception;

import com.pismo.paymentpractice.controller.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler(CreateAccountErrorException.class)
    @ResponseBody
    public ErrorDTO httpMessageNotReadableExceptionHandler(Exception e) {
        return new ErrorDTO("400 Bad Request", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(NotFoundErrorException.class)
    @ResponseBody
    public ErrorDTO httpNotFoundErrorExceptionHandler(Exception e) {
        return new ErrorDTO("404 Not Found", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 404
    @ExceptionHandler(NotProcessableException.class)
    @ResponseBody
    public ErrorDTO httpNotProcessableErrorExceptionHandler(Exception e) {
        return new ErrorDTO("422 Unprocessable Request", e.getMessage());
    }
}
