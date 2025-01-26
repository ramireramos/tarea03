package ramirezramos.francisco.tarea03.pokedex;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ramirezramos.francisco.tarea03.R;

public class PokedexViewHolder extends RecyclerView.ViewHolder {

    // Referencias a las vistas del item
    public ImageView pokemonImageView;
    public TextView nameTextView, idTextView, typeTextView, weightTextView, heightTextView;

    public PokedexViewHolder(@NonNull View itemView) {
        super(itemView);

        // Inicializar las vistas
        pokemonImageView = itemView.findViewById(R.id.pokemon_image);
        nameTextView = itemView.findViewById(R.id.pokemon_name);
        idTextView = itemView.findViewById(R.id.pokemon_id);
        typeTextView = itemView.findViewById(R.id.pokemon_type);
        weightTextView = itemView.findViewById(R.id.pokemon_weight);
        heightTextView = itemView.findViewById(R.id.pokemon_height);
    }
}
