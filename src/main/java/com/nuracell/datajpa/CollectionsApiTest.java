package com.nuracell.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class CollectionsApiTest {
    public CollectionsApiTest(){
        instance = this;
    }
    static CollectionsApiTest instance;
    public static void main(String[] args) {
        new CollectionsApiTest();

        Iterable<String> iterable = List.of("qwe");
        System.out.println(iterable);

        System.out.println(instance.lo().getClass().getName() + " " + instance.getClass().getAnnotatedSuperclass());
        System.out.println(new Lo().lo().getClass().getName() + " " + Lo.class.getAnnotatedSuperclass());

        System.out.println();
    }

    public Number lo(){
        return 5;
    }
}

class Lo extends CollectionsApiTest{
    public Double lo(){
        return 10.0;
    }
}

interface Fa{
    Iterable<String> findAll();
}

interface FaCollection extends Fa {
    Collection<String> findAll();
}

interface FaList extends FaCollection {
    List<String> findAll();
}


class StaticBinding {
    public static void main(String[] args) {
        Iter coll = new Coll();
        coll.printDynamicDependsOnObject();
        coll.printStaticDependsOnClass(); // = Iter.printStaticDependsOnClass();

        Iter iter = new Iter();
        iter.printDynamicDependsOnObject();
        iter.printStaticDependsOnClass();

        CollectionsApiTest.main(new String[]{});
        System.out.println("sooka");

        Callable<Object> callable = new Callable<Object>() {
            public Object call() throws Exception {
                return new Object();
            }
        };
    }
}

class Iter {
    public static void printStaticDependsOnClass() {
        System.out.println("ITER static");
    }
    public void printDynamicDependsOnObject() {
        System.out.println("ITER");
    }
}

class Coll extends Iter {
    public static void printStaticDependsOnClass() {
        System.out.println("Coll static");
    }
    public void printDynamicDependsOnObject() {
        System.out.println("Coll");
    }
}
