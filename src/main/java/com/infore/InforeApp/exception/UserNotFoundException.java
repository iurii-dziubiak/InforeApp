package com.infore.InforeApp.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final String username;

    public UserNotFoundException(String username) {
        super("User not found: " + username);
        this.username = username;
    }
}
