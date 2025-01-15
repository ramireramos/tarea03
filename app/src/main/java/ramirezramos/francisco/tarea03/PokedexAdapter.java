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
                .inflate(R.layout.item_pokemon, parent, false);
        return new PokedexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {
        PokemonCaptured pokemon = capturedPokemonList.get(position);
        holder.nameTextView.setText(pokemon.getName());
        holder.typeTextView.setText("Tipo: " + pokemon.getType());
        holder.weightTextView.setText("Peso: " + pokemon.getWeight() + " kg");
        holder.heightTextView.setText("Altura: " + pokemon.getHeight() + " m");

        Picasso.get()
                .load(pokemon.getImageUrl())
                .placeholder(R.drawable.pokemon)
                .error(R.drawable.poke2)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return capturedPokemonList.size();
    }

    public static class PokedexViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView typeTextView;
        TextView weightTextView;
        TextView heightTextView;

        public PokedexViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pokemon_image);
            nameTextView = itemView.findViewById(R.id.pokemon_name);
            typeTextView = itemView.findViewById(R.id.pokemon_type);
            weightTextView = itemView.findViewById(R.id.pokemon_weight);
            heightTextView = itemView.findViewById(R.id.pokemon_height);
        }
    }
}
