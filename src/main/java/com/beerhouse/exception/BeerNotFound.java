package com.beerhouse.exception;

public class BeerNotFound extends Exception {
    public BeerNotFound() {
        super("Beer not found");
    }
}
