package com.viewnext.energyapp.presentation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.practicaviewnext.databinding.ActivityMainBinding;
import com.viewnext.energyapp.presentation.ui.factura.FacturaActivity;

public class MainActivity extends AppCompatActivity { // Actividad principal, así empieza la app

    private ActivityMainBinding binding;
    private boolean usingRetromock = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // La interfaz se extiende por toda la pantalla
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> { // Ajustar vista en función de la barra de estado y navegación
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Botón para alternar entre Retrofit y Retromock
        binding.btToggleApi.setOnClickListener(v -> {
            usingRetromock = !usingRetromock;
            if (usingRetromock) {
                Toast.makeText(MainActivity.this, "Usando Retromock", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Usando Retrofit", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para abrir FacturaActivity y pasar usingRetromock
        binding.btFacturas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FacturaActivity.class);
            intent.putExtra("USING_RETROMOCK", usingRetromock);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() { // Cuando se destruye la actividad, ponemos el binding a null por la memoria
        super.onDestroy();
        binding = null;
    }
}
