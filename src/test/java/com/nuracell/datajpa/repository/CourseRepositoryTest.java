package com.nuracell.datajpa.repository;

import com.nuracell.datajpa.entity.Course;
import com.nuracell.datajpa.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository repository;

    @Test
    public void printCourses() {
        List<Course> courseList = repository.findAll();
        System.out.println(courseList);
    }

    @Test
    public void saveCourseWIthTeacher() {
        Teacher teacher = Teacher.builder()
                .firstName("L01")
                .lastName("Bo")
                .build();

        Course course = Course
                .builder()
                .title("React")
                .credit(5)
                .teacher(teacher)
                .build();

        repository.save(course);
    }

    @Test
    public void findAllPagination() {
        Pageable firstPageWithThreeRecords =
                PageRequest.of(0,3);
        Pageable secondPageWithTwoRecords =
                PageRequest.of(1, 2);

        List<Course> courses =
                repository.findAll(firstPageWithThreeRecords).getContent();
        long totalElements = repository.findAll(firstPageWithThreeRecords)
                .getTotalElements();
        long totalPages = repository.findAll(firstPageWithThreeRecords)
                        .getTotalPages();

        System.out.println("totalElements = " + totalElements);

        System.out.println("totalPages = " + totalPages);

        System.out.println("courses = " + courses);

        System.out.println(repository.findAll(secondPageWithTwoRecords).getContent());
        System.out.println(repository.findAll(secondPageWithTwoRecords).getTotalElements());
        System.out.println(repository.findAll(secondPageWithTwoRecords).getTotalPages());
    }

    @Test
    public void findAllSorting() {
        Pageable sortByTitle =
                PageRequest.of(
                        0,
                        2,
                        Sort.by("title")
                );

        Pageable sortByCreditDesc =
                PageRequest.of(
                        0,
                        2,
                        Sort.by("credit").descending()
                );

        Pageable sortByTitleAndCreditDesc =
                PageRequest.of(
                        0,
                        2,
                        Sort.by("title")
                                .descending()
                                .and(Sort.by("credit"))
                );

        Page<Course> coursePage = repository.findAll(sortByTitle);
        System.out.println(coursePage.getContent() + " " + coursePage.getNumber());

//        List<Course> courses = repository.findAll(sortByTitle).getContent();
//        System.out.println(courses);
    }

    @Test
    public void printFindByTitleContaining() {
        Pageable firstPageTenRecords = PageRequest.of(0, 10);
        List<Course> courses = repository.findByTitleContaining("D", firstPageTenRecords).getContent();

        System.out.println(courses);
    }

    @Test
    public void getAllPageable() {
        int size = 2;
        Page<Course> coursePage = repository.findAll(PageRequest.of(0,size));

        System.out.println("coursePage.getTotalPages() = " + coursePage.getTotalPages());
        System.out.println("coursePage.getTotalElements() = " + coursePage.getTotalElements());
        System.out.println(coursePage.getContent());

        IntStream intStream = IntStream.range(0, coursePage.getTotalPages());

        if(coursePage.getTotalPages() > 0) {
            intStream.forEach(
                    e -> {
                        System.out.println(e + " " + repository.findAll(PageRequest.of(e, size)).getContent());
                    }
            );
        }
    }

    @Test
    public void getAllPageableSortedByTitle() {
        int size = 2;
        Sort sortByTitle = Sort.by("title");
        Sort sortByTitleDesc = Sort.by("title").descending();
        Page<Course> coursePage = repository.findAll(PageRequest.of(0, size, sortByTitleDesc));

        IntStream intStream = IntStream.range(0, coursePage.getTotalPages());

        if(coursePage.getTotalPages() > 0) {
            intStream.forEach(
                    e -> {
                        System.out.println(e + " " + repository.findAll(PageRequest.of(e, size, sortByTitleDesc)).getContent());
                    }
            );
        }
    }

    //        System.out.println(courseClass.getDeclaredFields()[0].toString().substring(courseClass.getDeclaredFields()[0].toString().lastIndexOf("."))); // ong com.nuracell.datajpa.entity.Course.courseId
    @Test
    public void getAllPageableSortedByEverything() {
        Class<Course> courseClass = Course.class;

        List<String> fields = Arrays.stream(courseClass.getDeclaredFields())
                .map(
                        e -> {
                            String s = e.toString();
                            return s.substring(s.lastIndexOf(".") + 1);
                        }
                )
                .toList();

        List<Sort> sorts = new ArrayList<>(fields.stream().map(Sort::by).toList()); //fields.stream().map(Sort::by).toList();

/*        List<Sort> sortsDesc = fields
                .stream()
                .map(e -> Sort.by(e).descending())
                .toList();*/

        sorts.addAll(fields
                .stream()
                .map(e -> Sort.by(e).descending())
                .toList());

        int size = 2;
        Page<Course> coursePage = repository.findAll(PageRequest.of(0, size));
        AtomicReference<IntStream> intStream = new AtomicReference<>(IntStream.range(0, coursePage.getTotalPages()));

        if(coursePage.getTotalPages() > 0) {
            sorts.forEach(
                    sort -> {
                        System.out.println("\n SORTING: " + sort +  "");
                        intStream.set(IntStream.range(0, coursePage.getTotalPages()));
                        intStream.get().forEach(
                                e -> System.out.println(e + " " + repository.findAll(PageRequest.of(e, size, sort)).getContent())
                        );
                        System.out.println("Sort: " + sort +  " DONE \n");
                    }

            );
        }
    }
}