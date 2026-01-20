package com.viewnext.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.viewnext.data.api.ApiService;
import com.viewnext.data.api.RetrofitClient;
import com.viewnext.data.api.RetromockClient;
import com.viewnext.data.local.AppDatabase;
import com.viewnext.data.local.dao.FacturaDao;
import com.viewnext.data.local.entity.FacturaEntity;
import com.viewnext.data.mapper.FacturaMapper;
import com.viewnext.domain.model.Factura;
import com.viewnext.domain.model.FacturaResponse;
import com.viewnext.domain.repository.GetFacturasRepository;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class GetFacturasRepositoryImpl implements GetFacturasRepository {
    private final ApiService apiServiceRetrofit;
    private final ApiService apiServiceMock;
    private final FacturaDao facturaDao;

    @Inject
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
    @Override
    public void refreshFacturas(boolean usingRetromock, RepositoryCallback callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
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

                            // Devuelve la lista de dominio al UseCase
                            callback.onSuccess(FacturaMapper.toDomainList(entities));
                        });
                    } else {
                        callback.onError("Error en la respuesta de la API");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FacturaResponse> call, @NonNull Throwable t) {
                    callback.onError(t.getMessage() != null ? t.getMessage() : "Error desconocido");
                }
            });
        });
    }

    public List<Factura> getFacturasFromDb() {
        List<FacturaEntity> entities = facturaDao.getFacturasDirect();
        return FacturaMapper.toDomainList(entities);
    }
}