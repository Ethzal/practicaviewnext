package com.viewnext.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.viewnext.domain.model.Factura;
import com.viewnext.domain.usecase.FilterFacturasUseCase;
import com.viewnext.domain.usecase.GetFacturasUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FacturaViewModel extends ViewModel {

    private final MutableLiveData<List<Factura>> facturasLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final GetFacturasUseCase getFacturasUseCase;
    private final FilterFacturasUseCase filterFacturasUseCase;

    private List<Factura> facturasOriginales = new ArrayList<>();
    private List<Factura> facturasFiltradas = new ArrayList<>();

    // Filtros guardados
    private final MutableLiveData<String> fechaInicio = new MutableLiveData<>();
    private final MutableLiveData<String> fechaFin = new MutableLiveData<>();
    private final MutableLiveData<List<Float>> valoresSlider = new MutableLiveData<>();
    private final MutableLiveData<List<String>> estados = new MutableLiveData<>();

    @Inject
    public FacturaViewModel(
            GetFacturasUseCase getFacturasUseCase,
            FilterFacturasUseCase filterFacturasUseCase
    ) {
        this.getFacturasUseCase = getFacturasUseCase;
        this.filterFacturasUseCase = filterFacturasUseCase;
    }

    // Getters LiveData
    public LiveData<List<Factura>> getFacturas() {
        return facturasLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getFechaInicio() {
        return fechaInicio;
    }

    public MutableLiveData<String> getFechaFin() {
        return fechaFin;
    }

    public MutableLiveData<List<Float>> getValoresSlider() {
        return valoresSlider;
    }

    public MutableLiveData<List<String>> getEstados() {
        return estados;
    }

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    // Cargar facturas desde el UseCase
    public void loadFacturas(boolean usingRetromock) {
        loading.postValue(true);

        getFacturasUseCase.execute(usingRetromock, new GetFacturasUseCase.Callback() {
            @Override
            public void onSuccess(List<Factura> facturas) {
                facturasOriginales = new ArrayList<>(facturas);

                if (hayFiltrosActivos()) {
                    aplicarFiltros(
                            estados.getValue(),
                            fechaInicio.getValue(),
                            fechaFin.getValue(),
                            valoresSlider != null && valoresSlider.getValue() != null && valoresSlider.getValue().size() == 2
                                    ? (double) valoresSlider.getValue().get(0)
                                    : null,
                            valoresSlider != null && valoresSlider.getValue() != null && valoresSlider.getValue().size() == 2
                                    ? (double) valoresSlider.getValue().get(1)
                                    : null
                    );
                } else {
                    facturasLiveData.postValue(facturasOriginales);
                }

                loading.postValue(false);
            }

            @Override
            public void onError(String error) {
                errorMessage.postValue(error);
                loading.postValue(false);
            }
        });
    }



    // Aplicar filtros a la lista de facturas
    public void aplicarFiltros(List<String> estadosSeleccionados, String fechaInicio,
                               String fechaFin, Double importeMin, Double importeMax) {
        if (facturasOriginales.isEmpty()) return;

        List<Factura> facturasFiltradasResult = filterFacturasUseCase.filtrarFacturas(
                facturasOriginales,
                estadosSeleccionados,
                fechaInicio,
                fechaFin,
                importeMin,
                importeMax
        );

        facturasFiltradas = new ArrayList<>(facturasFiltradasResult);
        facturasLiveData.postValue(facturasFiltradas);
    }

    // Obtener el importe máximo de las facturas
    public float getMaxImporte() {
        if (facturasOriginales == null || facturasOriginales.isEmpty()) return 0f;

        float maxImporte = 0f;
        for (Factura factura : facturasOriginales) {
            if (factura.getImporteOrdenacion() > maxImporte) {
                maxImporte = (float) factura.getImporteOrdenacion();
            }
        }
        return maxImporte;
    }

    // Comprobar si hay filtros activos
    public boolean hayFiltrosActivos() {
        return (estados.getValue() != null && !estados.getValue().isEmpty()) ||
                (fechaInicio.getValue() != null && !fechaInicio.getValue().equals("día/mes/año")) ||
                (fechaFin.getValue() != null && !fechaFin.getValue().equals("día/mes/año")) ||
                (valoresSlider.getValue() != null &&
                        (valoresSlider.getValue().get(0) > 0 || valoresSlider.getValue().get(1) < getMaxImporte()));
    }
}
