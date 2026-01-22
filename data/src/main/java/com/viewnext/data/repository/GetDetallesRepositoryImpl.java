package com.viewnext.data.repository;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.viewnext.data.api.ApiService;
import com.viewnext.data.api.RetromockClient;
import com.viewnext.domain.model.Detalles;
import com.viewnext.domain.model.DetallesResponse;
import com.viewnext.domain.repository.DetallesCallback;
import com.viewnext.domain.repository.GetDetallesRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementación del repositorio GetDetallesRepository. Esta clase se encarga de obtener los detalles
 * de una fuente remota (simulada por Retromock) y almacenarlos en una lista en caché.
 * También proporciona un métodos para obtener los detalles como un LiveData.
 */
@Singleton
public class GetDetallesRepositoryImpl implements GetDetallesRepository {

    private final ApiService apiServiceMock;
    private final List<Detalles> detallesCache;
    private final MutableLiveData<List<Detalles>> detallesLiveData = new MutableLiveData<>();

    /**
     * Constructor que inicializa la instancia de la API (Retromock) y la caché de detalles.
     * @param context El contexto de la aplicación necesario para inicializar Retromock.
     */
    @Inject
    public GetDetallesRepositoryImpl(@ApplicationContext Context context) {
        this.apiServiceMock = RetromockClient.getRetromockInstance(context).create(ApiService.class);
        this.detallesCache = new ArrayList<>();
    }

    /**
     * Obtiene la lista de detalles almacenada en caché.
     * @return Una lista de objetos Detalles que se encuentran en la caché.
     */
    @Override
    public List<Detalles> getDetalles() {
        return new ArrayList<>(detallesCache);
    }

    /**
     * Realiza una solicitud a la API para obtener los detalles. Si la solicitud es exitosa,
     * se actualiza la caché y se pasa la lista de detalles al callback.
     * En caso de error, se pasa el error al callback.
     * @param callback El callback que maneja el éxito o el error al obtener los detalles.
     */
    @Override
    public void refreshDetalles(DetallesCallback<List<Detalles>> callback) {
        // Aquí es donde se hace la llamada real
        apiServiceMock.getDetallesMock().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<DetallesResponse> call, Response<DetallesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detallesCache.clear();
                    detallesCache.addAll(response.body().getDetalles());

                    // Al llamar a onSuccess con el resultado
                    callback.onSuccess(new ArrayList<>(detallesCache));
                    Log.d("Repository", "Detalles cargados desde assets: " + detallesCache.size());
                } else {
                    // Si hay error en la respuesta, llamamos a onFailure
                    callback.onFailure(new Exception("Error al cargar detalles"));
                }
            }

            @Override
            public void onFailure(Call<DetallesResponse> call, Throwable t) {
                // En caso de fallo de la llamada, se llama a onFailure con el error
                callback.onFailure(t);
            }
        });
    }

    /**
     * Obtiene el LiveData que contiene la lista de detalles. El LiveData permite observar
     * los cambios en los detalles en tiempo real.
     * @return Un LiveData que contiene la lista de detalles.
     */
    public LiveData<List<Detalles>> getDetallesLiveData() {
        return detallesLiveData;
    }
}