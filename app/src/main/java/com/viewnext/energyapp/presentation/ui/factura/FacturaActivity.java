package com.viewnext.energyapp.presentation.ui.factura;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaviewnext.databinding.ActivityFacturaBinding;
import com.viewnext.energyapp.data.model.Factura;
import com.viewnext.energyapp.presentation.viewmodel.FacturaViewModel;
import com.viewnext.energyapp.presentation.adapter.FacturaAdapter;

import java.util.ArrayList;
import java.util.List;

public class FacturaActivity extends AppCompatActivity {
    private FacturaAdapter adapter;
    ActivityFacturaBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFacturaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Creación ViewModel de Factura
        FacturaViewModel facturaViewModel = new ViewModelProvider(this).get(FacturaViewModel.class);

        // Inicializar lista de facturas
        List<Factura> facturaList = new ArrayList<>();

        // Creación Recycler View
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FacturaAdapter(facturaList);
        recyclerView.setAdapter(adapter);

        // Obtener el valor de "USING_RETROMOCK" desde el Intent
        Intent intent = getIntent();
        boolean usingRetromock = intent.getBooleanExtra("USING_RETROMOCK", false);  // Default es false (Retrofit)

        // Cargar facturas
        facturaViewModel.loadFacturas(usingRetromock);

        // Toolbar
        setSupportActionBar(binding.toolbar);

        // Botón atrás
        binding.backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        // Configuración del título
        binding.toolbarTitle.setText("Facturas");

        // Actualizar RecyclerView con las nuevas facturas
        facturaViewModel.getFacturas().observe(this, facturas -> {
            if (facturas != null) {
                adapter = new FacturaAdapter(facturas);
                recyclerView.setAdapter(adapter);
            }
        });

        // Mostrar mensaje de error
        facturaViewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(FacturaActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}