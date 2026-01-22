package com.viewnext.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase que configura la instancia de Retrofit para realizar las solicitudes de red.
 * Se usa para acceder a los servicios de la API de forma centralizada.
 */
public class RetrofitClient { // Configuración de Retrofit
    // private static final String BASE_URL = "https://viewnextandroid.wiremockapi.cloud/";
    private static final String BASE_URL = "https://francisco-pacheco.com/api/";

    private RetrofitClient() {
        // Constructor privado y vacío para evitar que se creen instancias externas
    }

    /**
     * Clase interna que mantiene la instancia única de Retrofit.
     * Se usa el patrón Singleton para garantizar que solo haya una instancia de Retrofit.
     */
    private static final class RetrofitHolder {
        private static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Devuelve la instancia única de Retrofit.
     * Utiliza el patrón Singleton para evitar crear múltiples instancias de Retrofit.
     * @return La instancia de Retrofit configurada.
     */
    public static Retrofit getRetrofitInstance() {
        // Usamos sincronización doble para evitar crear múltiples instancias
        return RetrofitHolder.retrofit;
    }

    /**
     * Devuelve una instancia de la interfaz {@link ApiService} que se utiliza para realizar las solicitudes HTTP.
     * Esta interfaz contiene los métodos definidos para la API.
     * @return La instancia de {@link ApiService} para hacer solicitudes a la API.
     */
    public static ApiService getApiService(){ // Devuelve el servicio de la API a partir de la instancia retrofit
        return getRetrofitInstance().create(ApiService.class);
    }
}
