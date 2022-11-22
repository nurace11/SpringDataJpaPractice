package com.nuracell.datajpa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {/*
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfig.class);

        RestTemplate restTemplate = context.getBean("restTemplate", RestTemplate.class);
        System.out.println("restTemplate = " + restTemplate);
//        System.out.println(restTemplate.getForObject());*/

        Random random = new Random();

        int[] ints = random.ints(10, 0, 3).toArray();
        List<Integer> intsList;

        System.out.println("Max = " + random.ints(10, -100, 100)
                .map(
                e -> {
                    System.out.println(e);
                    return e;
                }
        ).max()
                .orElse(-1) + "\n");

        List<Integer> integerList = random.ints(20, -100, 100).boxed().collect(Collectors.toList());
        System.out.println(integerList);
        System.out.println(integerList.stream().max(Comparator.naturalOrder()));

        Iterator<String> iterator;
    }
}
