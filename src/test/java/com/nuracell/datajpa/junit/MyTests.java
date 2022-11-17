package com.nuracell.datajpa.junit;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MyTests {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void collectionAPITest() {
/*        Iterable<String> iterable = new Iterable() {
            @Override
            public Iterator iterator() {
                return new ArrayList<String>(){{
                    add("MegaBus");
                    add("cactus");
                    add("Pool");
                }}.iterator();
            }
        };*/

        Iterable<String> iter = () -> new ArrayList<String>(){{
            add("MegaBus");
            add("cactus");
            add("Pool");
        }}.iterator();

        for(String s : iter) {
            System.out.println(s);
        }

        System.out.println(iter.getClass().getName());
    }
}
