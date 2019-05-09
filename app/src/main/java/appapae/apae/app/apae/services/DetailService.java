package appapae.apae.app.apae.services;

import appapae.apae.app.apae.dto.DetailDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DetailService {
    @GET("v2/partner/{id}")
    Call<DetailDTO> getDetailPartners(@Path("id")String idDetail);
}
