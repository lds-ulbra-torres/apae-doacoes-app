package com.example.lucca.doeamor_apaetorres.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

/**
 * Created by lucca on 09/04/2018.
 */

@JsonDeserialize
public class Category implements Serializable {

    @JsonDeserialize
    private long id_category;
    @JsonDeserialize
    private String name_category;
    @JsonDeserialize
    private String description_category;
    @JsonDeserialize
    private String photo_category;

    public long getId(){
        return id_category;
    }

    public void setId_category(long id_category) {
        this.id_category = id_category;
    }

    public String getDescription_category() {
        return description_category;
    }

    public void setDescription_category(String description_category) {
        this.description_category = description_category;
    }

    public String getNameCat() {
        return name_category;
    }

    public void setNameCat(String name_category) {
        this.name_category = name_category;
    }

    public String getPhotoCat() {
        return photo_category;
    }
    public void setPhotoCat(String photoCat) {
        this.photo_category = photoCat;
    }
}
