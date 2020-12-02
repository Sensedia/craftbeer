package com.beerhouse.repository;

import com.beerhouse.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Janaina Militão
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

}
