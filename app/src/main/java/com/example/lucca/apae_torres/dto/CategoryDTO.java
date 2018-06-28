package com.example.lucca.apae_torres.dto;

import com.example.lucca.apae_torres.models.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
public class CategoryDTO {

    @JsonProperty("category")
    private ArrayList<Category> category;

    public ArrayList<Category> getCategory(){
        return category;
    }
}