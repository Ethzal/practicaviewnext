package com.example.presentation.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.presentation.databinding.ItemFacturaBinding;
import com.example.domain.model.Factura;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FacturaAdapter extends RecyclerView.Adapter<FacturaAdapter.FacturaViewHolder> { // Adapter para mostrar las facturas con un RecyclerView
    private List<Factura> facturas;

    // Constructor
    public FacturaAdapter(List<Factura> facturas){
        this.facturas = facturas;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
        notifyDataSetChanged();
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

        holder.binding.descEstado.setVisibility(View.VISIBLE);
        holder.binding.descEstado.setText(factura.getDescEstado());

        // Estilos de los estados
        switch (factura.getDescEstado()) {
            case "Pagada":
                holder.binding.descEstado.setVisibility(View.GONE);
                break;

            case "Pendiente de pago":
                holder.binding.descEstado.setTextColor(Color.RED);
                holder.binding.descEstado.setTypeface(null, Typeface.NORMAL);
                break;

            case "Anulada":
                holder.binding.descEstado.setTextColor(Color.GRAY);
                holder.binding.descEstado.setTypeface(null, Typeface.ITALIC);
                break;

            default:
                holder.binding.descEstado.setTextColor(Color.BLACK);
                holder.binding.descEstado.setTypeface(null, Typeface.NORMAL);
                break;
        }

        // Mostrar el importe a la derecha
        holder.binding.importe.setText(formatCurrency(factura.getImporteOrdenacion()));

        // Formatear la fecha
        String fechaFormateada = formatFecha(factura.getFecha());
        holder.binding.fecha.setText(fechaFormateada);

        // PopUp nativo para la información por implementar
        holder.itemView.setOnClickListener(this::showPopup);
    }

    // PopUp nativo
    private void showPopup(View view) {
        new androidx.appcompat.app.AlertDialog.Builder(view.getContext())
                .setTitle("Información")
                .setMessage("Esta funcionalidad aún no está disponible")
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private String formatFecha(String fecha) {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

            // Parseamos la fecha original y la convertimos al nuevo formato
            return newFormat.format(Objects.requireNonNull(originalFormat.parse(fecha)));
        } catch (Exception e) {
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