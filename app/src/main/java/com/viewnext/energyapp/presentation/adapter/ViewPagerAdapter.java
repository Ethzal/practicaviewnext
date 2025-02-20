package com.viewnext.energyapp.presentation.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.viewnext.energyapp.presentation.ui.smartsolar.DetallesFragment;
import com.viewnext.energyapp.presentation.ui.smartsolar.EnergiaFragment;
import com.viewnext.energyapp.presentation.ui.smartsolar.MiInstalacionFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new EnergiaFragment();
            case 2:
                return new DetallesFragment();
            default:
                return new MiInstalacionFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Número de pestañas
    }
}
