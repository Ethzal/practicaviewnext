package com.viewnext.energyapp.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;

import com.viewnext.energyapp.data.api.ApiService;
import com.viewnext.energyapp.data.api.RetrofitClient;
import com.viewnext.energyapp.data.api.RetromockClient;
import com.viewnext.energyapp.data.model.FacturaResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFacturasRepository {
    private final ApiService apiServiceRetrofit;
    private final ApiService apiServiceMock;

    public GetFacturasRepository(Application application) {
        this.apiServiceRetrofit = RetrofitClient.getApiService();
        this.apiServiceMock = RetromockClient.getRetromockInstance(application).create(ApiService.class);
    }

    public void getFacturas(boolean usingRetromock, GetFacturasRepositoryCallback callback){
        ApiService apiService = usingRetromock ? apiServiceMock : apiServiceRetrofit;
        Call<FacturaResponse> call = usingRetromock ? apiService.getFacturasMock() : apiService.getFacturas();

        // Realizar la llamada y procesar la respuesta
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<FacturaResponse> call, @NonNull Response<FacturaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getFacturas());
                } else {
                    callback.onError("Error en la respuesta de la API");
                }
            }

            @Override
            public void onFailure(@NonNull Call<FacturaResponse> call, @NonNull Throwable t) {
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }
}
