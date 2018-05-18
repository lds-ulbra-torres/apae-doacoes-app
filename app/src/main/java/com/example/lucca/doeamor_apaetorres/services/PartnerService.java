package com.example.lucca.doeamor_apaetorres.services;

import com.example.lucca.doeamor_apaetorres.dto.PartnerDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PartnerService {

    @GET("partnerByCategory/{id}")
    Call <PartnerDTO> getPartners(@Path("id")String idCat);
}
