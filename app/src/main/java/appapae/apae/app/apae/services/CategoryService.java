package appapae.apae.app.apae.services;

import appapae.apae.app.apae.dto.CategoryDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("category")
    Call <CategoryDTO> getCategories();
}