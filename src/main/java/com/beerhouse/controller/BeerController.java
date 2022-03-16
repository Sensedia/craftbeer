package com.beerhouse.controller;

import com.beerhouse.entity.Beer;
import com.beerhouse.service.BeerService;
import org.aspectj.asm.IModelFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/beers")
public class BeerController {

    @Autowired
    private BeerService beerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Beer save(@RequestBody Beer beer) {
        return beerService.save(beer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<Beer> beerList() {
        return beerService.beerList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Beer findBeerById(@PathVariable("id") Integer id) {
        return beerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cerveja não encontrada!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeBeer (@PathVariable("id") Integer id) {
        beerService.findById(id)
                .map(beer -> {
                    beerService.removeById(beer.getId());
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cerveja não encontrada!"));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeer (@PathVariable("id") Integer id, @RequestBody Beer beer) {
        beerService.findById(id)
                .map(beerBase -> {
                    modelMapper.map(beer, beerBase);
                    beerService.save(beerBase);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cerveja não encontrada!"));
    }
}
