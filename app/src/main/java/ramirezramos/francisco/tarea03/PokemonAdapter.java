package ramirezramos.francisco.tarea03;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ramirezramos.francisco.tarea03.api.ApiClient;
import ramirezramos.francisco.tarea03.api.PokeApiService;
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
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.pokemon) // Imagen de carga
                .error(R.drawable.poke2)         // Imagen de error
                .into(holder.pokemonImageView);

        // Evento de clic
        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, PokemonDetailActivity.class);
            intent.putExtra("pokemon_name", pokemon.getName());
            intent.putExtra("pokemon_image_url", imageUrl);

            // Llama a la API para obtener detalles adicionales del Pokémon
            ApiClient.getInstance().create(PokeApiService.class)
                    .getPokemon(Integer.parseInt(id))
                    .enqueue(new retrofit2.Callback<PokemonDetailResponse>() {
                        @Override
                        public void onResponse(retrofit2.Call<PokemonDetailResponse> call, retrofit2.Response<PokemonDetailResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                PokemonDetailResponse details = response.body();
                                intent.putExtra("pokemon_type", details.getTypesAsString());
                                intent.putExtra("pokemon_weight", details.getWeight());
                                intent.putExtra("pokemon_height", details.getHeight());
                                context.startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<PokemonDetailResponse> call, Throwable t) {
                            // Manejo de errores
                            t.printStackTrace();
                        }
                    });
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
            nameTextView = itemView.findViewById(R.id.pokemon_name);
            pokemonImageView = itemView.findViewById(R.id.pokemon_image);
        }
    }
}