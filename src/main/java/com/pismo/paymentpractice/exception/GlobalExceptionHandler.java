package com.pismo.paymentpractice.exception;

import com.pismo.paymentpractice.controller.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(m -> {
                                    if(!Objects.requireNonNull(m.getDefaultMessage())
                                               .isEmpty()) {
                                        return m.getDefaultMessage();
                                    }
                                    return "";
                                })
                                .toList();

        return new ResponseEntity<>(new ErrorDTO("400 Bad Request", errors.toString()),
                HttpStatus.BAD_REQUEST);
    }
}
