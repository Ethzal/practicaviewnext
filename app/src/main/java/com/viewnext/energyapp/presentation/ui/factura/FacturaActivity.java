package com.viewnext.energyapp.presentation.ui.factura;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaviewnext.R;
import com.example.practicaviewnext.databinding.ActivityFacturaBinding;
import com.viewnext.energyapp.data.api.ApiService;
import com.viewnext.energyapp.data.api.RetrofitClient;
import com.viewnext.energyapp.data.model.Factura;
import com.viewnext.energyapp.data.model.FacturaResponse;
import com.viewnext.energyapp.presentation.adapter.FacturaAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacturaActivity extends AppCompatActivity {

    private ActivityFacturaBinding binding;
    private RecyclerView recyclerView;
    private FacturaAdapter adapter;
    private List<Factura> facturaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFacturaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar lista de facturas
        facturaList = new ArrayList<>();

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FacturaAdapter(facturaList);
        recyclerView.setAdapter(adapter);

        loadFacturas();
    }
    private void loadFacturas(){
        ApiService apiService = RetrofitClient.getApiService();

        Call<FacturaResponse> call = apiService.getFacturas();

        call.enqueue(new Callback<FacturaResponse>() {
            @Override
            public void onResponse(Call<FacturaResponse> call, Response<FacturaResponse> response) {
                if(response.isSuccessful()){
                    FacturaResponse facturaResponse = response.body();
                    if(facturaResponse != null){
                        facturaList.clear();
                        facturaList.addAll(facturaResponse.getFacturas());
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(FacturaActivity.this,"Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FacturaResponse> call, Throwable t) {
                Toast.makeText(FacturaActivity.this,"Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}