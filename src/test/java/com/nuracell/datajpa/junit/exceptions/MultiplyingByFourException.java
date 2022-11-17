package com.nuracell.datajpa.junit.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MultiplyingByFourException extends Exception {
    public MultiplyingByFourException(String message) {
        super(message);
    }
}
