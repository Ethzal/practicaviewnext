package com.viewnext.data.api;

import com.viewnext.domain.model.DetallesResponse;
import com.viewnext.domain.model.FacturaResponse;

import co.infinum.retromock.meta.Mock;
import co.infinum.retromock.meta.MockCircular;
import co.infinum.retromock.meta.MockResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interfaz que define los endpoints de la API que Retrofit utilizará para realizar las solicitudes.
 * Las respuestas de cada endpoint están mapeadas a objetos de tipo {@link FacturaResponse} o {@link DetallesResponse}.
 */
public interface ApiService { // Retrofit genera el código para implementar esta interfaz
    /**
     * Endpoint para obtener las facturas desde la API.
     * @return Un {@link Call} que devolverá una respuesta de tipo {@link FacturaResponse} cuando la solicitud sea exitosa.
     */
    @GET("invoices.json") // Endpoint
    Call<FacturaResponse> getFacturas(); // Devuelve objeto Call con la respuesta de la API (FacturaResponse)

    /**
     * Endpoint simulado usando Retromock para obtener las facturas desde un archivo de recursos local.
     * Se utilizan múltiples respuestas simuladas que se obtienen de archivos JSON ubicados en los assets.
     * @return Un {@link Call} que devolverá una respuesta simulada de tipo {@link FacturaResponse}.
     */
    @Mock // En el caso de usar retromock
    @MockCircular
    @MockResponse(body = "facturas_mock.json")
    @MockResponse(body = "facturas_mock2.json")
    @MockResponse(body = "facturas_mock3.json")
    @GET("/")
    Call<FacturaResponse> getFacturasMock();

    /**
     * Endpoint simulado usando Retromock para obtener los detalles desde un archivo de recursos local.
     * Se obtiene una respuesta simulada del archivo JSON correspondiente en los assets.
     * @return Un {@link Call} que devolverá una respuesta simulada de tipo {@link DetallesResponse}.
     */
    @Mock // En el caso de usar retromock
    @MockResponse(body="detalles_mock.json") // Detalles mock dentro de assets
    @GET("/") // Endpoint
    Call<DetallesResponse> getDetallesMock();
}
