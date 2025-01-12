package ramirezramos.francisco.tarea03.api;

import ramirezramos.francisco.tarea03.PokemonDetailResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {
    @GET("pokemon/{name}")
    Call<PokemonDetailResponse> getPokemonDetails(@Path("name") String name);


    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

}
