package com.nuracell.datajpa.junit;

import com.nuracell.datajpa.junit.exceptions.MultiplyingByFourException;
import org.junit.jupiter.api.function.Executable;

public class Calculator{
    public int add(int a, int b) {
        return a + b;
    }

    public int divide(int i, int i1) {
        return i / i1;
    }

    public int multiplyNotByFour(int x, int y) throws MultiplyingByFourException {
        if(x == 4 || y == 4) {
            throw new MultiplyingByFourException("Multiplying by four is not allowed here") ;
        }
        return x * y;
    }

    public int subtractWithTimeout(int a, int b, long timeoutMillis) {
        try {
            Thread.sleep(timeoutMillis);
            return a - b;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
