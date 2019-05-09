package appapae.apae.app.apae.services;

import appapae.apae.app.apae.dto.PartnerDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PartnerService {

    @GET("partnerByCategory/{id}")
    Call <PartnerDTO> getPartners(@Path("id")String idCat);
}
