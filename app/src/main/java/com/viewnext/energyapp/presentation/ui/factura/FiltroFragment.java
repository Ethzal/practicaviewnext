package com.viewnext.energyapp.presentation.ui.factura;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.practicaviewnext.databinding.FragmentFiltroBinding;
import com.google.android.material.slider.RangeSlider;
import com.viewnext.energyapp.presentation.viewmodel.FacturaViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FiltroFragment extends Fragment {
    private FragmentFiltroBinding binding;
    private RangeSlider rangeSlider;
    private TextView tvMinValue, tvMaxValue;
    private FacturaViewModel viewModel;

    public FiltroFragment() { // Constructor vacío
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFiltroBinding.inflate(inflater, container, false);

        binding.btnSelectDate.setOnClickListener(v -> {
            // Crear un calendario para obtener la fecha actual
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            // Crear el DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // Formatear la fecha en el formato adecuado
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String formattedDate = sdf.format(calendar.getTime());  // Formato correcto

                            // Mostrar la fecha seleccionada en el TextView usando ViewBinding
                            binding.btnSelectDate.setText(formattedDate);
                        }

                    },
                    year, month, dayOfMonth); // Pasamos la fecha actual como valor inicial

            // Mostrar el DatePickerDialog
            datePickerDialog.show();
        });

        binding.btnSelectDateUntil.setOnClickListener(v -> {
            // Crear un calendario para obtener la fecha actual
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            // Crear el DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // Mostrar la fecha seleccionada en el TextView usando ViewBinding
                            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            binding.btnSelectDateUntil.setText(selectedDate);
                        }
                    },
                    year, month, dayOfMonth); // Pasamos la fecha actual como valor inicial

            // Mostrar el DatePickerDialog
            datePickerDialog.show();
        });

        // Recuperar el Bundle con maxImporte
        Bundle bundle = getArguments();
        if (bundle != null) {
            float maxImporte = bundle.getFloat("MAX_IMPORTE", 0f); // Valor por defecto si no se pasa maxImporte
            Log.e("FiltroFragment", "maxImporte: " + maxImporte);

            // Asegurarse de que maxImporte no sea cero o incorrecto
            if (maxImporte > 0) {
                // Establecer los valores en el RangeSlider
                binding.rangeSlider.setValueFrom(0f);  // Valor mínimo
                binding.rangeSlider.setValueTo(maxImporte);  // Valor máximo
                binding.rangeSlider.setValues(0f, maxImporte);  // Inicializa el rango

                // Establecer los valores iniciales en los TextViews
                binding.tvMinValue.setText("0 €");
                binding.tvMaxValue.setText(maxImporte + " €");
                binding.tvMaxImporte.setText(maxImporte + " €");
            } else {
                // Manejar el caso en que maxImporte es 0 o inválido
                binding.tvMinValue.setText("0 €");
                binding.tvMaxValue.setText("0 €");
            }
        }

        // Listener para el RangeSlider
        binding.rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> values = slider.getValues();
                if (values.size() == 2) {
                    float minValue = values.get(0);
                    float maxValue = values.get(1);

                    // Actualizar los TextViews con los valores seleccionados
                    binding.tvMinValue.setText(String.format("%.0f €", minValue));
                    binding.tvMaxValue.setText(String.format("%.0f €", maxValue));
                }
            }
        });
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Botón para cerrar el fragmento
        binding.btnAplicar.setOnClickListener(v -> {
            List<String> estados = getStrings();
            String fechaInicioString = binding.btnSelectDate.getText().toString();
            String fechaFinString = binding.btnSelectDateUntil.getText().toString();

            Date fechaInicio = parseFecha(fechaInicioString);
            Date fechaFin = parseFecha(fechaFinString);


            List<Float> valoresSlider = binding.rangeSlider.getValues();
            float importeMin = valoresSlider.get(0);
            float importeMax = valoresSlider.get(1);

            // Crear un Bundle para pasar los datos
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("ESTADOS", new ArrayList<>(estados));
            bundle.putSerializable("FECHA_INICIO", fechaInicio);
            bundle.putSerializable("FECHA_FIN", fechaFin);
            bundle.putDouble("IMPORTE_MIN", importeMin);
            bundle.putDouble("IMPORTE_MAX", importeMax);

            // Pasar los datos a la actividad
            FacturaActivity activity = (FacturaActivity) getActivity();
            if (activity != null) {
                activity.aplicarFiltros(bundle);
                activity.restoreMainView();
            }

            // Cerrar el fragmento
            getParentFragmentManager().popBackStack();
        });
        binding.btnCerrar.setOnClickListener(v -> {
            if (getActivity() != null) {
                FacturaActivity activity = (FacturaActivity) getActivity();
                activity.restoreMainView();
            }
            getParentFragmentManager().popBackStack();
        });
        binding.btnBorrar.setOnClickListener(v -> {
            binding.btnSelectDate.setText("día/mes/año");
            binding.btnSelectDateUntil.setText("día/mes/año");

            viewModel = new ViewModelProvider(requireActivity()).get(FacturaViewModel.class);
            binding.rangeSlider.setValues(0f,viewModel.getMaxImporte());

            binding.checkPagadas.setChecked(false);
            binding.checkAnuladas.setChecked(false);
            binding.checkCuotaFija.setChecked(false);
            binding.checkPendientesPago.setChecked(false);
            binding.checkPlanPago.setChecked(false);
        });
    }

    @NonNull
    private List<String> getStrings() {
        List<String> estados = new ArrayList<>();

        if (binding.checkPagadas.isChecked()) {
            estados.add("Pagada");
        }
        if (binding.checkPendientesPago.isChecked()) {
            estados.add("Pendiente de pago");
        }
        if (binding.checkCuotaFija.isChecked()) {
            estados.add("Cuota Fija");
        }
        if (binding.checkPlanPago.isChecked()) {
            estados.add("Plan de pago");
        }
        if (binding.checkAnuladas.isChecked()) {
            estados.add("Anulada");
        }
        return estados;
    }

    private Date parseFecha(String fechaString) {
        String fechaSoloFecha = fechaString.split(" ")[0];

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return sdf.parse(fechaSoloFecha);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
