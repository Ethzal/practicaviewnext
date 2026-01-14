package com.example.presentation.ui.smartsolar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.presentation.databinding.FragmentMiInstalacionBinding;

public class MiInstalacionFragment extends Fragment {
    private FragmentMiInstalacionBinding binding;

    public MiInstalacionFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Binding
        binding = FragmentMiInstalacionBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}