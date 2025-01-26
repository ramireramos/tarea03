package ramirezramos.francisco.tarea03;

import android.os.Bundle;
import android.view.Menu;

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

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void testFirestore() {
        db.collection("pokemon_captured")
                .add(new Pokemon("Pikachu", "1234", "Electric", 6, 40))
                .addOnSuccessListener(documentReference -> Snackbar.make(binding.getRoot(), "¡Pokémon agregado!", Snackbar.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Snackbar.make(binding.getRoot(), "Error al agregar Pokémon", Snackbar.LENGTH_SHORT).show());
    }

    public static class Pokemon {
        private String name;
        private String id;
        private String type;
        private int weight;
        private int height;

        public Pokemon() {}

        public Pokemon(String name, String id, String type, int weight, int height) {
            this.name = name;
            this.id = id;
            this.type = type;
            this.weight = weight;
            this.height = height;
        }

        public String getName() {
            return name;
        }
        public String getId() {
            return id;
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
