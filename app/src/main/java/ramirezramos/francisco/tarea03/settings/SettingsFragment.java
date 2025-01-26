package ramirezramos.francisco.tarea03.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import ramirezramos.francisco.tarea03.AuthActivity;
import ramirezramos.francisco.tarea03.R;

public class SettingsFragment extends Fragment {

    private SharedPreferences preferences;
    private Switch allowDeleteSwitch;
    private Switch themeSwitch;
    private Spinner languageSpinner;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Inicializar SharedPreferences y ViewModel
        preferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Referencias a la UI
        allowDeleteSwitch = view.findViewById(R.id.switch_allow_delete);
        themeSwitch = view.findViewById(R.id.switch_theme);
        languageSpinner = view.findViewById(R.id.spinner_language);

        // Configurar el Switch para permitir eliminar Pokémon
        allowDeleteSwitch.setChecked(preferences.getBoolean("allow_delete", false));
        sharedViewModel.setIsDeleteEnabled(allowDeleteSwitch.isChecked());
        allowDeleteSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("allow_delete", isChecked).apply();
            sharedViewModel.setIsDeleteEnabled(isChecked);
            Toast.makeText(requireContext(), isChecked ? "Eliminación permitida" : "Eliminación no permitida", Toast.LENGTH_SHORT).show();
        });

        // Configurar el Switch para el tema (modo claro/oscuro)
        themeSwitch.setChecked(preferences.getBoolean("dark_mode", false));
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean("dark_mode", isChecked).apply();
            changeTheme(isChecked);
        });

        // Configurar el Spinner para cambiar el idioma
        String savedLanguage = preferences.getString("language", "Español");
        languageSpinner.setSelection(savedLanguage.equals("English") ? 1 : 0);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                if (!selectedLanguage.equals(preferences.getString("language", "Español"))) {
                    preferences.edit().putString("language", selectedLanguage).apply();
                    changeLanguage(selectedLanguage);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Configurar el botón de cerrar sesión
        view.findViewById(R.id.btn_logout).setOnClickListener(v -> showLogoutConfirmationDialog());

        // Configurar el botón de "Acerca de"
        view.findViewById(R.id.btn_about).setOnClickListener(v -> showAboutDialog());

        return view;
    }

    private void changeLanguage(String language) {
        Locale locale = language.equals("English") ? new Locale("en") : new Locale("es");
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Mostrar mensaje de cambio de idioma
        Toast.makeText(requireContext(), "Idioma cambiado a " + language, Toast.LENGTH_SHORT).show();

        // Recargar la actividad para aplicar el nuevo idioma
        requireActivity().recreate();
    }

    private void changeTheme(boolean isDarkMode) {
        // Cambiar el tema de la aplicación
        AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        requireActivity().recreate(); // Recargar la actividad para aplicar el nuevo tema
    }

    private void showLogoutConfirmationDialog() {
        // Crear cuadro de diálogo para confirmar cierre de sesión
        new AlertDialog.Builder(requireContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    preferences.edit().clear().apply(); // Limpiar preferencias
                    startActivity(new Intent(requireContext(), AuthActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    requireActivity().finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showAboutDialog() {
        // Crear cuadro de diálogo "Acerca de"
        new AlertDialog.Builder(requireContext())
                .setTitle("Acerca de la aplicación")
                .setMessage("Desarrollador: Francisco Ramírez Ramos\n\nAplicación que permite gestionar Pokémon, cambiar configuración, y más.\nVersión: 1.0.0\n\nGracias por usar esta app!")
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
