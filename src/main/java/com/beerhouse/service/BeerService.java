package com.beerhouse.service;

import com.beerhouse.dto.BeerRequest;
import com.beerhouse.exception.BeerNotFound;
import com.beerhouse.model.Beer;
import com.beerhouse.repository.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BeerService  extends GenericService<Beer>{

    @Autowired
    private BeerRepository beerRepository;

    public void create(BeerRequest beerRequest){
     Beer beer = beerRequest.convertAsObject();
     beerRepository.saveAndFlush(beer);
    }

    public List<Beer> list(){
       log.info("List beer");
       return beerRepository.findAll();
    }

    public Beer recover(Long id) throws BeerNotFound {
        log.info("Recover beer: " + id);
        return beerRepository.findById(id).orElseThrow(BeerNotFound::new);
    }


    public void edit(Long beerId, BeerRequest beerResourceRequest) throws BeerNotFound {
        log.info("Edit beer: "+beerId);
        Beer beer = beerRepository.findById(beerId).orElseThrow(BeerNotFound::new);

        Beer beerEdit = beerResourceRequest.convertAsObject();
        beerEdit.setId(beer.getId());
        beerRepository.saveAndFlush(beerEdit);
    }

    public void edit(Long beerId, Map<String, Object> fields) throws BeerNotFound {
        log.info("Edit beer: "+beerId);
        Beer beer = beerRepository.findById(beerId).orElseThrow(BeerNotFound::new);

        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Beer.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, beer, v);
        });

        beerRepository.saveAndFlush(beer);
    }

    public void delete(Long beerId) throws BeerNotFound {
        beerRepository.delete(recover(beerId));
        log.info("Delete beer: "+beerId);
    }
}
