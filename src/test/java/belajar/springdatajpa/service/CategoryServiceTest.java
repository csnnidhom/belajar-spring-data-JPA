package belajar.springdatajpa.service;

import belajar.springdatajpa.entity.Category;
import belajar.springdatajpa.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void success() {
        Assertions.assertThrows(RuntimeException.class, () ->{
            categoryService.create();
        });
    }

    @Test
    void failed() {
        Assertions.assertThrows(RuntimeException.class, () ->{
            categoryService.test();
        });
    }

    @Test
    void programmatic() {
        Assertions.assertThrows(RuntimeException.class, () ->{
            categoryService.createCategories();
        });
    }

    @Test
    void manual() {
        Assertions.assertThrows(RuntimeException.class, () ->{
            categoryService.createCategories();
        });
    }


}