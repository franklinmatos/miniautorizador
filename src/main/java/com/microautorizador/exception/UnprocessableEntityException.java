package com.microautorizador.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnprocessableEntityException extends RuntimeException {

    private static final long serialVersionUID = -1910692456746145525L;

    public UnprocessableEntityException(String message) {
        super(message);
    }

}
