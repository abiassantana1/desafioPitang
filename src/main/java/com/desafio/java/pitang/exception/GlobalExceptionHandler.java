package com.desafio.java.pitang.exception;

import com.desafio.java.pitang.model.dto.ErrorMessageDTO;
import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipleBusinessException.class)
    public ResponseEntity<Object> handleMultipleBusinessException(MultipleBusinessException ex) {
        List<ErrorMessageDTO> errorMessages = ex.getExceptions().stream()
                .map(erroMessage -> new ErrorMessageDTO(erroMessage.getMessage(), HttpStatus.BAD_REQUEST.value()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException() {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO("Invalid login or passwordd", HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<Object> handleCredentialsExpiredException() {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO("Unauthorized - invalid session", HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SourceNotFoundException.class)
    public ResponseEntity<Object> handleSourceNotFoundException(SourceNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Object> handleInvalidCredentialException(InvalidCredentialException ex) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(MappingException.class)
    public ResponseEntity<Object> handleMappingException(MappingException ex) {
        ErrorMessageDTO errorMessage = new ErrorMessageDTO("Invalid fields", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
