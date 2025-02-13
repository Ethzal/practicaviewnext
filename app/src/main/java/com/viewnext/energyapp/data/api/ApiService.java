package com.viewnext.energyapp.data.api;

import com.viewnext.energyapp.data.model.FacturaResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService { // Retrofit genera el código para implementar esata interfaz
    @GET("facturas") // Endpoint
    Call<FacturaResponse> getFacturas(); // Devuelve objeto Call con la respuesta de la API (FacturaResponse)
}
