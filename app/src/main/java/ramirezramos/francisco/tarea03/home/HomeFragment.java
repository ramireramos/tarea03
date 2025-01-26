package ramirezramos.francisco.tarea03.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ramirezramos.francisco.tarea03.R;
import ramirezramos.francisco.tarea03.api.ApiClient;
import ramirezramos.francisco.tarea03.api.PokeApiService;
import ramirezramos.francisco.tarea03.api.PokemonResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadPokemon();
        return root;
    }

    private void loadPokemon() {
        PokeApiService apiService = ApiClient.getInstance().create(PokeApiService.class);
        Call<PokemonResponse> call = apiService.getPokemonList(100, 0);

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PokemonResponse.Pokemon> pokemonList = response.body().getResults();
                    adapter = new PokemonAdapter(pokemonList);
                    recyclerView.setAdapter(adapter);
                } else {
                    System.out.println("No se pudo obtener la lista de Pokémon.");
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                System.out.println("Error de conexión con la API.");
            }
        });
    }
}
