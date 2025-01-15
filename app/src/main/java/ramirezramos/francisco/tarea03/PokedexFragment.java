package ramirezramos.francisco.tarea03;

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

public class PokedexFragment extends Fragment {

    private FirebaseFirestore db;
    private final List<PokemonCaptured> capturedPokemonList = new ArrayList<>();
    private PokedexAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.pokedex_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PokedexAdapter(capturedPokemonList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadCapturedPokemon();

        return view;
    }

    private void loadCapturedPokemon() {
        db.collection("captured_pokemon")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    capturedPokemonList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Validar y asignar valores predeterminados si son nulos
                        String name = document.getString("name") != null ? document.getString("name") : "Desconocido";
                        String type = document.getString("type") != null ? document.getString("type") : "Desconocido";
                        Long weightLong = document.getLong("weight");
                        Long heightLong = document.getLong("height");
                        String imageUrl = document.getString("imageUrl") != null ? document.getString("imageUrl") : "";

                        // Manejar valores Long que pueden ser nulos
                        int weight = (weightLong != null) ? weightLong.intValue() : 0;
                        int height = (heightLong != null) ? heightLong.intValue() : 0;

                        capturedPokemonList.add(new PokemonCaptured(name, type, weight, height, imageUrl));
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al cargar la Pokédex", Toast.LENGTH_SHORT).show();
                });
    }
}
