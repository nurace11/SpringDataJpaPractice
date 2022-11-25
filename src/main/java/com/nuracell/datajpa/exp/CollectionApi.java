package com.nuracell.datajpa.exp;

import java.util.*;

class CollectionApi {
    public static void main(String[] args) {
//        queue();

//        sets();

        hashMap();

        // Ctrl + Alt + M -> method extract
    }

    // Map (key value pairs)
    // HashTable(null not allowed, synchronized),
    // HashMap <- LinkedHashMap (null allowed, DoublyLinkedList  based implementation),
    // SortedMap <- NavigableMap <- TreeMap (sorted, null not allowed)

    // HashMap
    public static void hashMap() {
        Map<Integer, Employee> map = new HashMap<>();
        map.put(1, new Employee("Robert"));
        map.put(2, new Employee("Michelangelo"));
        map.put(3, new Employee("Alexa"));
        map.put(3, new Employee("AlexaA"));// overrides 3rd key's value

        System.out.println(map.get(1));
        System.out.println(map.containsKey(4));

        System.out.println("entrySet " + map.entrySet());
        System.out.println("keySet " + map.keySet());
        System.out.println("values " + map.values());

        map.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
//        Map.Entry - key value pair

        map.remove(3);
        map.forEach((key, employee) -> System.out.println(key + " " + employee));

        map.computeIfAbsent(3, (key) -> new Employee("Luna"));
        System.out.println(map);
        map.computeIfPresent(3, (key, value) -> new Employee("Ms. " + value.name));
        System.out.println(map);

        map.compute(4, (key, val) -> new Employee("Oracle"));
        System.out.println(map);
        map.compute(4, (key, val) -> new Employee(val.name() + " Yade") );
        System.out.println(map);

        map.clear();
        System.out.println("cleared map " + map);
        int key = 5;
        System.out.println(key + " " + map.getOrDefault(key, new Employee("Default")));
    }

    record Employee(String name){
    }




    // Set - Order is not guaranteed
    // duplicates are not allowed (with override equals() and hashcode() class objects)
    public static void sets() {
        Set<Ball> ballSet = new HashSet<>(); // backed by HashMap. TreeHash backed by TreeMap
        ballSet.add(new Ball("red"));
        ballSet.add(new Ball("red"));
        ballSet.add(new Ball("greed"));
        ballSet.add(new Ball("blue"));

        ballSet.remove(new Ball("red"));

        System.out.println("size " + ballSet.size());
        ballSet.forEach(System.out::println);
    }

    record Ball(String color){}

    // Queue interface (extends Collection)
    // FIFO
    public static void queue() {
        Queue<Person> supermarket = new LinkedList<>();
        supermarket.add(new Person("Kira", 19));
        supermarket.add(new Person("Light", 31));// Shift + Ctrl + ↑/↓ swap lines
        supermarket.add(new Person("Obama", 17));
        supermarket.offer(new Person("Mariam", 26));// use offer() when queue is capacity-restricted to prevent  from throwing an exception

        System.out.println(supermarket.peek()); // returns first element in the queue
        System.out.println(supermarket.poll()); // returns and Removes first element in the queue

        int supermarketSize = supermarket.size();
        for(int i = 0; i < supermarketSize; i++) {
            System.out.println("poll() " + supermarket.poll() + " i = " + i);
        }
    }

    static record Person(String name, int age){}

    // Stack extends Vector that implements List
    // LIFO
    public static void stackAndVector() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack);
        int stackSize = stack.size();
        for(int i = 0; i < stackSize; i++) {
            System.out.println("pop(): " + stack.pop() + " i = " + i);
        }

        // press Shift + Ctrl + Space -> shows all implementations
        // List<String> list =

        // Vector is synchronized. If a thread-safe implementation is not needed, it is recommended to use ArrayList in place of Vector.
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.addElement("qwe");
        System.out.println(vector);
    }
}




