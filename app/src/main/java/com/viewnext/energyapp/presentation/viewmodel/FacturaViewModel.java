package com.viewnext.energyapp.presentation.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.viewnext.energyapp.data.local.entity.FacturaEntity;
import com.viewnext.energyapp.data.mapper.FacturaMapper;
import com.viewnext.energyapp.data.model.Factura;
import com.viewnext.energyapp.domain.FilterFacturasUseCase;
import com.viewnext.energyapp.domain.FilterFacturasUseCaseCallback;
import com.viewnext.energyapp.domain.GetFacturasUseCase;
import com.viewnext.energyapp.domain.GetFacturasUseCaseCallback;

import java.util.ArrayList;
import java.util.List;

public class FacturaViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Factura>> facturasLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final GetFacturasUseCase getFacturasUseCase;
    private final FilterFacturasUseCase filterFacturasUseCase;
    private List<Factura> facturasOriginales = new ArrayList<>();
    private List<Factura> facturasFiltradas = new ArrayList<>();
    public LiveData<List<FacturaEntity>> facturas;

    // Filtros guardados
    private MutableLiveData<String> fechaInicio = new MutableLiveData<>();
    private MutableLiveData<String> fechaFin = new MutableLiveData<>();
    private MutableLiveData<List<Float>> valoresSlider = new MutableLiveData<>();
    private MutableLiveData<List<String>> estados = new MutableLiveData<>();

    public FacturaViewModel(Application application) {
        super(application);
        this.getFacturasUseCase = new GetFacturasUseCase(application);
        this.filterFacturasUseCase = new FilterFacturasUseCase(application);
        this.facturas = getFacturasUseCase.getFacturasFromDb();
    }

    // Getters y Setters
    public LiveData<List<Factura>> getFacturas() {
        return facturasLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(MutableLiveData<String> fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public MutableLiveData<String> getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(MutableLiveData<String> fechaFin) {
        this.fechaFin = fechaFin;
    }

    public MutableLiveData<List<Float>> getValoresSlider() {
        return valoresSlider;
    }

    public void setValoresSlider(MutableLiveData<List<Float>> valoresSlider) {
        this.valoresSlider = valoresSlider;
    }

    public MutableLiveData<List<String>> getEstados() {
        return estados;
    }

    public void setEstados(MutableLiveData<List<String>> estados) {
        this.estados = estados;
    }

    // Cargar facturas
    public void loadFacturas(boolean usingRetromock) {
        getFacturasUseCase.refresh(usingRetromock); // API -> Room

        getFacturasUseCase.getFacturasFromDb().observeForever(facturasEntities -> {
            List<Factura> facturas = FacturaMapper.toDomainList(facturasEntities);
            facturasOriginales = new ArrayList<>(facturas);

            if (hayFiltrosActivos()) {
                List<String> estadosSeleccionados = estados.getValue();
                String fechaInicioValue = fechaInicio.getValue();
                String fechaFinValue = fechaFin.getValue();
                List<Float> valoresSliderValue = valoresSlider.getValue();

                if (valoresSliderValue != null && valoresSliderValue.size() == 2) {
                    Double importeMin = (double) valoresSliderValue.get(0);
                    Double importeMax = (double) valoresSliderValue.get(1);

                    aplicarFiltros(estadosSeleccionados, fechaInicioValue, fechaFinValue, importeMin, importeMax);
                }
            } else {
                facturasLiveData.postValue(facturasOriginales);
            }
        });
    }

    // Aplicar filtros
    public void aplicarFiltros(List<String> estadosSeleccionados, String fechaInicio, String fechaFin, Double importeMin, Double importeMax) {
        if (facturasOriginales.isEmpty()) {
            return;
        }

        // UseCase de filtrar facturas
        filterFacturasUseCase.filtrarFacturas(facturasOriginales, estadosSeleccionados, fechaInicio, fechaFin, importeMin, importeMax, new FilterFacturasUseCaseCallback() {
            @Override
            public void onSuccess(List<Factura> facturasFiltradasResult) {
                facturasFiltradas = new ArrayList<>(facturasFiltradasResult);
                facturasLiveData.setValue(facturasFiltradas);
            }

            @Override
            public void onError(String error) {
                errorMessage.setValue(error);
            }
        });
    }

    // Obtener MaxImporte de facturas
    public float getMaxImporte() {
        if (facturasOriginales == null || facturasOriginales.isEmpty()) {
            return 0f;
        }

        float maxImporte = 0f;
        for (Factura factura : facturasOriginales) {
            if (factura.getImporteOrdenacion() > maxImporte) {
                maxImporte = (float) factura.getImporteOrdenacion();
            }
        }
        return maxImporte;
    }

    public boolean hayFiltrosActivos() {
        return (estados.getValue() != null && !estados.getValue().isEmpty()) ||
                (fechaInicio.getValue() != null && !fechaInicio.getValue().equals("día/mes/año")) ||
                (fechaFin.getValue() != null && !fechaFin.getValue().equals("día/mes/año")) ||
                (valoresSlider.getValue() != null && (valoresSlider.getValue().get(0) > 0 || valoresSlider.getValue().get(1) < getMaxImporte()));
    }
}
