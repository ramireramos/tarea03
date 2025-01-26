package ramirezramos.francisco.tarea03.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonDetailResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("weight")
    private int weight;

    @SerializedName("height")
    private int height;

    @SerializedName("types")
    private final List<Type> types;

    public PokemonDetailResponse(List<Type> types) {
        this.types = types;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public String getTypesAsString() {
        StringBuilder typeNames = new StringBuilder();
        for (Type type : types) {
            typeNames.append(type.getType().getName()).append(", ");
        }
        return typeNames.length() > 0 ? typeNames.substring(0, typeNames.length() - 2) : "Desconocido";
    }

    public static class Type {
        @SerializedName("type")
        private TypeDetail type;

        public TypeDetail getType() {
            return type;
        }
    }

    public static class TypeDetail {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }
}
