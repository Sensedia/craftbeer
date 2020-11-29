package com.beerhouse.controller;

import com.beerhouse.dto.BeerRequest;
import com.beerhouse.exception.BeerNotFound;
import com.beerhouse.model.Beer;
import com.beerhouse.service.BeerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/beers")
@Api(tags = "Beers", description = "Related operations like beers")
public class BeerController {

    @Autowired
    private BeerService beerService;


    @ApiOperation(value = "Create Beer", response = Beer.class)
    @RequestMapping(method= RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE} )
    public ResponseEntity<?> createBeer(@Valid @RequestBody BeerRequest beerRequest) {
        beerService.create(beerRequest);
        return new ResponseEntity<Object>(null, HttpStatus.CREATED);
    }

    @ApiOperation(value = "List Beer")
    @RequestMapping(method= RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Beer>> listBeer() {
        return new ResponseEntity<>(beerService.list(), HttpStatus.OK);
    }

    @ApiOperation(value = "Recover Beer", response = Beer.class)
    @RequestMapping(value = "/{beerId}", method= RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Beer> recoverBeer(@PathVariable("beerId") Long beerId) throws BeerNotFound {
        return new ResponseEntity<>(beerService.recover(beerId), HttpStatus.OK);
    }

    @ApiOperation(value = "Edit Beer", response = Beer.class)
    @PatchMapping(value = "/{beerId}", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity editComplete(@PathVariable("beerId") Long beerId, @Valid @RequestBody Map<String, Object> fields) throws BeerNotFound {
        beerService.edit(beerId, fields);
        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

    @ApiOperation(value = "Edit Beer", response = Beer.class)
    @PutMapping(value = "/{beerId}", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity editPartial(@PathVariable("beerId") Long beerId, @Valid @RequestBody BeerRequest beerRequest) throws BeerNotFound {
        beerService.edit(beerId, beerRequest);
        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Beer", response = Beer.class)
    @DeleteMapping(value = "/{beerId}")
    public ResponseEntity<?> deleteBeer(@PathVariable("beerId") Long beerId) throws BeerNotFound {
        beerService.delete(beerId);
        return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
    }
}
