package ramirezramos.francisco.tarea03;

public class PokemonCaptured {

    private final String name;
    private final String type;
    private final int weight;
    private final int height;
    private final String imageUrl;

    public PokemonCaptured(String name, String type, int weight, int height, String imageUrl) {
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.height = height;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
