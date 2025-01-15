package ramirezramos.francisco.tarea03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder> {

    private final List<PokemonCaptured> capturedPokemonList;
    private final OnPokemonClickListener listener;
    private final FirebaseFirestore db;

    public interface OnPokemonClickListener {
        void onPokemonClick(PokemonCaptured pokemon);
    }

    public PokedexAdapter(List<PokemonCaptured> capturedPokemonList, OnPokemonClickListener listener) {
        this.capturedPokemonList = capturedPokemonList;
        this.listener = listener;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokedex, parent, false);
        return new PokedexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {
        PokemonCaptured pokemon = capturedPokemonList.get(position);

        // Configuración de la vista
        holder.pokemonName.setText(pokemon.getName());
        holder.pokemonType.setText(pokemon.getType());
        holder.pokemonDetails.setText("Peso: " + pokemon.getWeight() + " | Altura: " + pokemon.getHeight());
        Glide.with(holder.itemView.getContext())
                .load(pokemon.getImageUrl())
                .placeholder(R.drawable.delete)
                .into(holder.pokemonImage);

        // Acción al hacer clic en el elemento
        holder.itemView.setOnClickListener(v -> listener.onPokemonClick(pokemon));

        // Acción al hacer clic en el botón de eliminar
        holder.deleteButton.setOnClickListener(v -> {
            db.collection("captured_pokemon").document(pokemon.getName())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(holder.itemView.getContext(), "Pokémon eliminado", Toast.LENGTH_SHORT).show();
                        capturedPokemonList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, capturedPokemonList.size());
                    })
                    .addOnFailureListener(e -> Toast.makeText(holder.itemView.getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() {
        return capturedPokemonList.size();
    }

    static class PokedexViewHolder extends RecyclerView.ViewHolder {
        ImageView pokemonImage;
        TextView pokemonName, pokemonType, pokemonDetails;
        ImageButton deleteButton;

        public PokedexViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemonImage = itemView.findViewById(R.id.pokemon_image);
            pokemonName = itemView.findViewById(R.id.pokemon_name);
            pokemonType = itemView.findViewById(R.id.pokemon_type);
            pokemonDetails = itemView.findViewById(R.id.pokemon_details);
            deleteButton = itemView.findViewById(R.id.btn_delete_pokemon);
        }
    }
}
