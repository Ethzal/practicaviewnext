package com.viewnext.energyapp.presentation.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaviewnext.databinding.ItemFacturaBinding;
import com.viewnext.energyapp.data.model.Factura;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FacturaAdapter extends RecyclerView.Adapter<FacturaAdapter.FacturaViewHolder> { // Adapter para mostrar las facturas con un RecyclerView
    private final List<Factura> facturas;

    // Constructor
    public FacturaAdapter(List<Factura> facturas){
        this.facturas = facturas;
    }

    // Crear ViewHolder para mostrar un ítem
    @NonNull
    @Override
    public FacturaAdapter.FacturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFacturaBinding binding = ItemFacturaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FacturaViewHolder(binding);
    }

    // Vincular datos al ViewHolder
    @Override
    public void onBindViewHolder(@NonNull FacturaAdapter.FacturaViewHolder holder, int position) {
        Factura factura = facturas.get(position);

        // Si la factura está pagada, no mostrar el estado y hacerlo invisible
        if (Objects.equals(factura.getDescEstado(), "Pagada")) {
            holder.binding.descEstado.setVisibility(View.GONE);
        } else {
            holder.binding.descEstado.setVisibility(View.VISIBLE);
            holder.binding.descEstado.setText(factura.getDescEstado());
            holder.binding.descEstado.setTextColor(Color.RED);
        }

        // Mostrar el importe a la derecha
        holder.binding.importe.setText(formatCurrency(factura.getImporteOrdenacion()));

        // Formatear la fecha
        String fechaFormateada = formatFecha(factura.getFecha());
        holder.binding.fecha.setText(fechaFormateada);
    }



    private String formatFecha(String fecha) {
        try {
            // Formato de la fecha original
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            // Nuevo formato
            SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

            // Parseamos la fecha original y la convertimos al nuevo formato
            return newFormat.format(Objects.requireNonNull(originalFormat.parse(fecha)));
        } catch (Exception e) {
            Log.e("FacturaAdapter", "Error formatting date", e);
            return fecha; // Si hay un error en el formato, regresamos la fecha original
        }
    }

    @Override
    public int getItemCount() { // Número de elementos de factura
        return facturas.size();
    }

    private String formatCurrency(double amount) { // Representar moneda
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        return format.format(amount);
    }

    // ViewHolder que guarda el binding del ítem para acceder a sus vistas
    public static class FacturaViewHolder extends RecyclerView.ViewHolder{
        private final ItemFacturaBinding binding;
        public FacturaViewHolder(ItemFacturaBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}