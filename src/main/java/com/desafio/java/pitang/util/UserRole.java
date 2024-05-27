package com.desafio.java.pitang.util;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("user");

    private final String role;

    UserRole(String role){
        this.role = role;
    }
}
