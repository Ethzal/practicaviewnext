package com.viewnext.presentation.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.viewnext.presentation.ui.smartsolar.DetallesFragment;
import com.viewnext.presentation.ui.smartsolar.EnergiaFragment;
import com.viewnext.presentation.ui.smartsolar.MiInstalacionFragment;

/**
 * Adapter para ViewPager2 que gestiona los fragmentos de SmartSolar.
 * Contiene tres pestañas: MiInstalacion, Energia y Detalles.
 */
public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * Devuelve el fragmento correspondiente según la posición de la pestaña.
     * @param position Posición de la pestaña (0, 1, 2)
     * @return Fragment asociado a la pestaña
     */
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

    /**
     * Número total de pestañas/fragments en el ViewPager.
     * @return Siempre 3
     */
    @Override
    public int getItemCount() {
        return 3; // Número de pestañas
    }
}