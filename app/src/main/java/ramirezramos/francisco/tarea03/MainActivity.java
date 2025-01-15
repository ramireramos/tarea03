package ramirezramos.francisco.tarea03;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import ramirezramos.francisco.tarea03.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar Firebase y Firestore
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ejemplo de interacción con Firestore
                testFirestore();

                Snackbar.make(view, "Probando Firestore", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of IDs because each menu should be considered as top-level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_pokedex, R.id.nav_ajustes)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Método de prueba para agregar datos a Firestore.
     */
    private void testFirestore() {
        // Agregar un Pokémon de prueba a Firestore
        db.collection("pokemon_captured")
                .add(new Pokemon("Pikachu", "Electric", 6, 40))
                .addOnSuccessListener(documentReference -> {
                    Snackbar.make(binding.getRoot(), "¡Pokémon agregado!", Snackbar.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Snackbar.make(binding.getRoot(), "Error al agregar Pokémon: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                });
    }

    /**
     * Clase modelo para un Pokémon.
     */
    public static class Pokemon {
        private String name;
        private String type;
        private int weight;
        private int height;

        // Constructor vacío requerido por Firestore
        public Pokemon() {}

        public Pokemon(String name, String type, int weight, int height) {
            this.name = name;
            this.type = type;
            this.weight = weight;
            this.height = height;
        }

        // Getters y Setters
        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getWeight() {
            return weight;
        }

        public int getHeight() {
            return height;
        }
    }
}
