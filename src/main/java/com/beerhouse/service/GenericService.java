package com.beerhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class GenericService<T> {
	@Autowired
    private JpaRepository<T, Long> repository;
     
    public List<T> findAll() {
        return repository.findAll();
    }
     
    public T findOne(Long id) {
        return repository.findOne(id);
    }
     
    public T save(T post) {
        return repository.save(post);
    }
    
    public void deleteAll() {
    	repository.deleteAll();
    }
}