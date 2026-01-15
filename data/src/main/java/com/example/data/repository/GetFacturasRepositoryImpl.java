package com.example.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.data.api.ApiService;
import com.example.data.api.RetrofitClient;
import com.example.data.api.RetromockClient;
import com.example.data.local.AppDatabase;
import com.example.data.local.dao.FacturaDao;
import com.example.data.local.entity.FacturaEntity;
import com.example.data.mapper.FacturaMapper;
import com.example.domain.model.Factura;
import com.example.domain.model.FacturaResponse;
import com.example.domain.repository.GetFacturasRepository;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFacturasRepositoryImpl implements GetFacturasRepository {
    private final ApiService apiServiceRetrofit;
    private final ApiService apiServiceMock;
    private final FacturaDao facturaDao;

    public GetFacturasRepositoryImpl(Application application) {
        this.apiServiceRetrofit = RetrofitClient.getApiService();
        this.apiServiceMock = RetromockClient.getRetromockInstance(application).create(ApiService.class);

        AppDatabase db = AppDatabase.getInstance(application);
        facturaDao = db.facturaDao();
    }

    // Room -> LiveData
    public LiveData<List<Factura>> getFacturasLiveData() {
        return new LiveData<>() {
            private final Observer<List<FacturaEntity>> observer =
                    entities -> setValue(FacturaMapper.toDomainList(entities));

            @Override
            protected void onActive() {
                facturaDao.getFacturas().observeForever(observer);
            }

            @Override
            protected void onInactive() {
                facturaDao.getFacturas().removeObserver(observer);
            }
        };
    }

    // API -> Room
    public void refreshFacturas(boolean usingRetromock) {
        ApiService apiService = usingRetromock ? apiServiceMock : apiServiceRetrofit;
        Call<FacturaResponse> call = usingRetromock ? apiService.getFacturasMock() : apiService.getFacturas();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<FacturaResponse> call, @NonNull Response<FacturaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FacturaEntity> entities = FacturaMapper.toEntityList(response.body().getFacturas());
                    Executors.newSingleThreadExecutor().execute(() -> {
                        facturaDao.deleteAll();
                        facturaDao.insertAll(entities);
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<FacturaResponse> call, @NonNull Throwable t) {
                Log.e("GetFacturasRepositoryImpl", "Error refrescando facturas: " + t.getMessage());
            }
        });
    }

    public List<Factura> getFacturasFromDb() {
        List<FacturaEntity> entities = facturaDao.getFacturasDirect();
        return FacturaMapper.toDomainList(entities);
    }
}
