package com.viewnext.energyapp.presentation.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.viewnext.energyapp.data.model.Factura;
import com.viewnext.energyapp.domain.getFacturasUseCase;
import com.viewnext.energyapp.domain.getFacturasUseCaseCallback;

import java.util.List;

public class FacturaViewModel extends AndroidViewModel { // Se encarga de gestionar los datos de la factura y realizar solicitudes a la API
    // ViewModel actúa como intermediario entre la vista (UI) y los datos (modelo)
    // LiveData permite que los observadores (interfaz) se suscriban a los datos y se actualicen automáticamente
    private final MutableLiveData<List<Factura>> facturasLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final getFacturasUseCase getFacturasUseCase;

    // Constructor
    public FacturaViewModel(Application application){
        super(application); // Le pasa el contexto al AndroidViewModel
        this.getFacturasUseCase = new getFacturasUseCase(application);
    }

    // Getters
    public LiveData<List<Factura>> getFacturas(){
        return facturasLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadFacturas(boolean usingRetromock) { // Se encarga de cargar las facturas de la API
        getFacturasUseCase.execute(usingRetromock, new getFacturasUseCaseCallback() { // Llama al UseCase
            @Override
            public void onSuccess(List<Factura> facturas) {
                facturasLiveData.postValue(facturas);
            }

            @Override
            public void onError(String error) {
                errorMessage.postValue(error);
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
