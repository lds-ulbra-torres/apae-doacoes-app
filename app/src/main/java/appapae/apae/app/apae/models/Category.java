package appapae.apae.app.apae.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by lucca on 09/04/2018.
 */

public class Category implements Serializable {
    @JsonProperty("id_category")
    private long id_category;
    @JsonProperty("name_category")
    private String name_category;
    @JsonProperty("description_category")
    private String description_category;
    @JsonProperty("photo_category")
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
