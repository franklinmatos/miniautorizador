package com.microautorizador.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnauthorizedException extends RuntimeException{

    private static final long serialVersionUID = 7778489596283660999L;

    public UnauthorizedException(String message) {
        super(message);
    }

}
