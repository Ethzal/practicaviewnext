package com.viewnext.energyapp.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.viewnext.energyapp.data.api.ApiService;
import com.viewnext.energyapp.data.api.RetrofitClient;
import com.viewnext.energyapp.data.api.RetromockClient;
import com.viewnext.energyapp.data.local.AppDatabase;
import com.viewnext.energyapp.data.local.dao.FacturaDao;
import com.viewnext.energyapp.data.local.entity.FacturaEntity;
import com.viewnext.energyapp.data.mapper.FacturaMapper;
import com.viewnext.energyapp.data.model.FacturaResponse;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFacturasRepository {
    private final ApiService apiServiceRetrofit;
    private final ApiService apiServiceMock;
    private final FacturaDao facturaDao;

    public GetFacturasRepository(Application application) {
        this.apiServiceRetrofit = RetrofitClient.getApiService();
        this.apiServiceMock = RetromockClient.getRetromockInstance(application).create(ApiService.class);

        AppDatabase db = AppDatabase.getInstance(application);
        facturaDao = db.facturaDao();
    }

//    public void getFacturas(boolean usingRetromock, GetFacturasRepositoryCallback callback){
//        ApiService apiService = usingRetromock ? apiServiceMock : apiServiceRetrofit;
//        Call<FacturaResponse> call = usingRetromock ? apiService.getFacturasMock() : apiService.getFacturas();
//
//        // Realizar la llamada y procesar la respuesta
//        call.enqueue(new Callback<>() {
//            @Override
//            public void onResponse(@NonNull Call<FacturaResponse> call, @NonNull Response<FacturaResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    callback.onSuccess(response.body().getFacturas());
//                } else {
//                    callback.onError("Error en la respuesta de la API");
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<FacturaResponse> call, @NonNull Throwable t) {
//                callback.onError("Error de conexión: " + t.getMessage());
//            }
//        });
//    }

    // Room -> LiveData
    public LiveData<List<FacturaEntity>> getFacturasFromDb() {
        return facturaDao.getFacturas();
    }

    // API -> Room
    public void refreshFacturas(boolean usingRetromock) {

        ApiService apiService = usingRetromock ? apiServiceMock : apiServiceRetrofit;
        Call<FacturaResponse> call =
                usingRetromock ? apiService.getFacturasMock() : apiService.getFacturas();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(
                    @NonNull Call<FacturaResponse> call,
                    @NonNull Response<FacturaResponse> response
            ) {
                if (response.isSuccessful() && response.body() != null) {

                    List<FacturaEntity> entities =
                            FacturaMapper.toEntityList(response.body().getFacturas());

                    Executors.newSingleThreadExecutor().execute(() -> {
                            facturaDao.deleteAll();
                            facturaDao.insertAll(entities);
                    });
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<FacturaResponse> call,
                    @NonNull Throwable t
            ) {
                Log.e("GetFacturasRepository", "Error refrescando facturas: " + t.getMessage());
            }
        });
    }
}
