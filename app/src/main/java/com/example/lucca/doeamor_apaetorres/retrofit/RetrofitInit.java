package com.example.lucca.doeamor_apaetorres.retrofit;

import com.example.lucca.doeamor_apaetorres.services.CategoryService;
import com.example.lucca.doeamor_apaetorres.services.PartnerService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInit {

    private final Retrofit retrofit;
    public RetrofitInit(){
       retrofit = new Retrofit.Builder().baseUrl("http://apaetorres.org.br/doacoes/api/")
               .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public CategoryService getCategoryService() {
        return retrofit.create(CategoryService.class);
    }

    public PartnerService getPartnerService(){
        return retrofit.create(PartnerService.class);
    }
}
