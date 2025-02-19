package com.viewnext.energyapp.presentation.ui.smartsolar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.practicaviewnext.R;
import com.example.practicaviewnext.databinding.FragmentDetallesBinding;
import com.example.practicaviewnext.databinding.FragmentEnergiaBinding;

public class EnergiaFragment extends Fragment {
    private FragmentEnergiaBinding binding;

    public EnergiaFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Binding
        binding = FragmentEnergiaBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}