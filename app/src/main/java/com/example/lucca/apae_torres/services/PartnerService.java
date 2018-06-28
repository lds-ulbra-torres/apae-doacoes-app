package com.example.lucca.apae_torres.services;

import com.example.lucca.apae_torres.dto.PartnerDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PartnerService {

    @GET("partnerByCategory/{id}")
    Call <PartnerDTO> getPartners(@Path("id")String idCat);
}
