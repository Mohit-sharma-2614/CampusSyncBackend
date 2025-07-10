package com.example.CampusSync.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AuthModel {
    private Boolean valid;
    private String message;

    // Constructors
    public AuthModel() {}

    public AuthModel(Boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }
}
