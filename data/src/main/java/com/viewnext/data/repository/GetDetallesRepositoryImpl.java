package com.viewnext.data.repository;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.viewnext.data.api.ApiService;
import com.viewnext.data.api.RetromockClient;
import com.viewnext.domain.model.Detalles;
import com.viewnext.domain.model.DetallesResponse;
import com.viewnext.domain.repository.GetDetallesRepository;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDetallesRepositoryImpl implements GetDetallesRepository {

    private final ApiService apiServiceMock;
    private final List<Detalles> detallesCache;
    private final MutableLiveData<List<Detalles>> detallesLiveData = new MutableLiveData<>();

    public GetDetallesRepositoryImpl(Application application) {
        this.apiServiceMock = RetromockClient.getRetromockInstance(application).create(ApiService.class);
        this.detallesCache = new ArrayList<>();
    }

//    @Override
//    public List<Detalles> getDetalles() {
//        return new ArrayList<>(detallesCache);
//    }

    @Override
    public void refreshDetalles() {
        apiServiceMock.getDetallesMock().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<DetallesResponse> call, @NonNull Response<DetallesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    detallesCache.clear();
                    detallesCache.addAll(response.body().getDetalles());
                    detallesLiveData.setValue(new ArrayList<>(detallesCache));
                    Log.d("Repository", "Detalles cargados desde assets: " + detallesCache.size());
                }
            }


            @Override
            public void onFailure(@NonNull Call<DetallesResponse> call, @NonNull Throwable t) {
                Log.e("Repository", "Error Retromock: " + t.getMessage());
            }
        });
    }

    public LiveData<List<Detalles>> getDetallesLiveData() {
        return detallesLiveData;
    }
}
