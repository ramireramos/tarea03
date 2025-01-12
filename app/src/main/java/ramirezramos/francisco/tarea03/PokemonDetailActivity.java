package ramirezramos.francisco.tarea03;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PokemonDetailActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        // Recupera referencias de las vistas
        ImageView pokemonImageView = findViewById(R.id.pokemon_detail_image);
        TextView pokemonNameTextView = findViewById(R.id.pokemon_name);
        TextView pokemonTypeTextView = findViewById(R.id.pokemon_type);
        TextView pokemonWeightTextView = findViewById(R.id.pokemon_weight);
        TextView pokemonHeightTextView = findViewById(R.id.pokemon_height);

        // Recuperar datos del intent
        String pokemonName = getIntent().getStringExtra("pokemon_name");
        String pokemonType = getIntent().getStringExtra("pokemon_type");
        int pokemonWeight = getIntent().getIntExtra("pokemon_weight", 0);
        int pokemonHeight = getIntent().getIntExtra("pokemon_height", 0);
        String pokemonImageUrl = getIntent().getStringExtra("pokemon_image_url");

        // Actualizar vistas
        pokemonNameTextView.setText("Nombre: " + (pokemonName != null ? pokemonName : "Desconocido"));
        pokemonTypeTextView.setText("Tipo: " + (pokemonType != null ? pokemonType : "Desconocido"));
        pokemonWeightTextView.setText("Peso: " + (pokemonWeight > 0 ? pokemonWeight + " kg" : "N/A"));
        pokemonHeightTextView.setText("Altura: " + (pokemonHeight > 0 ? pokemonHeight + " m" : "N/A"));

        // Cargar la imagen con Picasso
        Picasso.get()
                .load(pokemonImageUrl)
                .placeholder(R.drawable.pokemon)
                .error(R.drawable.poke2)
                .into(pokemonImageView);
    }
}
