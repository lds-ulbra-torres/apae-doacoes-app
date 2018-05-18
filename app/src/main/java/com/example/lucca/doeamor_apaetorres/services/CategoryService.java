package com.example.lucca.doeamor_apaetorres.services;

import com.example.lucca.doeamor_apaetorres.dto.CategoryDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("category")
    Call <CategoryDTO> getCategories();
}
