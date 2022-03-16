package com.sound.labsound.exception;

import com.sound.labsound.exception.domain.*;
import com.sound.labsound.model.CustomHttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(AlbumExistsException.class)
    public ResponseEntity<CustomHttpResponse> albumExistsException(AlbumExistsException e) {
        return responseCustomHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> albumNotFoundException(AlbumNotFoundException e) {
        return responseCustomHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomHttpResponse> accessDeniedException() {
        return responseCustomHttpResponse(FORBIDDEN, "You do not have enough permission");
    }

    @ExceptionHandler(ArtistExistsException.class)
    public ResponseEntity<CustomHttpResponse> artistExistsException(ArtistExistsException e) {
        return responseCustomHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> artistNotFoundException(ArtistNotFoundException e) {
        return responseCustomHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AudioExistsException.class)
    public ResponseEntity<CustomHttpResponse> audioExistsException(AudioExistsException e) {
        return responseCustomHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AudioNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> audioNotFoundException(AudioNotFoundException e) {
        return responseCustomHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<CustomHttpResponse> fileIOException() {
        return responseCustomHttpResponse(BAD_REQUEST, "Error occured while processing file.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> userNotFoundException(UserNotFoundException e) {
        return responseCustomHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UserNameExistsException.class)
    public ResponseEntity<CustomHttpResponse> userNameExistsException(UserNameExistsException e) {
        return responseCustomHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<CustomHttpResponse> emailExistsException(EmailExistsException e) {
        return responseCustomHttpResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(PasswordNotValidException.class)
    public ResponseEntity<CustomHttpResponse> passwordNotValidException(PasswordNotValidException e) {
        return responseCustomHttpResponse(UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(NullOrEmtpyFieldUserException.class)
    public ResponseEntity<CustomHttpResponse> nullOrEmtpyFieldUserException(NullOrEmtpyFieldUserException e) {
        return responseCustomHttpResponse(BAD_REQUEST, e.getMessage());
    }



    private ResponseEntity<CustomHttpResponse> responseCustomHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new CustomHttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message), httpStatus);
    }

}
