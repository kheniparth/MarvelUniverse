package com.techmuzz.marveluniverse.models;

import com.squareup.moshi.Json;

public class Item {

    @Json(name = "resourceURI")
    private String resourceURI;
    @Json(name = "name")
    private String name;

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
