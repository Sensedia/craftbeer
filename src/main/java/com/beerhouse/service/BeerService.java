package com.beerhouse.service;

import com.beerhouse.entity.Beer;
import com.beerhouse.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeerService {

    @Autowired
    private BeerRepository beerRepository;

    public Beer save (Beer beer) {
        return beerRepository.save(beer);
    }

    public List <Beer> beerList() {
        return beerRepository.findAll();
    }

    public Optional <Beer> findById(Integer id) {
        return beerRepository.findById(id);
    }

    public void removeById(Integer id) {
        beerRepository.deleteAll();
    }
}
