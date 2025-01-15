package ramirezramos.francisco.tarea03;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokedexViewHolder extends RecyclerView.ViewHolder {

    public ImageView pokemonImageView;
    public TextView nameTextView;
    public TextView typeTextView;
    public TextView weightTextView;
    public TextView heightTextView;

    public PokedexViewHolder(@NonNull View itemView) {
        super(itemView);

        pokemonImageView = itemView.findViewById(R.id.pokemon_image);
        nameTextView = itemView.findViewById(R.id.pokemon_name);
        typeTextView = itemView.findViewById(R.id.pokemon_type);
        weightTextView = itemView.findViewById(R.id.pokemon_weight);
        heightTextView = itemView.findViewById(R.id.pokemon_height);
    }
}
