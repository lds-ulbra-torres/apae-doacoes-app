package com.example.lucca.apae_torres.services;

import com.example.lucca.apae_torres.dto.CategoryDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("category")
    Call <CategoryDTO> getCategories();
}