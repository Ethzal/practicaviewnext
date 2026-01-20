package com.viewnext.presentation.ui.smartsolar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.viewnext.presentation.databinding.FragmentEnergiaBinding;

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