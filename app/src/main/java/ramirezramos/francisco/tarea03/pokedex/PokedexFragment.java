package ramirezramos.francisco.tarea03.pokedex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import ramirezramos.francisco.tarea03.PokemonDetailActivity;
import ramirezramos.francisco.tarea03.R;
import ramirezramos.francisco.tarea03.home.PokemonCaptured;

public class PokedexFragment extends Fragment {

    private FirebaseFirestore db;
    private final List<PokemonCaptured> capturedPokemonList = new ArrayList<>();
    private PokedexAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);

        // Configurar RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.pokedex_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configurar el adaptador y asignarlo al RecyclerView
        adapter = new PokedexAdapter(requireContext(), capturedPokemonList, this::onPokemonClick);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadCapturedPokemon(); // Cargar datos desde Firestore

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Leer configuración para permitir o no eliminación desde SharedPreferences
        getContext();
        boolean isDeleteEnabled = requireContext()
                .getSharedPreferences("settings", Context.MODE_PRIVATE)
                .getBoolean("allow_delete", true);

        // Actualizar adaptador con el estado de eliminación
        adapter.setAllowDelete(isDeleteEnabled);

        // Notificar cambios al adaptador
        adapter.notifyDataSetChanged();
    }

    private void loadCapturedPokemon() {
        db.collection("captured_pokemon")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    capturedPokemonList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("name");
                        String id = document.getString("id");
                        String type = document.getString("type");
                        long weight = document.getLong("weight");
                        long height = document.getLong("height");
                        String imageUrl = document.getString("imageUrl");

                        capturedPokemonList.add(new PokemonCaptured(name, id, type, (int) weight, (int) height, imageUrl));
                    }
                    adapter.notifyDataSetChanged(); // Refrescar la lista
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al cargar la Pokédex", Toast.LENGTH_SHORT).show();
                });
    }

    public void onPokemonClick(PokemonCaptured pokemon) {
        // Abrir la actividad de detalles del Pokémon con los datos correspondientes
        Intent intent = new Intent(requireContext(), PokemonDetailActivity.class);
        intent.putExtra("pokemon_name", pokemon.getName());
        intent.putExtra("pokemon_id", pokemon.getId());
        intent.putExtra("pokemon_type", pokemon.getType());
        intent.putExtra("pokemon_weight", pokemon.getWeight());
        intent.putExtra("pokemon_height", pokemon.getHeight());
        intent.putExtra("pokemon_image_url", pokemon.getImageUrl());
        startActivity(intent);
    }
}
