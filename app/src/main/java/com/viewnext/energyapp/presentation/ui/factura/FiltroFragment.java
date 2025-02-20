package com.viewnext.energyapp.presentation.ui.factura;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.practicaviewnext.databinding.FragmentFiltroBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.viewnext.energyapp.presentation.viewmodel.FacturaViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FiltroFragment extends Fragment {
    private FragmentFiltroBinding binding;
    private FacturaViewModel viewModel;

    public FiltroFragment() { // Constructor vacío
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFiltroBinding.inflate(inflater, container, false);

        // Pasar el ViewModel de Factura
        viewModel = new ViewModelProvider(requireActivity()).get(FacturaViewModel.class);

        // Restaurar filtros si están disponibles
        restoreFiltersViewModel();

        // Valores por defecto de las fechas
        /*
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaDefault = dateFormat.format(Calendar.getInstance().getTime());
        binding.btnSelectDate.setText(fechaDefault);
        binding.btnSelectDateUntil.setText(fechaDefault);*/

        // Botón fecha desde
        binding.btnSelectDate.setOnClickListener(v -> showDatePicker(binding.btnSelectDate));
        binding.btnSelectDateUntil.setOnClickListener(v -> showDatePicker(binding.btnSelectDateUntil));

        // Recuperar el Bundle con maxImporte
        Bundle bundle = getArguments();
        if (bundle != null) {
            float maxImporte = bundle.getFloat("MAX_IMPORTE", 0f);

            if (maxImporte > 0) {
                binding.rangeSlider.setValueFrom(0f);
                binding.rangeSlider.setValueTo(maxImporte);
                binding.rangeSlider.setValues(0f, maxImporte);

                binding.tvMinValue.setText("0 €");
                binding.tvMaxValue.setText(maxImporte + " €");
                binding.tvMaxImporte.setText(maxImporte + " €");
            } else {
                binding.tvMinValue.setText("0 €");
                binding.tvMaxValue.setText("0 €");
            }
        }
        binding.rangeSlider.setLabelBehavior(LabelFormatter.LABEL_GONE);
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

    private void showDatePicker(MaterialButton button) {
        // Obtener la fecha actual
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear y mostrar el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireActivity(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Formatear la fecha seleccionada
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = sdf.format(selectedCalendar.getTime());

                    // Establecer la fecha en el botón correspondiente
                    button.setText(formattedDate);
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void restoreFiltersViewModel() {
        // Restaurar fecha de inicio
        viewModel.getFechaInicio().observe(getViewLifecycleOwner(), fecha -> {
            if (fecha != null) {
                binding.btnSelectDate.setText(fecha);
            }
        });

        // Restaurar fecha de fin
        viewModel.getFechaFin().observe(getViewLifecycleOwner(), fecha -> {
            if (fecha != null) {
                binding.btnSelectDateUntil.setText(fecha);
            }
        });

        // Restaurar valores del slider
        viewModel.getValoresSlider().observe(getViewLifecycleOwner(), valores -> {
            if (valores != null && valores.size() == 2) {
                binding.rangeSlider.setValues(valores.get(0), valores.get(1));
                binding.tvMinValue.setText(String.format(Locale.getDefault(), "%.0f €", valores.get(0)));
                binding.tvMaxValue.setText(String.format(Locale.getDefault(), "%.0f €", valores.get(1)));
            }
        });

        // Restaurar estados seleccionados
        viewModel.getEstados().observe(getViewLifecycleOwner(), estados -> {
            if (estados != null) {
                binding.checkPagadas.setChecked(estados.contains("Pagada"));
                binding.checkPendientesPago.setChecked(estados.contains("Pendiente de pago"));
                binding.checkCuotaFija.setChecked(estados.contains("Cuota Fija"));
                binding.checkPlanPago.setChecked(estados.contains("Plan de pago"));
                binding.checkAnuladas.setChecked(estados.contains("Anulada"));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Botón aplicar filtros
        binding.btnAplicar.setOnClickListener(v -> {
            List<String> estados = getStrings();
            String fechaInicio = binding.btnSelectDate.getText().toString();
            String fechaFin = binding.btnSelectDateUntil.getText().toString();

            /*
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaDefault = dateFormat.format(Calendar.getInstance().getTime());
            */
            List<Float> valoresSlider = binding.rangeSlider.getValues();
            float importeMin = valoresSlider.get(0);
            float importeMax = valoresSlider.get(1);

            // Guardar los filtros en el ViewModel
            viewModel.getEstados().setValue(estados);
            viewModel.getFechaInicio().setValue(fechaInicio);
            viewModel.getFechaFin().setValue(fechaFin);
            viewModel.getValoresSlider().setValue(valoresSlider);

            // Crear un Bundle con los filtros
            Bundle bundle = new Bundle();
            if (!estados.isEmpty()) {
                bundle.putStringArrayList("ESTADOS", (ArrayList<String>) estados);
            }
            bundle.putString("FECHA_INICIO", fechaInicio);
            bundle.putString("FECHA_FIN", fechaFin);
            bundle.putDouble("IMPORTE_MIN", importeMin);
            bundle.putDouble("IMPORTE_MAX", importeMax);

            // Pasar los datos a la actividad
            FacturaActivity activity = (FacturaActivity) getActivity();
            if (activity != null) {
                activity.aplicarFiltros(bundle);
                activity.restoreMainView();
            }
            getParentFragmentManager().popBackStack();
        });

        // Botón cerrar fragmento filtros
        binding.btnCerrar.setOnClickListener(v -> {
            List<String> estados = getStrings();
            String fechaInicio = binding.btnSelectDate.getText().toString();
            String fechaFin = binding.btnSelectDateUntil.getText().toString();
            List<Float> valoresSlider = binding.rangeSlider.getValues();

            // Guardar los filtros en el ViewModel
            viewModel.getEstados().setValue(estados);
            viewModel.getFechaInicio().setValue(fechaInicio);
            viewModel.getFechaFin().setValue(fechaFin);
            viewModel.getValoresSlider().setValue(valoresSlider);
            if (getActivity() != null) {
                FacturaActivity activity = (FacturaActivity) getActivity();
                activity.restoreMainView();
            }
            getParentFragmentManager().popBackStack();
        });

        // Botón borrar filtros
        binding.btnBorrar.setOnClickListener(v -> {
            binding.btnSelectDate.setText("día/mes/año");
            binding.btnSelectDateUntil.setText("día/mes/año");

            binding.rangeSlider.setValues(0f,viewModel.getMaxImporte());

            binding.checkPagadas.setChecked(false);
            binding.checkAnuladas.setChecked(false);
            binding.checkCuotaFija.setChecked(false);
            binding.checkPendientesPago.setChecked(false);
            binding.checkPlanPago.setChecked(false);
        });

        // Recuperar los datos tras rotar
        viewModel.getFechaInicio().observe(getViewLifecycleOwner(), fecha -> {
            if (fecha != null && !fecha.isEmpty()) {
                binding.btnSelectDate.setText(fecha);
            }
        });
        viewModel.getFechaFin().observe(getViewLifecycleOwner(), fecha -> {
            if (fecha != null && !fecha.isEmpty()) {
                binding.btnSelectDateUntil.setText(fecha);
            }
        });
        viewModel.getValoresSlider().observe(getViewLifecycleOwner(), valores -> {
            if (valores != null && valores.size() == 2) {
                binding.rangeSlider.setValues(valores.get(0), valores.get(1));
            }
        });
        viewModel.getEstados().observe(getViewLifecycleOwner(), estados -> {
            if (estados != null) {
                binding.checkPagadas.setChecked(estados.contains("Pagada"));
                binding.checkPendientesPago.setChecked(estados.contains("Pendiente de pago"));
                binding.checkCuotaFija.setChecked(estados.contains("Cuota Fija"));
                binding.checkPlanPago.setChecked(estados.contains("Plan de pago"));
                binding.checkAnuladas.setChecked(estados.contains("Anulada"));
            }
        });
    }

    @NonNull
    private List<String> getStrings() { // Estados
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
