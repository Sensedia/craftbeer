package com.beerhouse.service;

import com.beerhouse.dto.BeerRequest;
import com.beerhouse.dto.BeerResourceRequest;
import com.beerhouse.exception.BeerNotFound;
import com.beerhouse.model.Beer;
import com.beerhouse.repository.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BeerService  extends GenericService<Beer>{

    @Autowired
    private BeerRepository beerRepository;

    public void create(BeerResourceRequest beerRequest){
     Beer beer = beerRequest.convertAsObject();
     beerRepository.saveAndFlush(beer);
    }

    public List<Beer> list(){
       log.info("List beer");
       return beerRepository.findAll();
    }

    public Beer recover(Long id) throws BeerNotFound {
      log.info("Recover beer: "+id);
      Optional<Beer> beer =  beerRepository.findById(id);

      if (beer.isPresent()){

          return  beer.get();
      }else{
          throw new BeerNotFound();
      }
    }

    public void edit(Long beerId, BeerResourceRequest beerResourceRequest){
        log.info("Edit beer: "+beerId);
        Optional<Beer> beer = beerRepository.findById(beerId);

        if (beer.isPresent()) {
            Beer beerEdit = beerResourceRequest.convertAsObject();
            beerEdit.setId(beer.get().getId());
            beerRepository.saveAndFlush(beerEdit);
        }
    }

    public void edit(Long beerId, BeerRequest beerRequest){
        log.info("Edit beer: "+beerId);
        Optional<Beer> beer = beerRepository.findById(beerId);

        if (beer.isPresent()) {
           Beer beerEdit = beer.get();
           beerEdit.setName(beerRequest.getName());
           beerEdit.setIngredients(beerRequest.getIngredients());
           beerRepository.saveAndFlush(beerEdit);
        }
    }

    public void delete(Long beerId) throws BeerNotFound {
        log.info("Delete beer: "+beerId);
        beerRepository.delete(recover(beerId));
    }
}
