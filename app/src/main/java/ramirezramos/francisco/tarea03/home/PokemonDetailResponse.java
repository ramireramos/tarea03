package ramirezramos.francisco.tarea03.home;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonDetailResponse {

    @SerializedName("id")
    private int id; // ID del Pokémon

    @SerializedName("weight")
    private int weight; // Peso del Pokémon

    @SerializedName("height")
    private int height; // Altura del Pokémon

    @SerializedName("types")
    private List<Type> types; // Tipos del Pokémon

    // Métodos de acceso
    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    // Obtener los tipos como una cadena
    public String getTypesAsString() {
        StringBuilder typeNames = new StringBuilder();
        for (Type type : types) {
            typeNames.append(type.getType().getName()).append(", ");
        }
        // Si existen tipos, retorna los nombres, sino "Desconocido"
        return typeNames.length() > 0 ? typeNames.substring(0, typeNames.length() - 2) : "Desconocido";
    }

    // Clase anidada para manejar los tipos de Pokémon
    public static class Type {
        @SerializedName("type")
        private TypeDetail type; // Detalle del tipo

        public TypeDetail getType() {
            return type;
        }
    }

    // Clase para obtener el nombre del tipo
    public static class TypeDetail {
        @SerializedName("name")
        private String name; // Nombre del tipo (Ej. fuego, agua, etc.)

        public String getName() {
            return name;
        }
    }
}
