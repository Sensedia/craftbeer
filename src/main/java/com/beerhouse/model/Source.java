package com.beerhouse.model;

public enum Source {

    NATIONAL(1 ,"National"),
    INTERNATIONAL(2, "International");

    public int code;
    public String description;

    Source(int code, String description){
        this.code = code;
        this.description = description;
    }
}
