package ramirezramos.francisco.tarea03;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PokemonDetailActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private Button captureButton;
    private String pokemonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        db = FirebaseFirestore.getInstance();

        // Referencias a los elementos de la UI
        ImageView pokemonImageView = findViewById(R.id.pokemon_detail_image);
        TextView pokemonNameTextView = findViewById(R.id.pokemon_name);
        TextView pokemonIdTextView = findViewById(R.id.pokemon_id);
        TextView pokemonTypeTextView = findViewById(R.id.pokemon_type);
        TextView pokemonWeightTextView = findViewById(R.id.pokemon_weight);
        TextView pokemonHeightTextView = findViewById(R.id.pokemon_height);
        captureButton = findViewById(R.id.capture_button);

        // Recuperar datos del intent
        pokemonName = getIntent().getStringExtra("pokemon_name");
        String pokemonId = getIntent().getStringExtra("pokemon_id");
        String pokemonType = getIntent().getStringExtra("pokemon_type");
        int pokemonWeight = getIntent().getIntExtra("pokemon_weight", 0);
        int pokemonHeight = getIntent().getIntExtra("pokemon_height", 0);
        String pokemonImageUrl = getIntent().getStringExtra("pokemon_image_url");

        // Mostrar los datos en la UI
        pokemonNameTextView.setText("Nombre: " + pokemonName);
        pokemonIdTextView.setText("Índice: " + pokemonId);
        pokemonTypeTextView.setText("Tipo: " + pokemonType);
        pokemonWeightTextView.setText("Peso: " + pokemonWeight + " kg");
        pokemonHeightTextView.setText("Altura: " + pokemonHeight + " m");

        Picasso.get()
                .load(pokemonImageUrl)
                .placeholder(R.drawable.pokemon)
                .error(R.drawable.poke2)
                .into(pokemonImageView);

        // Verificar si el Pokémon ya fue capturado
        checkIfPokemonCaptured();

        // Configurar el botón de captura
        captureButton.setOnClickListener(v -> capturePokemon(pokemonType, pokemonId, pokemonWeight, pokemonHeight, pokemonImageUrl));
    }

    private void checkIfPokemonCaptured() {
        // Verificar si el Pokémon ya fue capturado
        db.collection("captured_pokemon")
                .whereEqualTo("name", pokemonName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        captureButton.setEnabled(false);
                        captureButton.setText("Ya capturado");
                    } else {
                        captureButton.setEnabled(true);
                        captureButton.setText("Capturar Pokémon");
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al verificar captura", Toast.LENGTH_SHORT).show());
    }

    private void capturePokemon(String type, String id, int weight, int height, String imageUrl) {
        // Guardar el Pokémon en Firestore
        Map<String, Object> capturedPokemon = new HashMap<>();
        capturedPokemon.put("name", pokemonName);
        capturedPokemon.put("id", id);
        capturedPokemon.put("type", type);
        capturedPokemon.put("weight", weight);
        capturedPokemon.put("height", height);
        capturedPokemon.put("imageUrl", imageUrl);

        db.collection("captured_pokemon")
                .add(capturedPokemon)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, pokemonName + " capturado!", Toast.LENGTH_SHORT).show();
                    captureButton.setEnabled(false);
                    captureButton.setText("Ya capturado");
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al capturar el Pokémon", Toast.LENGTH_SHORT).show());
    }
}
