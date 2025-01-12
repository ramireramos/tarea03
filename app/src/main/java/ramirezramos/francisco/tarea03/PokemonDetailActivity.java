package ramirezramos.francisco.tarea03;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PokemonDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        // Recuperar referencias de las vistas
        ImageView pokemonImageView = findViewById(R.id.pokemon_detail_image);
        TextView pokemonNameTextView = findViewById(R.id.tv_name);
        TextView pokemonTypeTextView = findViewById(R.id.tv_type);
        TextView pokemonWeightTextView = findViewById(R.id.tv_weight);
        TextView pokemonHeightTextView = findViewById(R.id.tv_height);

        // Recuperar datos del intent
        String pokemonName = getIntent().getStringExtra("pokemon_name");
        String pokemonType = getIntent().getStringExtra("pokemon_type");
        int pokemonWeight = getIntent().getIntExtra("pokemon_weight", 0);
        int pokemonHeight = getIntent().getIntExtra("pokemon_height", 0);
        String pokemonImageUrl = getIntent().getStringExtra("pokemon_image_url");

        // Configurar la vista con los datos
        pokemonNameTextView.setText("Nombre: " + pokemonName);
        pokemonTypeTextView.setText("Tipo: " + pokemonType);
        pokemonWeightTextView.setText("Peso: " + pokemonWeight + " kg");
        pokemonHeightTextView.setText("Altura: " + pokemonHeight + " m");

        // Cargar la imagen con Picasso
        Picasso.get()
                .load(pokemonImageUrl)
                .placeholder(R.drawable.pokemon) // Imagen de carga
                .error(R.drawable.poke2)             // Imagen de error
                .into(pokemonImageView);
    }
}
