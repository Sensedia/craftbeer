package com.beerhouse.model;

public enum Flavor {

    SWEET(1, "Sweet"),
    BITCH(2, "Bitch"),
    STRONG(3, "Strong"),
    FRUITY(4, "Fruity"),
    SOFT(5, "Soft");

    public int code;
    public String description;

    Flavor(int code, String description){
        this.code = code;
        this.description = description;
    }
}
