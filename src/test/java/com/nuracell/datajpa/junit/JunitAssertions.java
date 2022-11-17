package com.nuracell.datajpa.junit;

import com.nuracell.datajpa.junit.exceptions.MultiplyingByFourException;
import org.checkerframework.dataflow.qual.Pure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JunitAssertions {

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
// -------------------------- EQUALS --------------------------
    @Test
    public void calculatorShouldAdd() {
        assertEquals(5, calculator.add(2,3));
    }

    @Test
    public void calculatorShouldAddAndEquationMustNotBeEqual() {
        assertNotEquals(6, calculator.add(2,3));
    }

// -------------------------- THROWS --------------------------
    @Test
    public void calculatorShouldThrowException() {
        assertThrows(Exception.class, () -> calculator.divide(2, 0));
    }

    @Test
    public void calculatorShouldNotThrowException() {
        assertDoesNotThrow(() -> calculator.divide(7, 7));
    }

    @Test
    public void shouldThrowExactly() {
        assertThrowsExactly(MultiplyingByFourException.class, ()-> calculator.multiplyNotByFour(4, 8));
    }

// -------------------------- BOOLEAN --------------------------
    @Test
    public void calculatorShouldReturnTrue() {
        assertTrue(5 > calculator.add(6,Integer.MIN_VALUE));
    }

    @Test
    public void calculatorShouldReturnFalse() {
        assertFalse(5 < calculator.add(6,Integer.MIN_VALUE));
    }

// -------------------------- NULL --------------------------
    @Test
    public void shouldBeNull(){
        assertNull(null);
        assertNull(null, "null");
        assertNull(null, () -> {
            String message = "";
            message = "Message supplier";
            System.out.println(message);
            return message;
        });
    }

    @Test
    public void shouldNotBeNUll() {
        assertNotNull(calculator);

    }

// -------------------------- InstanceOf --------------------------
    @Test
    public void integerShouldBeInstanceOfNumber() {
        assertInstanceOf(Number.class, 5);
    }

/*
    @Test
    public void stringShouldNotBeInstanceOfNumber() {
//        assertInstanceOf(String.class, 3);
    }
*/

// -------------------------- Lines Match and Arrays --------------------------
    @Test
    public void linesShouldBeTheSame(){
        assertLinesMatch(List.of("kek", "aboba"), List.of("kek", "aboba"));
        assertLinesMatch(Stream.of("e1", "e2"), Stream.of("e1", "e2"));

        assertLinesMatch(
                Stream.of("1", "2"),
                Stream.of(1, 2).map(String::valueOf)
        );
    }

    @Test
    public void twoEqualArraysShouldBeEqual() {
        assertArrayEquals(
                new int[] {1, 2, 5},
                new int[] {1, 2, 5}
        );
    }

//        assertIterableEquals();
    @Test
    public void assertIterableEqualsTest() {
        assertIterableEquals(
                List.of(),
                List.of()
        );

        //org.opentest4j.AssertionFailedError: iterable contents differ at index [0], expected: [I@2cbb3d47<[5, 9, 7]> but was: [I@527e5409<[5, 9, 7]>
/*        assertIterableEquals(
                List.of(new int[]{5, 9, 7}),
                List.of(new int[]{5, 9, 7})
        );*/

        assertIterableEquals(
                List.of(5,9,7),
                List.of(5,9,7)
        );

        assertIterableEquals(
                Set.of(1,5,7),
                Set.of(1,5,7)
        );
    }



// -------------------------- Assert all --------------------------
    @Test
    public void assertAllShouldWork() {
        assertAll(() -> calculator.multiplyNotByFour(5, 5));

        assertAll(
                () -> calculator.multiplyNotByFour(10, 40),
                () -> calculator.multiplyNotByFour(450, 35),
                () -> calculator.multiplyNotByFour(785785, 4750)
        );

        Collection<Executable> executables = List.of(
                () -> calculator.multiplyNotByFour(10, 40),
                () -> calculator.multiplyNotByFour(450, 35),
                () -> calculator.multiplyNotByFour(785785, 4750)
        );
        assertAll(executables);

        assertAll(Stream.of(
                () -> calculator.multiplyNotByFour(10, 40),
                () -> calculator.multiplyNotByFour(450, 35)
        ));

        assertAll(
                "Heading",
                () -> calculator.multiplyNotByFour(10, 40),
                () -> calculator.multiplyNotByFour(450, 35)
                );

        assertAll("Heading2", executables);

        assertAll("Heading", Stream.of(
                () -> calculator.multiplyNotByFour(10, 40),
                () -> calculator.multiplyNotByFour(450, 35)
        ));
    }

// -------------------------- (NOT) SAME --------------------------
    @Test
    public void twoEqualStringsShouldBeSame() {
        String s1 = "qwerty";
        String s2 = "qwerty";
        assertSame(s1, s2);
    }

    @Test
    public void twoNotSameStringsShouldNotSame() {
        String s1 = "string1";
        String s2 = "string2";
        assertNotSame(s1, s2);
    }

// -------------------------- TimeOut --------------------------
// (assertTimeOut() and assertTimeoutPreemptively() are alternatives to @Timeout annotation)
    @Test
    public void assertTimeoutTest() {
        assertTimeout(Duration.ofNanos(0), () -> calculator.add(1,1));
    }

    @Test
    @Timeout(
            value = 700,
            unit = TimeUnit.MILLISECONDS
    )
    public void assertTimeoutAnnotationTest() {
        calculator.add(1,1);
    }

    @Test
    public void assertTimeoutThreeSeconds() { // if executable takes more time than duration, - test fails
        assertTimeout(Duration.ofMillis(3000), () -> calculator.subtractWithTimeout(10,60, 2950));
    }

    @Test // Preemptively - превентивно
    public void assertTimeoutPreemptivelyTest() {
        assertTimeoutPreemptively(
                Duration.ofMillis(100),
                () -> calculator.subtractWithTimeout(10,60, 105)
        );
    }

    @AfterEach
    void tearDown() {
        calculator = null;
    }
}
