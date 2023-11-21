package belajar.springdatajpa.repository;

import belajar.springdatajpa.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findFirstByNameEquals(String name);

    List<Category> findAllByNameLike(String name);

}
