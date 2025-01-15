package ramirezramos.francisco.tarea03;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PokedexActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private final List<PokemonCaptured> capturedPokemonList = new ArrayList<>();
    private PokedexAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        RecyclerView recyclerView = findViewById(R.id.pokedex_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PokedexAdapter(capturedPokemonList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // Cargar Pokémon capturados desde Firestore
        db.collection("captured_pokemon")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    capturedPokemonList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("name");
                        String type = document.getString("type");
                        long weight = document.getLong("weight");
                        long height = document.getLong("height");
                        String imageUrl = document.getString("imageUrl");

                        capturedPokemonList.add(new PokemonCaptured(
                                name, type, (int) weight, (int) height, imageUrl));
                    }
                    adapter.notifyDataSetChanged(); // Actualizar la lista
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar la Pokédex", Toast.LENGTH_SHORT).show();
                });
    }
}
