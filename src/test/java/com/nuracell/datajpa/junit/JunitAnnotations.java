package com.nuracell.datajpa.junit;


import org.aspectj.weaver.Iterators;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.*;

//@SpringBootTest
//@DataJpaTest
//https://junit.org/junit5/docs/current/user-guide/#writing-tests
public class JunitAnnotations {
    @Test
    public void justASimpleTest() {
    }

// ------------------------------------------------ @TEST_FACTORY ------------------------------------------------
    @TestFactory
    public DynamicTest testFactoryDynamicTest() {
        return DynamicTest.dynamicTest("Dynamic Test", () -> {});
    }

    @TestFactory
    public DynamicContainer testFactoryDynamicContainer() {
        return DynamicContainer.dynamicContainer("Dynamic Container", testFactoryDynamicTestStream());
    }

    @TestFactory
    public Stream<DynamicNode> testFactoryDynamicNodeStream() {
        return Stream.of(
                DynamicTest.dynamicTest("repeatedTest", this::repeatedTest),
                DynamicContainer.dynamicContainer("Dynamic Container", testFactoryDynamicTestStream())
        );
    }

    @TestFactory
    public Stream<DynamicTest> testFactoryDynamicTestStream() {
        return Stream.of(
                DynamicTest.dynamicTest("repeatedTest", this::repeatedTest),
                DynamicTest.dynamicTest("repeatedTestTwoTimes", this::repeatedTestTwoTimes)//,
//                DynamicTest.dynamicTest("failingTest", this::failingTest)
        );
    }

    @TestFactory
    public Stream<DynamicContainer> testFactoryDynamicContainerStream() {
        return Stream.of(testFactoryDynamicContainer(), testFactoryDynamicContainer(), testFactoryDynamicContainer());
    }

    @TestFactory
    public List<DynamicTest> testFactoryDynamicTestList() {
        return List.of(
                testFactoryDynamicTest(),
                DynamicTest.dynamicTest("DynamicTest2 in the list", this::justASimpleTest)
        );
    }

    @TestFactory
    public Collection<DynamicTest> testFactoryDynamicTestCollection() {
        return new ArrayList<>(){{
            add(testFactoryDynamicTest());
            add(DynamicTest.dynamicTest("DynamicTest repeatedTest in arrayList", () -> repeatedTest()));
            add(DynamicTest.dynamicTest("DynamicTest justASimpleTest in arrayList", () -> justASimpleTest()));
        }};
    }

    @TestFactory
    public Iterable<DynamicTest> testFactoryDynamicTestIterable() {
        return new HashSet<>(){{
            add(testFactoryDynamicTest());
            add(DynamicTest.dynamicTest("DynamicTest repeatedTest in arrayList", () -> repeatedTest()));
            add(DynamicTest.dynamicTest("DynamicTest justASimpleTest in arrayList", () -> justASimpleTest()));
        }};
    }

    @TestFactory
    public Stream<DynamicContainer> summaryOfTestFactories() {
        return Stream.of(
                DynamicContainer.dynamicContainer("DynamicTest Iterable (HashSet)", testFactoryDynamicTestIterable()),
                DynamicContainer.dynamicContainer("DynamicTest Collection (ArrayList)", testFactoryDynamicTestCollection()),
                DynamicContainer.dynamicContainer("DynamicTest List (ImmutableList)", testFactoryDynamicTestList()),
                DynamicContainer.dynamicContainer("DynamicTest Stream ", testFactoryDynamicTestStream()),
                DynamicContainer.dynamicContainer("DynamicContainer Stream ", testFactoryDynamicContainerStream()),
                DynamicContainer.dynamicContainer("DynamicNode Stream", testFactoryDynamicNodeStream())
        );
    }

// ------------------------------------------------ @REPEATED TEST ------------------------------------------------
    @RepeatedTest(value = 5, name = "{displayName} Test {currentRepetition} of {totalRepetitions}")
    public void repeatedTest() {
        assertThat(true).isTrue();
    }

    @RepeatedTest(2)
    public void repeatedTestTwoTimes() {
        assertThat(true).isTrue();
    }


// ------------------------------------------------ @PARAMETERIZED TEST ------------------------------------------------
    @ParameterizedTest
    @ValueSource(strings = {"Matrix", "loopa", "space", "back"})
    public void parameterizedTest(String parameter) {
        System.out.println(parameter);
        assertThat(parameter.contains("a")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 5})
    public void parameterizedTest(int number) {
        System.out.println(number);
        assertThat(number % 5).isEqualTo(0);
    }

    @Test
    public void failingTest() {
        assertThat(1).isEqualTo(0);
    }

// Annotations LEFT
/* @TestClassORder
* @TestMethodOrder
* @TestInstance
* @DisplayName
* @DisplayNameGeneration
* @BeforeEach
* @AfterEach
* @BeforeAll
* @AfterAll
* @Netser
* @Tag
* @Disabled
* @Timeout
* @ExtendWith
* @RegisterExtension
* @TempDir */

// ------------------------------------------------ @TEST_TEMPLATE ------------------------------------------------
// https://junit.org/junit5/docs/current/user-guide/#extensions-test-templates
    final List<String> fruits = Arrays.asList("apple", "banana", "lemon");

    @TestTemplate
    @ExtendWith(MyTestTemplateInvocationContextProvider.class)
    void testTemplate(String fruit) {
        assertThat(fruits.contains(fruit)).isTrue();
    }

}

class MyTestTemplateInvocationContextProvider implements TestTemplateInvocationContextProvider {
    @Override
    public boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        return Stream.of(invocationContext("apple"), invocationContext("banana"));
    }

    private TestTemplateInvocationContext invocationContext(String parameter) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return parameter;
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return Collections.singletonList(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(ParameterContext parameterContext,
                                                     ExtensionContext extensionContext) {
                        return parameterContext.getParameter().getType().equals(String.class);
                    }

                    @Override
                    public Object resolveParameter(ParameterContext parameterContext,
                                                   ExtensionContext extensionContext) {
                        return parameter;
                    }
                });
            }
        };
    }
}
