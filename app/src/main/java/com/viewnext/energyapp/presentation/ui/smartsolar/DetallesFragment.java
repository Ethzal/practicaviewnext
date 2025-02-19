package com.viewnext.energyapp.presentation.ui.smartsolar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.practicaviewnext.R;
import com.example.practicaviewnext.databinding.FragmentDetallesBinding;
import com.example.practicaviewnext.databinding.FragmentFiltroBinding;
import com.viewnext.energyapp.data.api.ApiService;
import com.viewnext.energyapp.data.model.DetallesResponse;
import com.viewnext.energyapp.presentation.viewmodel.DetallesViewModel;
import com.viewnext.energyapp.presentation.viewmodel.FacturaViewModel;

import java.util.Objects;

import retrofit2.Call;

public class DetallesFragment extends Fragment {
    private DetallesViewModel viewModel;
    private FragmentDetallesBinding binding;

    public DetallesFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Binding
        binding = FragmentDetallesBinding.inflate(inflater, container, false);
        // Creación ViewModel de Detalles
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(DetallesViewModel.class);

        // Cargar detalles y mostrarlos
        viewModel.loadDetalles();
        viewModel.getDetalles().observe(getViewLifecycleOwner(), detalles -> {
            if (detalles != null && !detalles.isEmpty()) {
                binding.cau.setText(detalles.get(0).getCau());
                binding.estadoSolicitud.setText(detalles.get(0).getEstadoSolicitud());
                binding.tipoAutoconsumo.setText(detalles.get(0).getTipoAutoconsumo());
                binding.compensacion.setText(detalles.get(0).getCompensacion());
                binding.potencia.setText(detalles.get(0).getPotencia());
            }
        });

        binding.btPopUp.setOnClickListener(this::showPopup);
        return binding.getRoot();
    }
    @SuppressLint("SetTextI18n")
    private void showPopup(View view) {
        Context context = view.getContext();

        // Crear un TextView para el título
        TextView title = new TextView(context);
        title.setText("Estado solicitud autoconsumo");
        title.setPadding(20, 80, 20, 20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);

        // Crear un TextView para el mensaje
        TextView message = new TextView(context);
        message.setText("El tiempo estimado de activación de tu autoconsumo es de 1 a 2 meses, éste variará en función de tu comunidad autónoma y distribuidora");
        message.setPadding(100, 60, 100, 60);
        message.setGravity(Gravity.CENTER);
        message.setTextSize(16);

        // Crear el diálogo
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCustomTitle(title)
                .setView(message)
                .setPositiveButton("Aceptar", (d, which) -> d.dismiss())
                .show();

        // Personalizar el botón después de mostrar el diálogo
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setBackground(ContextCompat.getDrawable(context, R.drawable.button_background));
            positiveButton.setTextColor(Color.parseColor("#ff99cc00"));
            positiveButton.setPadding(20, 10, 20, 60);
        }
    }
}