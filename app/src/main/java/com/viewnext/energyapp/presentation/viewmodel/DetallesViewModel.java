package com.viewnext.energyapp.presentation.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.viewnext.energyapp.data.api.ApiService;
import com.viewnext.energyapp.data.api.RetromockClient;
import com.viewnext.energyapp.data.model.Detalles;
import com.viewnext.energyapp.data.model.DetallesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallesViewModel extends AndroidViewModel {
    // MutableLiveData para almacenar los datos de la instalación
    private final MutableLiveData<List<Detalles>> detallesLiveData;
    private final ApiService apiService;

    // Constructor, inicializando el servicio de API
    public DetallesViewModel(Application application) {
        super(application);
        apiService = RetromockClient.getRetromockInstance(application).create(ApiService.class);
        detallesLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Detalles>> getDetalles() {
        return detallesLiveData;
    }

    public void loadDetalles() {
        Call<DetallesResponse> call = apiService.getDetallesMock();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<DetallesResponse> call, @NonNull Response<DetallesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detallesLiveData.setValue(response.body().getDetalles());
                } else {
                    Log.e("ViewModel", "Error en la respuesta de la API, código: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetallesResponse> call, @NonNull Throwable t) {
                Log.e("ViewModel", "Error de conexión con la API: " + t.getMessage());
            }
        });
    }
}
