package com.apae.lucca.apae_torres.services;

import com.apae.lucca.apae_torres.dto.CategoryDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("category")
    Call <CategoryDTO> getCategories();
}