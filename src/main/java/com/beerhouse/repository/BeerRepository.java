package com.beerhouse.repository;

import com.beerhouse.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Janaina Militão
 */
public interface BeerRepository extends JpaRepository<Beer, Long> {

    Optional<Beer> findById(Long id);

    Optional<Beer> findByName(String name);

    Optional<Beer> findByIngredients_Id(Long ingredientId);

}