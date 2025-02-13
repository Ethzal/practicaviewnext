package com.viewnext.energyapp.presentation.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.practicaviewnext.R;
import com.example.practicaviewnext.databinding.ActivityMainBinding;
import com.viewnext.energyapp.presentation.ui.factura.FacturaActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // La interfaz se extiende por toda la pantalla
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // Ajustar vista en función de la barra de estado y navegacion
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btFacturas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FacturaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy(){ // Cuando s edestruya la actividad, ponemos el binding a null por la memoria
        super.onDestroy();
        binding = null;
    }
}