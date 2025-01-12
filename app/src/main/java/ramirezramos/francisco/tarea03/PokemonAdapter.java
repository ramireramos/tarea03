package ramirezramos.francisco.tarea03;

import static com.squareup.picasso.Picasso.get;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ramirezramos.francisco.tarea03.api.PokemonResponse;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private final List<PokemonResponse.Pokemon> pokemonList;

    public PokemonAdapter(List<PokemonResponse.Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokemonResponse.Pokemon pokemon = pokemonList.get(position);
        holder.nameTextView.setText(pokemon.getName());

        // Extraer el ID del Pokémon desde la URL
        String url = pokemon.getUrl(); // Ejemplo: "https://pokeapi.co/api/v2/pokemon/1/"
        String[] parts = url.split("/");
        String id = parts[parts.length - 1];

        // Construir la URL de la imagen
        String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + id + ".png";

        // Cargar la imagen con Picasso
        get()
                .load(imageUrl)
                .placeholder(R.drawable.pokemon) // Imagen de carga
                .error(R.drawable.poke2)             // Imagen de error
                .into(holder.pokemonImageView);

        // Evento de clic
        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            var intent = new Intent(context, PokemonDetailActivity.class);
            intent.putExtra("pokemon_name", pokemon.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView pokemonImageView;
        TextView nameTextView;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_view);
            pokemonImageView = itemView.findViewById(R.id.pokemon_image); // ID debe coincidir con el XML
        }
    }
}
