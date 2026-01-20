package com.viewnext.presentation.ui.factura;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.viewnext.data.repository.GetFacturasRepositoryImpl;
import com.viewnext.domain.usecase.FilterFacturasUseCase;
import com.viewnext.domain.usecase.GetFacturasUseCase;
import com.viewnext.presentation.R;
import com.viewnext.presentation.databinding.ActivityFacturaBinding;
import com.viewnext.presentation.viewmodel.FacturaViewModel;
import com.viewnext.presentation.adapter.FacturaAdapter;

import java.util.ArrayList;
import java.util.List;

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

        // Hacer visible el fragmento tras rotar
        FiltroFragment filtroFragment = (FiltroFragment) getSupportFragmentManager().findFragmentByTag("FILTRO_FRAGMENT");
        if (filtroFragment != null) {
            binding.fragmentContainer.setVisibility(View.VISIBLE);
        }

        // Creacion ViewModel de Factura con UseCase y Repository
        GetFacturasRepositoryImpl repositoryImpl = new GetFacturasRepositoryImpl(getApplication());
        GetFacturasUseCase getFacturasUseCase = new GetFacturasUseCase(repositoryImpl);
        FilterFacturasUseCase filterFacturasUseCase = new FilterFacturasUseCase();

        facturaViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(FacturaViewModel.class)) {
                    return (T) new FacturaViewModel(getFacturasUseCase, filterFacturasUseCase);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        }).get(FacturaViewModel.class);


        // Adapter
        adapter = new FacturaAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        // Mostrar skeleton
        showShimmer();

        boolean primeraVez = savedInstanceState == null;

        // Obtener el valor de "USING_RETROMOCK" desde el Intent
        Intent intent = getIntent();
        boolean usingRetromock = intent.getBooleanExtra("USING_RETROMOCK", false);  // Default es false (Retrofit)

        // Cargar facturas
        if(primeraVez){
            if (facturaViewModel.hayFiltrosActivos()) {
                List<String> estados = facturaViewModel.getEstados().getValue();
                String fechaInicio = facturaViewModel.getFechaInicio().getValue();
                String fechaFin = facturaViewModel.getFechaFin().getValue();
                List<Float> valoresSlider = facturaViewModel.getValoresSlider().getValue();

                if (estados == null) estados = new ArrayList<>();
                if (fechaInicio == null) fechaInicio = "";
                if (fechaFin == null) fechaFin = "";
                if (valoresSlider == null || valoresSlider.size() < 2) {
                    valoresSlider = new ArrayList<>();
                    valoresSlider.add(0.0f);
                    valoresSlider.add(facturaViewModel.getMaxImporte());
                }

                facturaViewModel.aplicarFiltros(estados, fechaInicio, fechaFin, (double) valoresSlider.get(0), (double) valoresSlider.get(1));
            } else {
                facturaViewModel.loadFacturas(usingRetromock);
            }
        }

        facturaViewModel.getLoading().observe(this, isLoading -> {
            if (Boolean.TRUE.equals(isLoading)) {
                showShimmer();
            } else {
                hideShimmer();
            }
        });

        // Toolbar
        setSupportActionBar(binding.toolbar);

        // BotÃ³n atrÃ¡s
        binding.backButton.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        // ConfiguraciÃ³n del tÃ­tulo
        binding.toolbarTitle.setText("Facturas");

        // Actualizar RecyclerView con las nuevas facturas
        facturaViewModel.getFacturas().observe(this, facturas -> {
            if (facturas == null) return;

            if (facturas.isEmpty() && facturaViewModel.hayFiltrosActivos()) {
                Toast.makeText(this, "No se encontraron facturas", Toast.LENGTH_SHORT).show();
            }

            adapter.setFacturas(facturas);
        });

        // Mostrar mensaje de error
        facturaViewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(FacturaActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Mostrar/ocultar skeleton
    private void showShimmer() {
        binding.shimmerLayout.setVisibility(View.VISIBLE);
        binding.shimmerLayout.startShimmer();
        binding.recyclerView.setVisibility(View.GONE);
    }

    private void hideShimmer() {
        binding.shimmerLayout.stopShimmer();
        binding.shimmerLayout.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Crear menÃº para el botÃ³n filtros
        getMenuInflater().inflate(R.menu.menu_factura, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_filters) {
            FiltroFragment filtroFragment = (FiltroFragment) getSupportFragmentManager().findFragmentByTag("FILTRO_FRAGMENT");
            if (filtroFragment == null) { // Crear filtro si no existe
                filtroFragment = new FiltroFragment();

                // Bundle para el importe mÃ¡ximo
                float maxImporte = facturaViewModel.getMaxImporte();
                Bundle bundle = new Bundle();
                bundle.putFloat("MAX_IMPORTE", maxImporte);
                filtroFragment.setArguments(bundle);

                // CreaciÃ³n fragmento de filtros
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.fragment_container, filtroFragment, "FILTRO_FRAGMENT");
                transaction.addToBackStack(null);
                transaction.commit();
            }

            binding.fragmentContainer.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void restoreMainView() { // Restaurar visibilidad de la actividad Factura
        binding.toolbar.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.fragmentContainer.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() { // Sobreescribir el metodo deprecated por problemas
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
        List<String> estadosSeleccionados = bundle.getStringArrayList("ESTADOS");
        String fechaInicio = bundle.getString("FECHA_INICIO");
        String fechaFin = bundle.getString("FECHA_FIN");
        Double importeMin = bundle.getDouble("IMPORTE_MIN");
        Double importeMax = bundle.getDouble("IMPORTE_MAX");

        // Llamar al ViewModel para aplicar los filtros
        facturaViewModel.aplicarFiltros(estadosSeleccionados, fechaInicio, fechaFin, importeMin, importeMax);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}