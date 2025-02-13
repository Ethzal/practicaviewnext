package com.viewnext.energyapp.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaviewnext.databinding.ItemFacturaBinding;
import com.viewnext.energyapp.data.model.Factura;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FacturaAdapter extends RecyclerView.Adapter<FacturaAdapter.FacturaViewHolder> {
    private List<Factura> facturas;

    public FacturaAdapter(List<Factura> facturas){
        this.facturas = facturas;
    }

    @NonNull
    @Override
    public FacturaAdapter.FacturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFacturaBinding binding = ItemFacturaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FacturaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FacturaAdapter.FacturaViewHolder holder, int position) {
        Factura factura = facturas.get(position);
        holder.binding.descEstado.setText(String.valueOf(factura.getDescEstado()));
        holder.binding.importe.setText(formatCurrency(factura.getImporteOrdenacion()));
        holder.binding.fecha.setText(factura.getFecha());
    }

    @Override
    public int getItemCount() {
        return facturas.size();
    }

    private String formatCurrency(double amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return format.format(amount);
    }

    public static class FacturaViewHolder extends RecyclerView.ViewHolder{
        private final ItemFacturaBinding binding;
        public FacturaViewHolder(ItemFacturaBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
