package ramirezramos.francisco.tarea03.pokedex;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import ramirezramos.francisco.tarea03.R;
import ramirezramos.francisco.tarea03.home.PokemonCaptured;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder> {

    private final List<PokemonCaptured> capturedPokemonList;
    private final OnPokemonClickListener listener;
    private final FirebaseFirestore db;
    private final SharedPreferences sharedPreferences;
    private boolean allowDelete = true; // Controla si la eliminación está habilitada

    public interface OnPokemonClickListener {
        void onPokemonClick(PokemonCaptured pokemon);
    }

    public PokedexAdapter(Context context, List<PokemonCaptured> capturedPokemonList, OnPokemonClickListener listener) {
        this.capturedPokemonList = capturedPokemonList;
        this.listener = listener;
        this.db = FirebaseFirestore.getInstance();
        this.sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void setAllowDelete(boolean allowDelete) {
        this.allowDelete = allowDelete;
        notifyDataSetChanged();
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

        // Configurar datos del Pokémon
        holder.pokemonName.setText(pokemon.getName());
        holder.pokemonId.setText(pokemon.getId());
        holder.pokemonType.setText(pokemon.getType());
        holder.pokemonDetails.setText("Peso: " + pokemon.getWeight() + " | Altura: " + pokemon.getHeight());

        // Cargar imagen del Pokémon con Picasso
        Picasso.get()
                .load(pokemon.getImageUrl())
                .placeholder(R.drawable.pokemon)
                .error(R.drawable.poke2)
                .into(holder.pokemonImage);

        holder.itemView.setOnClickListener(v -> listener.onPokemonClick(pokemon));

        // Configurar botón de eliminar
        holder.deleteButton.setEnabled(allowDelete);
        holder.deleteButton.setOnClickListener(v -> {
            if (!allowDelete) {
                Toast.makeText(holder.itemView.getContext(), "La eliminación está inhabilitada", Toast.LENGTH_SHORT).show();
                return;
            }

            // Eliminar Pokémon de Firestore
            db.collection("captured_pokemon")
                    .whereEqualTo("name", pokemon.getName())
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        if (!querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                db.collection("captured_pokemon").document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(holder.itemView.getContext(), "Pokémon eliminado", Toast.LENGTH_SHORT).show();
                                            capturedPokemonList.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, capturedPokemonList.size());
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(holder.itemView.getContext(), "Error al eliminar el Pokémon", Toast.LENGTH_SHORT).show());
                            }
                        } else {
                            Toast.makeText(holder.itemView.getContext(), "No se encontró el Pokémon en Firestore", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(holder.itemView.getContext(), "Error al buscar el Pokémon en Firestore", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() {
        return capturedPokemonList.size();
    }

    static class PokedexViewHolder extends RecyclerView.ViewHolder {
        ImageView pokemonImage;
        TextView pokemonName, pokemonId, pokemonType, pokemonDetails;
        ImageButton deleteButton;

        public PokedexViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemonImage = itemView.findViewById(R.id.pokemon_image);
            pokemonId = itemView.findViewById(R.id.pokemon_id);
            pokemonName = itemView.findViewById(R.id.pokemon_name);
            pokemonType = itemView.findViewById(R.id.pokemon_type);
            pokemonDetails = itemView.findViewById(R.id.pokemon_details);
            deleteButton = itemView.findViewById(R.id.btn_delete_pokemon);
        }
    }
}
