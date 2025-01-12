package ramirezramos.francisco.tarea03;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonDetailResponse {

    private String name;
    private int weight;
    private int height;
    private List<Type> types;

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public List<Type> getTypes() {
        return types;
    }

    public static class Type {
        @SerializedName("type")
        private TypeDetail type;

        public TypeDetail getType() {
            return type;
        }
    }

    public static class TypeDetail {
        private String name;

        public String getName() {
            return name;
        }
    }
}
