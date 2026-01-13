package com.viewnext.energyapp.data.api;

import com.viewnext.energyapp.data.model.DetallesResponse;
import com.viewnext.energyapp.data.model.FacturaResponse;

import co.infinum.retromock.meta.Mock;
import co.infinum.retromock.meta.MockResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService { // Retrofit genera el código para implementar esta interfaz
    @GET("invoices.json") // Endpoint
    Call<FacturaResponse> getFacturas(); // Devuelve objeto Call con la respuesta de la API (FacturaResponse)
    @Mock // En el caso de usar retromock
    @MockResponse(body="facturas_mock.json") // Facturas mock dentro de assets
    @GET("/") // Endpoint
    Call<FacturaResponse> getFacturasMock();
    @Mock // En el caso de usar retromock
    @MockResponse(body="detalles_mock.json") // Detalles mock dentro de assets
    @GET("/") // Endpoint
    Call<DetallesResponse> getDetallesMock();
}
