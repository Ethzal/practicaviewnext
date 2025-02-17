package com.viewnext.energyapp.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.viewnext.energyapp.data.api.ApiService;
import com.viewnext.energyapp.data.api.RetrofitClient;
import com.viewnext.energyapp.data.api.RetromockClient;
import com.viewnext.energyapp.data.model.Factura;
import com.viewnext.energyapp.data.model.FacturaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacturaViewModel extends AndroidViewModel { // Se encarga de gestionar los datos de la factura y realizar solicitudes a la API
    // ViewModel actúa como intermediario entre la vista (UI) y los datos (modelo)
    // LiveData permite que los observadores (interfaz) se suscriban a los datos y se actualicen automáticamente
    private final MutableLiveData<List<Factura>> facturasLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Constructor
    public FacturaViewModel(Application application){
        super(application); // Le pasa el contexto al AndroidViewModel
    }

    // Getters
    public LiveData<List<Factura>> getFacturas(){
        return facturasLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadFacturas(boolean usingRetromock) { // Se encarga de cargar las facturas de la API
        ApiService apiService;
        Call<FacturaResponse> call;

        if (usingRetromock) { // Retromock
            apiService = RetromockClient.getRetromockInstance(getApplication()).create(ApiService.class);
            call = apiService.getFacturasMock();
        } else { // Retrofit
            apiService = RetrofitClient.getApiService();
            call = apiService.getFacturas();
        }

        // Realizar la llamada y procesar la respuesta
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<FacturaResponse> call, @NonNull Response<FacturaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    facturasLiveData.setValue(response.body().getFacturas());
                } else {
                    errorMessage.setValue("Error en la respuesta de la API");
                }
            }

            @Override
            public void onFailure(@NonNull Call<FacturaResponse> call, @NonNull Throwable t) {
                errorMessage.setValue("Error de conexión: " + t.getMessage());
            }
        });
    }
    public float getMaxImporte() {
        if (facturasLiveData.getValue() == null || facturasLiveData.getValue().isEmpty()) {
            return 0f;
        }

        float maxImporte = 0f;
        for (Factura factura : facturasLiveData.getValue()) {
            if (factura.getImporteOrdenacion() > maxImporte) {
                maxImporte = (float) factura.getImporteOrdenacion();
            }
        }
        return maxImporte;
    }

}
