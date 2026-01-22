package com.viewnext.domain.usecase;

import com.viewnext.domain.model.Factura;
import com.viewnext.domain.repository.GetFacturasRepository;
import java.util.List;

/**
 * Caso de uso para obtener la lista de facturas desde el repositorio.
 * Encapsula la lógica de negocio para la recuperación de facturas,
 * permitiendo diferenciar entre fuentes de datos (Retrofit o Retromock).
 */
public class GetFacturasUseCase {

    private final GetFacturasRepository repository;

    /**
     * Constructor del caso de uso.
     * @param repository Repositorio que maneja la obtención de facturas
     */
    public GetFacturasUseCase(GetFacturasRepository repository) {
        this.repository = repository;
    }

    // Callback para notificar el resultado de la ejecución del caso de uso
    public interface Callback {
        void onSuccess(List<Factura> facturas);
        void onError(String error);
    }

    /**
     * Ejecuta el caso de uso para obtener las facturas.
     * @param usingRetromock Si es true, usa datos simulados; si es false, usa la API real
     * @param callback Callback que será notificado con los resultados
     */
    public void execute(boolean usingRetromock, Callback callback) {
        repository.refreshFacturas(usingRetromock, new GetFacturasRepository.RepositoryCallback() {
            @Override
            public void onSuccess(List<Factura> facturas) {
                callback.onSuccess(facturas);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }
}
