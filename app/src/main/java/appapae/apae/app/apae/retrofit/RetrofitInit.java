package appapae.apae.app.apae.retrofit;

import appapae.apae.app.apae.services.CategoryService;
import appapae.apae.app.apae.services.DetailService;
import appapae.apae.app.apae.services.PartnerService;


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
