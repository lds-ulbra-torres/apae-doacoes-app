package com.example.lucca.apae_torres.retrofit;

import com.example.lucca.apae_torres.services.CategoryService;
import com.example.lucca.apae_torres.services.DetailService;
import com.example.lucca.apae_torres.services.PartnerService;


import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInit {

    private final Retrofit retrofit;
    public RetrofitInit(){
       retrofit = new Retrofit.Builder().baseUrl("http://doacoes.apaetorres.org.br/api/")
               .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public CategoryService getCategoryService() {
        return retrofit.create(CategoryService.class);
    }
    public PartnerService getPartnerService(){
        return retrofit.create(PartnerService.class);
    }
    public DetailService getDetailService(){
        return retrofit.create(DetailService.class);
    }
}
