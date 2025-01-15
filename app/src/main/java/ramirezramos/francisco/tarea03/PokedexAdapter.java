package ramirezramos.francisco.tarea03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder> {

    private final List<PokemonCaptured> capturedPokemonList;

    public PokedexAdapter(List<PokemonCaptured> capturedPokemonList) {
        this.capturedPokemonList = capturedPokemonList;
    }

    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokedex, parent, false); // Asegúrate de usar el nombre correcto del archivo XML
        return new PokedexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {
        PokemonCaptured pokemon = capturedPokemonList.get(position);
        holder.nameTextView.setText(pokemon.getName());
        holder.typeTextView.setText(pokemon.getType());
        holder.weightTextView.setText(String.format("Weight: %d", pokemon.getWeight()));
        holder.heightTextView.setText(String.format("Height: %d", pokemon.getHeight()));
        Picasso.get()
                .load(pokemon.getImageUrl())
                .placeholder(R.drawable.pokemon) // Imagen de carga
                .error(R.drawable.poke2)             // Imagen de error
                .into(holder.pokemonImageView);
    }

    @Override
    public int getItemCount() {
        return capturedPokemonList.size();
    }

    public static class PokedexViewHolder extends RecyclerView.ViewHolder {
        ImageView pokemonImageView;
        TextView nameTextView, typeTextView, weightTextView, heightTextView;

        public PokedexViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImageView = itemView.findViewById(R.id.pokemon_image);  // ID del ImageView
            nameTextView = itemView.findViewById(R.id.pokemon_name);      // ID del TextView para el nombre
            typeTextView = itemView.findViewById(R.id.pokemon_type);      // ID del TextView para el tipo
            weightTextView = itemView.findViewById(R.id.pokemon_weight);  // ID del TextView para el peso
            heightTextView = itemView.findViewById(R.id.pokemon_height);  // ID del TextView para la altura
        }
    }
}
