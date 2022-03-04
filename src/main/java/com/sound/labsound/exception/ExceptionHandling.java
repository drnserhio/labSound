package com.sound.labsound.exception;

import com.sound.labsound.exception.domain.*;
import com.sound.labsound.model.CustomHttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(AlbumExistsException.class)
    public ResponseEntity<CustomHttpResponse> albumExistsException(AlbumExistsException e) {
        return responseCustomHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AlbumNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> albumNotFoundException(AlbumNotFoundException e) {
        return responseCustomHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ArtistExistsException.class)
    public ResponseEntity<CustomHttpResponse> artistExistsException(ArtistExistsException e) {
        return responseCustomHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> artistNotFoundException(ArtistNotFoundException e) {
        return responseCustomHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AudioExistsException.class)
    public ResponseEntity<CustomHttpResponse> audioExistsException(AudioExistsException e) {
        return responseCustomHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AudioNotFoundException.class)
    public ResponseEntity<CustomHttpResponse> audioNotFoundException(AudioNotFoundException e) {
        return responseCustomHttpResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<CustomHttpResponse> responseCustomHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new CustomHttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message), httpStatus);
    }
}
