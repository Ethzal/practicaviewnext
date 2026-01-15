package com.example.data.api;

import com.example.domain.model.DetallesResponse;
import com.example.domain.model.FacturaResponse;

import co.infinum.retromock.meta.Mock;
import co.infinum.retromock.meta.MockCircular;
import co.infinum.retromock.meta.MockResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService { // Retrofit genera el código para implementar esta interfaz
    @GET("invoices.json") // Endpoint
    Call<FacturaResponse> getFacturas(); // Devuelve objeto Call con la respuesta de la API (FacturaResponse)
    @Mock // En el caso de usar retromock
    @MockCircular
    @MockResponse(body = "facturas_mock.json")
    @MockResponse(body = "facturas_mock2.json")
    @MockResponse(body = "facturas_mock3.json")
    @GET("/")
    Call<FacturaResponse> getFacturasMock();

    @Mock // En el caso de usar retromock
    @MockResponse(body="detalles_mock.json") // Detalles mock dentro de assets
    @GET("/") // Endpoint
    Call<DetallesResponse> getDetallesMock();
}
