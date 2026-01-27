package com.viewnext.presentation.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.viewnext.domain.model.Factura;
import com.viewnext.presentation.databinding.ItemFacturaBinding;
import com.viewnext.presentation.util.FacturaFormatter;
import com.viewnext.presentation.util.FacturaStyler;

/**
 * ViewHolder responsable de vincular los datos de una factura.
 */
public class FacturaViewHolder extends RecyclerView.ViewHolder {

    private final ItemFacturaBinding binding;

    public FacturaViewHolder(ItemFacturaBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Factura factura, FacturaAdapter.OnFacturaClickListener listener) {
        binding.descEstado.setText(factura.getDescEstado());
        binding.descEstado.setVisibility(View.VISIBLE);

        FacturaStyler.style(binding.descEstado, factura.getDescEstado());
        binding.importe.setText(FacturaFormatter.formatCurrency(factura.getImporteOrdenacion()));
        binding.fecha.setText(FacturaFormatter.formatFecha(factura.getFecha()));

        itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFacturaClick(factura); // pasa el evento al Fragment/Activity
            }
        });
    }
}
