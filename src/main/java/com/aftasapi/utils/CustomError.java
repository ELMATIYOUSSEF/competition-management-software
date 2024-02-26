package com.aftasapi.utils;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomError {
    private final String field;
    private final String message;

    @Override
    public String toString() {
        return "{" +
                "\"field\" : " + getField() +","+
                "\"message\" : " + getMessage() +","+
                "}";
    }
}
