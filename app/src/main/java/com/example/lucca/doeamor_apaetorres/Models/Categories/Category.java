package com.example.lucca.doeamor_apaetorres.Models.Categories;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lucca on 09/04/2018.
 */

public class Category {



    @SerializedName("id_category")

    private int idCategory;
    @SerializedName("name_category")
    private String name_category;
    @SerializedName("description")
    private String description;
    @SerializedName("photo_category")
    private String photo_cat;

    public int getId(){
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameCat() {
        return name_category;
    }

    public void setNameCat(String name_category) {
        this.name_category = name_category;
    }

    public String getPhotoCat() {

        return photo_cat;
    }

    public void setPhotoCat(String photoCat) {
        this.photo_cat = photoCat;
    }
}
