package appapae.apae.app.apae.dto;

import appapae.apae.app.apae.models.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
public class CategoryDTO {

    @JsonProperty("category")
    private ArrayList<Category> category;

    public ArrayList<Category> getCategory(){
        return category;
    }
}