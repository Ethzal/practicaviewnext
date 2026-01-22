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

@Singleton
public class GetDetallesRepositoryImpl implements GetDetallesRepository {

    private final ApiService apiServiceMock;
    private final List<Detalles> detallesCache;
    private final MutableLiveData<List<Detalles>> detallesLiveData = new MutableLiveData<>();

    @Inject
    public GetDetallesRepositoryImpl(@ApplicationContext Context context) {
        this.apiServiceMock = RetromockClient.getRetromockInstance(context).create(ApiService.class);
        this.detallesCache = new ArrayList<>();
    }

    @Override
    public List<Detalles> getDetalles() {
        return new ArrayList<>(detallesCache);
    }

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

    public LiveData<List<Detalles>> getDetallesLiveData() {
        return detallesLiveData;
    }
}