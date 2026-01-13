package com.viewnext.energyapp.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient { // Configuración de Retrofit
    // private static final String BASE_URL = "https://viewnextandroid.wiremockapi.cloud/";
    private static final String BASE_URL = "https://francisco-pacheco.com/api/";

    private RetrofitClient() {
        // Constructor privado y vacío para evitar que se creen instancias externas
    }

    private static final class RetrofitHolder {
        private static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // Acceso singleton
    public static Retrofit getRetrofitInstance() {
        // Usamos sincronización doble para evitar crear múltiples instancias
        return RetrofitHolder.retrofit;
    }

    public static ApiService getApiService(){ // Devuelve el servicio de la API a partir de la instancia retrofit
        return getRetrofitInstance().create(ApiService.class);
    }
}
