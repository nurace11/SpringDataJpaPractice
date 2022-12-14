package com.nuracell.datajpa.junit;

import org.assertj.core.api.Condition;
import org.assertj.core.description.Description;
import org.assertj.core.internal.Conditions;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.discovery.predicates.IsNestedTestClass;

import java.lang.management.GarbageCollectorMXBean;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class AssertJAssertions {
    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator(50, "Calculogus");
    }

// ------------------------------------ NUMBERS ------------------------------------
    @Test
    public void calculateTwoAddFiveShouldBeSeven() {
        assertThat(calculator.add(2,5)).isEqualTo(7);
    }

    @Test
    public void calculateTwoAddFiveShouldNotBeNine() {
        assertThat(calculator.add(2,5)).isNotEqualTo(9);
    }

    @Test
    public void calculateAssertJOtherMethodsForInteger() {
        assertThat(calculator.add(2,5)).isLessThan(8);
        assertThat(calculator.add(2,5)).isGreaterThan(6);

        assertThat(calculator.add(2,6)).isEven(); // Четный
        assertThat(calculator.add(2,5)).isOdd(); // Нечетный

        assertThat(calculator.add(2,5)).isIn(List.of(7));
        assertThat(calculator.add(2,5)).isIn(7, 5, 6, 7);
    }

// ------------------------------------ OBJECT OF ANY OTHER CLASS ------------------------------------
    Calculator testCalc = new Calculator(10, "wsxEDCrfvTGByhnUJMik,OL>p;/{\"]");

    @Test
    public void assertJAssertMethodsOnAnyClass() {
        assertThat(calculator).isEqualTo(calculator);
        assertThat(calculator).isNotEqualTo(testCalc);

        assertThat(calculator).isIn(calculator, testCalc);
        assertThat(calculator).isIn(List.of(calculator, testCalc));
        assertThat(calculator).isNotIn(testCalc, new Calculator());
        assertThat(calculator).isNotIn(List.of(testCalc, new Calculator()));

        assertThat(calculator).isNotNull();
        System.out.println("assertThat(testCalc).hashCode() = " + assertThat(testCalc).hashCode());
        System.out.println("assertThat(calculator).hashCode() = " + assertThat(calculator).hashCode());
        testCalc = null;
        assertThat(testCalc).isNull();


        System.out.println("assertThat(calculator).descriptionText() = " + assertThat(calculator).descriptionText());
        System.out.println("assertThat(calculator).hashCode() = " + assertThat(calculator).hashCode() + " calcHashcode " + calculator.hashCode());
        System.out.println("assertThat(calculator).asString() = " + assertThat(calculator).asString());
        System.out.println("assertThat(calculator).toString() = " + assertThat(calculator).toString());
        System.out.println(assertThat(calculator).info);

        assertThat(calculator).returns(calculator, (e) -> calculator);
//        assertThat(calculator).describedAs()
//        assertThat(calculator).getWritableAssertionInfo()
//        assertThat(calculator).extracting()
//        assertThat(calculator).withThreadDumpOnError();
//        assertThat(calculator).withRepresentation();
//        assertThat(calculator).withFailMessage();


//        assertThat(calculator).usingRecursiveComparison();
//        assertThat(calculator).usingComparator();
//        assertThat(calculator).usingComparatorForFields();
//        assertThat(calculator).usingComparatorForType();

        assertThat(calculator).satisfies();
        assertThat(calculator).satisfiesAnyOf();

//        assertThat(calculator).asList();
//        assertThat(calculator).overridingErrorMessage();

//        assertThat(calculator).matches();
    }

    @Test
    public void assertConditionableMethods() {
        // Conditions. is, isNot, has, doesNotHave
        Condition<Calculator> complicatedCalculator = new Condition<>((c) -> c.getButtons() > 40, "our calculator is complicated calculator");
        Condition<Calculator> simpleCalculator = new Condition<>((c) -> c.getButtons() < 15 , "our calculator is simple calculator");
        assertThat(calculator).is(complicatedCalculator);
        assertThat(calculator).isNot(simpleCalculator);

        Condition<Calculator> calculatorNameCalculogus = new Condition<>((c) -> c.getName().equals("Calculogus"), " Calculator's name is Calculogus");
        Condition<Calculator> calculatorNameCali = new Condition<>((c) -> c.getName().equals("Cali"), " Calculator's name is Cali");
        assertThat(calculator).has(calculatorNameCalculogus);
        assertThat(calculator).doesNotHave(calculatorNameCali);

        assertThat(calculator).is(allOf(complicatedCalculator, calculatorNameCalculogus));
        assertThat(calculator).is(not(simpleCalculator));
        assertThat(calculator).is(not(calculatorNameCali));
        assertThat(calculator).is(anyOf(complicatedCalculator, calculatorNameCalculogus, calculatorNameCali, simpleCalculator));
    }

    @Test
    public void assertReflectionMethods() {
        assertThat(calculator).isNotExactlyInstanceOf(Object.class);
        assertThat(calculator).isExactlyInstanceOf(Calculator.class);

//        assertThat(testCalc).hasAllNullFieldsOrProperties(); //test fails
        assertThat(calculator).hasAllNullFieldsOrPropertiesExcept("name", "buttons");
        assertThat(calculator).hasFieldOrProperty("name");
        assertThat(calculator).hasFieldOrProperty("buttons");
        assertThat(calculator).hasFieldOrPropertyWithValue("buttons", 50);
        assertThat(calculator).hasFieldOrPropertyWithValue("name", "Calculogus");
        assertThat(calculator).hasOnlyFields("buttons", "name");
//        assertThat(calculator).hasOnlyFields("buttons"); //test fails

        assertThat(calculator).hasToString(calculator.toString());
        assertThat(calculator).doesNotHaveToString("ok");
        assertThat(calculator).hasSameClassAs(calculator);
        assertThat(calculator).hasSameHashCodeAs(calculator);
        assertThat(calculator).doesNotHaveSameClassAs("immutable String object");
        assertThat(calculator).doesNotHaveSameHashCodeAs(testCalc);

        assertThat(calculator).isOfAnyClassIn(Calculator.class, CalculatorInheritor.class);
    }

// ------------------------------------ INSTANCE_OF ------------------------------------
    @Test
    public void dividingMustReturnIntegerInstance() {
        assertThat(calculator.divide(9,2)).isInstanceOf(Integer.class);
    }

// ------------------------------------ THROWS ------------------------------------
    @Test
    public void divideByZeroMustThrowException() {
        assertThatThrownBy(() -> calculator.divide(7,0)).isInstanceOf(Exception.class);
    }


}
