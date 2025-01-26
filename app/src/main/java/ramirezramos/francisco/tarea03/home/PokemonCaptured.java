package ramirezramos.francisco.tarea03.home;

public class PokemonCaptured {

    private final String name;
    private final String id;
    private final String type;
    private final int weight;
    private final int height;
    private final String imageUrl;

    public PokemonCaptured(String name, String id, String type, int weight, int height, String imageUrl) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.weight = weight;
        this.height = height;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getName() { return name; }
    public String getId() { return id; }
    public String getType() { return type; }
    public int getWeight() { return weight; }
    public int getHeight() { return height; }
    public String getImageUrl() { return imageUrl; }
}
