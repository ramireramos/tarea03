package ramirezramos.francisco.tarea03;

public class HomeData {

    private final String image;
    private final String character;
    public final String description;

    public HomeData(String image, String character, String description) {

        this.image = image;
        this.character = character;
        this.description = description;
    }

    public String getImage() {
        return image;
    } // Metodo para obtener la URL de la imagen

    public String getName() {
        return character;
    } //Metodo para obtener el nombre del personaje

    public String getDescription() {
        return description;
    } // Metodo para obtener la descripción
}

