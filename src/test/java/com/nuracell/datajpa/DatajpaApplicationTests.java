package com.nuracell.datajpa;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.AssertionsForClassTypes.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DatajpaApplicationTests {
    Calculator underTest = new Calculator();

    @Test
    void itShouldAddTwoNumbers() {
        // given
        int numberOne = 12;
        int numberTwo = 23;

        // when
        int result = underTest.add(numberOne, numberTwo);

        // then
        int expected = 35;
        assertThat(result).isEqualTo(expected);
    }

    class Calculator {
        public int add(int a, int b) {
            return a + b;
        }
    }
}
