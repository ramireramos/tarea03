package ramirezramos.francisco.tarea03.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonResponse {

    @SerializedName("results")
    private List<Pokemon> results;

    public List<Pokemon> getResults() {
        return results;
    }

    public static class Pokemon {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
