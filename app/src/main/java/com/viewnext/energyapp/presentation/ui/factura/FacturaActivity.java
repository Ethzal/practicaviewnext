package com.viewnext.energyapp.presentation.ui.factura;

import static com.viewnext.energyapp.data.model.Factura.stringToDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaviewnext.R;
import com.example.practicaviewnext.databinding.ActivityFacturaBinding;
import com.viewnext.energyapp.data.model.Factura;
import com.viewnext.energyapp.presentation.viewmodel.FacturaViewModel;
import com.viewnext.energyapp.presentation.adapter.FacturaAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FacturaActivity extends AppCompatActivity {
    private FacturaAdapter adapter;
    ActivityFacturaBinding binding;
    FacturaViewModel facturaViewModel;

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
        facturaViewModel = new ViewModelProvider(this).get(FacturaViewModel.class);

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
            } else {
                Toast.makeText(FacturaActivity.this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_factura, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_filters) {
            // Antes de crear el fragmento, obtenemos el maxImporte
            float maxImporte = facturaViewModel.getMaxImporte();  // O la variable que almacena el maxImporte

            // Crear el Bundle para pasar al fragmento
            Bundle bundle = new Bundle();
            bundle.putFloat("MAX_IMPORTE", maxImporte);

            // Hacer visible el contenedor de fragmentos
            binding.fragmentContainer.setVisibility(View.VISIBLE);

            // Mostrar el Fragment en toda la pantalla
            FiltroFragment filtroFragment = new FiltroFragment();
            filtroFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            transaction.replace(R.id.fragment_container, filtroFragment);
            transaction.addToBackStack(null); // Permite volver atrás
            transaction.commit();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void restoreMainView() {
        binding.toolbar.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.fragmentContainer.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed() {
        FiltroFragment filtroFragment = (FiltroFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (filtroFragment != null && filtroFragment.isVisible()) {
            restoreMainView();
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void aplicarFiltros(Bundle bundle) {
        // Recuperar los filtros desde el Bundle
        List<String> estadosSeleccionados = bundle.getStringArrayList("ESTADOS");  // Cambiar de "ESTADO" a "ESTADOS"
        Date fechaInicio = (Date) bundle.getSerializable("FECHA_INICIO");
        Date fechaFin = (Date) bundle.getSerializable("FECHA_FIN");
        Double importeMin = bundle.getDouble("IMPORTE_MIN");
        Double importeMax = bundle.getDouble("IMPORTE_MAX");

        Log.d("AplicarFiltros", "Estados seleccionados: " + estadosSeleccionados);
        Log.d("AplicarFiltros", "Fecha inicio: " + fechaInicio);
        Log.d("AplicarFiltros", "Fecha fin: " + fechaFin);
        Log.d("AplicarFiltros", "Importe Min: " + importeMin);
        Log.d("AplicarFiltros", "Importe Max: " + importeMax);

        // Filtrar las facturas
        List<Factura> facturasFiltradas = filtrarFacturas(estadosSeleccionados, String.valueOf(fechaInicio), String.valueOf(fechaFin), importeMin, importeMax);

        // Actualizar el RecyclerView con las facturas filtradas
        if (facturasFiltradas.isEmpty()) {
            Log.d("AplicarFiltros", "No hay facturas que coincidan con los filtros.");
        } else {
            Log.d("AplicarFiltros", "Se encontraron " + facturasFiltradas.size() + " facturas.");
        }
        runOnUiThread(() -> {
            adapter.setFacturas(facturasFiltradas);
        });
    }

    public List<Factura> filtrarFacturas(List<String> estadosSeleccionados, String fechaInicioString, String fechaFinString, Double importeMin, Double importeMax) {
        List<Factura> facturasFiltradas = new ArrayList<>();

        // Obtener las facturas cargadas desde el ViewModel
        List<Factura> facturas = facturaViewModel.getFacturas().getValue();  // Obtén las facturas cargadas

        if (facturas == null) {
            return facturasFiltradas;  // Si no hay facturas, retornamos una lista vacía
        }

        // Convertir las fechas de String a Date
        Date fechaInicio = stringToDate(fechaInicioString);
        Date fechaFin = stringToDate(fechaFinString);

        // Filtrar por estado, si se seleccionaron algunos
        for (Factura factura : facturas) {
            boolean cumpleEstado = (estadosSeleccionados == null || estadosSeleccionados.contains(factura.getDescEstado()));

            // Filtrar por fecha, si hay un rango de fechas
            boolean cumpleFecha = true;

            if (fechaInicio != null && factura.getFecha() != null) {
                cumpleFecha &= factura.getFecha().compareTo(String.valueOf(fechaInicio)) >= 0;  // Verificar si la factura es posterior o igual a la fecha de inicio
            }

            if (fechaFin != null && factura.getFecha() != null) {
                cumpleFecha &= factura.getFecha().compareTo(String.valueOf(fechaFin)) <= 0;  // Verificar si la factura es anterior o igual a la fecha de fin
            }

            // Filtrar por importe, si hay un rango de importes
            boolean cumpleImporte = (importeMin == null || factura.getImporteOrdenacion() >= importeMin) &&
                    (importeMax == null || factura.getImporteOrdenacion() <= importeMax);

            // Si cumple todos los filtros, añadir la factura a la lista filtrada
            if (cumpleEstado && cumpleFecha && cumpleImporte) {
                facturasFiltradas.add(factura);
            }
        }

        return facturasFiltradas;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
