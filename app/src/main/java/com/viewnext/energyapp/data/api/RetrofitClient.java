package com.viewnext.energyapp.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient { // Configuración de Retrofit
    private static final String BASE_URL = "https://viewnextandroid.wiremockapi.cloud/";

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(){ // Devuelve instancia de Retrofit
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService(){ // Devuelve el servicio de la API a partir de la instancia retrofit
        return getRetrofitInstance().create(ApiService.class);
    }
}
