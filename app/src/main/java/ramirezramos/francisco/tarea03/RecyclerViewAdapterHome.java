package ramirezramos.francisco.tarea03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ramirezramos.francisco.tarea03.databinding.AppCardviewBinding;

public class RecyclerViewAdapterHome extends RecyclerView.Adapter<HomeViewHolder> {

    private final ArrayList<HomeData> characters;
    private final Context context;

    public RecyclerViewAdapterHome (ArrayList<HomeData> characters, Context context){
        this.characters = characters;
        this.context = context;
    }

    // Metodo que crea el ViewHolder
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppCardviewBinding binding = AppCardviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent,false
        );
        return new HomeViewHolder(binding);
    }

    // enlazar datos con ViewHolder
    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        HomeData currentCharacter = this.characters.get(position);
        holder.bind(currentCharacter);

        // on_click_listener
        holder.itemView.setOnClickListener(view -> itemClicked(currentCharacter, view));
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    private void itemClicked(HomeData currentCharacter, View view) {
        // se ejecuta CharacterClicked de MainActivity,pulsando sobre el personaje
        //((MainActivity) context).characterClicked(currentCharacter, view);
    }
}
