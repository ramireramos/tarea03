package ramirezramos.francisco.tarea03;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import ramirezramos.francisco.tarea03.databinding.AppCardviewBinding;

public class HomeViewHolder extends RecyclerView.ViewHolder {
    private final AppCardviewBinding binding; // Declaramos Appcardviewbinding
    TextView nameTextView;
    ImageView pokemonImageView;
    public HomeViewHolder(AppCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        //super(itemView);
        nameTextView = itemView.findViewById(R.id.text_view);
        pokemonImageView = itemView.findViewById(R.id.pokemon_image);
    }    // Constructor del ViewHolder

    // Metodo para enlazar los datos del personaje con las vistas
    public void bind (HomeData character){
        Picasso picasso = Picasso.get();
        // usamos libreria picasso para que se muestren imagenes online
        binding.character.setText(character.getName()); // Establece el nombre del personaje
        binding.character.setText(character.getName()); // Establece la descripción del personaje en el TextView correspondiente
        binding.description.setText(String.valueOf(character.getDescription())); binding.character.setText(character.getName()); // Establece la descripción del personaje en el TextView correspondiente
        binding.executePendingBindings();
    }
}