package belajar.springdatajpa.repository;

import belajar.springdatajpa.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void insert() {
        Category category = new Category();
        category.setName("GADGET");

        categoryRepository.save(category);
        Assertions.assertNotNull(category.getId());
    }

    @Test
    void update() {
        Category category = categoryRepository.findById(2L).orElse(null);
        Assertions.assertNotNull(category);

        category.setName("GADGET MURAH SEKALI");
        categoryRepository.save(category);

        category = categoryRepository.findById(2L).orElse(null);
        Assertions.assertNotNull(category);
        Assertions.assertEquals("GADGET MURAH SEKALI", category.getName());
    }

    @Test
    void delete() {
        Category category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(category);

        categoryRepository.delete(category);
    }

    @Test
    void testMethodQuery() {
        Category category = categoryRepository.findFirstByNameEquals("GADGET MURAH SEKALI").orElse(null);
        assertNotNull(category);
        assertEquals("GADGET MURAH SEKALI", category.getName());

        List<Category> categoryList = categoryRepository.findAllByNameLike("%GADGET%");
        assertEquals(2, categoryList.size());
        assertEquals("GADGET MURAH SEKALI", categoryList.get(0).getName());
        assertEquals("GADGET CCUY", categoryList.get(1).getName());
    }

    @Test
    void audit() {
        Category category = new Category();
        category.setName("Sample Audit");
        categoryRepository.save(category);

        assertNotNull(category.getId());
        assertNotNull(category.getCreatedDate());
        assertNotNull(category.getLastModifiedDate());
    }

    @Test
    void example1() {
        Category category = new Category();
        category.setName("GADGET MURAH SEKALI");

        Example<Category> example = Example.of(category);

        List<Category> categories = categoryRepository.findAll(example);
        assertEquals(1, categories.size());
    }

    @Test
    void exampleMatcher() {
        Category category = new Category();
        category.setName("gadget MURAH SEKALI");

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase();

        Example<Category> example = Example.of(category, matcher);

        List<Category> categories = categoryRepository.findAll(example);
        assertEquals(1, categories.size());
    }
}