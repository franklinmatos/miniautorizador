package com.microautorizador.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6324386991680530871L;

    public NotFoundException(String message) {
        super(message);
    }

}
