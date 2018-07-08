package com.apae.lucca.apae_torres.services;

import com.apae.lucca.apae_torres.dto.DetailDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DetailService {
    @GET("v2/partner/{id}")
    Call<DetailDTO> getDetailPartners(@Path("id")String idDetail);
}
