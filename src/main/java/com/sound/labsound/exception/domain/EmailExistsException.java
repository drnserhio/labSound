package com.sound.labsound.exception.domain;

public class EmailExistsException extends Exception {
    public EmailExistsException(String message) {
        super(message);
    }
}
