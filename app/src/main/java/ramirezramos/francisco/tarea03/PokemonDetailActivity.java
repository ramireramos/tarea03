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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        db = FirebaseFirestore.getInstance(); // Inicializa Firestore

        ImageView pokemonImageView = findViewById(R.id.pokemon_detail_image);
        TextView pokemonNameTextView = findViewById(R.id.pokemon_name);
        TextView pokemonTypeTextView = findViewById(R.id.pokemon_type);
        TextView pokemonWeightTextView = findViewById(R.id.pokemon_weight);
        TextView pokemonHeightTextView = findViewById(R.id.pokemon_height);
        Button captureButton = findViewById(R.id.capture_button);

        // Recuperar datos del intent
        String pokemonName = getIntent().getStringExtra("pokemon_name");
        String pokemonType = getIntent().getStringExtra("pokemon_type");
        int pokemonWeight = getIntent().getIntExtra("pokemon_weight", 0);
        int pokemonHeight = getIntent().getIntExtra("pokemon_height", 0);
        String pokemonImageUrl = getIntent().getStringExtra("pokemon_image_url");

        // Mostrar los datos en la UI
        pokemonNameTextView.setText("Nombre: " + pokemonName);
        pokemonTypeTextView.setText("Tipo: " + pokemonType);
        pokemonWeightTextView.setText("Peso: " + pokemonWeight + " kg");
        pokemonHeightTextView.setText("Altura: " + pokemonHeight + " m");

        Picasso.get()
                .load(pokemonImageUrl)
                .placeholder(R.drawable.pokemon)
                .error(R.drawable.poke2)
                .into(pokemonImageView);

        // Configurar el botón de captura
        captureButton.setOnClickListener(v -> {
            Map<String, Object> capturedPokemon = new HashMap<>();
            capturedPokemon.put("name", pokemonName);
            capturedPokemon.put("type", pokemonType);
            capturedPokemon.put("weight", pokemonWeight);
            capturedPokemon.put("height", pokemonHeight);
            capturedPokemon.put("imageUrl", pokemonImageUrl);

            db.collection("captured_pokemon") // Nombre de la colección en Firestore
                    .add(capturedPokemon)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, pokemonName + " capturado!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error al capturar el Pokémon", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
