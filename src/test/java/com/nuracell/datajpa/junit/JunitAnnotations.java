package com.nuracell.datajpa.junit;


import com.nuracell.datajpa.junit.extension.CalculatorExtension;
import com.nuracell.datajpa.junit.extension.TimingExtension;
import org.aspectj.weaver.Iterators;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.*;

//@SpringBootTest
//@DataJpaTest
//https://junit.org/junit5/docs/current/user-guide/#writing-tests

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnabledOnOs(OS.WINDOWS)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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



// ------------------------------------------------ @TempDir IO tests ------------------------------------------------
    @Test
    void writeItemsToFile(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.txt");

        BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(file));
        writer.write("a");
        writer.write("b");
        writer.write("c");
        writer.flush();

        assertThat("abc").isEqualTo(Files.readAllLines(file).get(0));
    }

// ------------------------------------------------ @EnabledOnOs @DisabledOnOs ------------------------------------------------
    @DisabledOnOs({OS.WINDOWS, OS.AIX, OS.MAC, OS.LINUX, OS.OTHER, OS.SOLARIS})
    @Test
    void disabledOnAllOS() throws InterruptedException {
        Thread.sleep(50);
    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void enablesOnWindows() throws InterruptedException {
        Thread.sleep(50);
    }

    @EnabledOnOs(OS.LINUX) // Test passes. @EnabledOnOs(OS.WINDOWS) annotation is on the class
    @Test
    void enabledOnLinux() throws InterruptedException {
        Thread.sleep(50);
    }

// ------------------------------------------------ @ExtendWith ------------------------------------------------
    @Test
    @ExtendWith({TimingExtension.class})
    void extendWithSpringExtensionClassTest() throws InterruptedException {
        Thread.sleep(100);
    }



// ------------------------------------------------ @Timeout ------------------------------------------------
    @Test
    @Timeout(unit = TimeUnit.MILLISECONDS, value = 5)
    void timeoutTestOneMillionIterations() {
        System.out.println("for loop 1 million iterations");
        for(int i = 0; i < 1_000_000; i++) {}
    }

    @Test
    @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
    void timeoutTestOneBillionIterations() {
        System.out.println("for loop 1 billion iterations");
        for(int i = 0; i < 1_000_000_000; i++) {}
    }

// ------------------------------------------------ @Tag ------------------------------------------------
    // Test classes and methods can be tagged via the @Tag annotation. Those tags can later be used to filter test discovery and execution.
    @Tag("firstTag")
    @Test
    void taggedMethodTest(){

    }
    @Tag("fast")
    @Tag("Model")
    @Tag("thirdTag")
    @DisplayName("TaggedTestClass")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TaggedTestClass {
        static TaggedTestClass taggedTestClassVariable;

        public TaggedTestClass() {
            taggedTestClassVariable = this;
        }

        @Test
        @Tag("taxes")
        void testingTaxCalculation(){}

        @BeforeAll
        static void beforeAllTaggedTestClassMethod(){
            System.out.println("beforeAllTaggedTestClassMethod " + taggedTestClassVariable.getClass().getName() );
        }
    }

// ------------------------------------------------ @BeforeEach @BeforeAll @AfterEach @AfterAll ------------------------------------------------
    // @BeforeAll method 'public void com.nuracell.datajpa.junit.JunitAnnotations.beforeAllMethodTest()' must be static unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS)
    @BeforeAll
    static void beforeAllMethodTest() {
        System.out.println("BeforeAll");
    }

    // if test class is not annotated with @TestInstance(TestInstance.Lifecycle.PER_CLASS), count must be static
    static int count;
    @BeforeEach
    void beforeEachTest() {
        System.out.println("Before test number " + ++count);
    }

    @AfterEach
    void afterEachTest() {
        System.out.println("(AfterEach) Test number " + count + " done");
    }

    @AfterAll
    static void afterAllMethod() {
        System.out.println("AfterAll. The end of testing");
    }

    @Test
    @Disabled
    void failingTest() {
        fail("failingTest");
    }

// ------------------------------------------------ @TestInstance (class annotation) ------------------------------------------------
    // Used to configure the test instance lifecycle for the annotated test class
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TestOfTheTestClass {
        @BeforeAll
        public void passingTest() {
            System.out.println("BeforeAll TestOfTheTestClass");
        }

        @Test
        void test(){

        }

    }

// ------------------------------------------------ @TestMethodOrder (class annotation) ------------------------------------------------
    @Order(5)
    @Test
    @DisplayName("fifthOrderedTestMethod ╯°□°）╯")
    public void fifthOrderedTestMethod() {
    }

    @Order(99)
    @Test
    @DisplayName("ninetyNineOrderedTestMethod \uD83D\uDE31")
    public void ninetyNineOrderedTestMethod() {

    }


// ------------------------------------------------ @TestClassOrder (class annotation) ------------------------------------------------

    @Nested
    @Order(1)
    class PrimaryTests{
        @Test
        public void test1() {

        }
    }

    @Nested
    @Order(2)
    class SecondaryTests{
        @Test
        public void test2() {

        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @TestFactory
    DynamicTest testFactoryOfMostAnnotatedTestMethod() {
        return DynamicTest.dynamicTest("DynamicTest of MATM", () -> mostAnnotatedTestMethod(7, Path.of("")));
    }
    @Order(Integer.MIN_VALUE)
    @Tag("NIO")
    @DisplayName("Most annotated IO Test")
    @EnabledOnOs(OS.WINDOWS)
    @DisabledOnOs(OS.AIX)
    @DisabledOnJre(JRE.JAVA_9)
    @ExtendWith(TimingExtension.class)
    @TestTemplate
    @Timeout(value = 730, unit = TimeUnit.DAYS)
    @ParameterizedTest
    @ValueSource(ints = {1,1,1,1})
    void mostAnnotatedTestMethod(int ints, @TempDir Path tempDir) throws IOException {
        System.out.println(ints);
        Path file = tempDir.resolve("test.txt");

        BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(file));
        writer.write("a");
        writer.write("b");
        writer.write("c");
        writer.flush();

        assertThat("abc").isEqualTo(Files.readAllLines(file).get(0));
    }

// Annotations LEFT
/*
     * @DisplayNameGeneration
*/


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
