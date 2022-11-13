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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

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
}