package ramirezramos.francisco.tarea03;

public class HomeData {

    private final String image;
    private final String name;
    public final String indice;

    public HomeData(String image, String name, String indice) {

        this.image = image;
        this.name = name;
        this.indice = indice;
    }

    public String getImage() {
        return image;
    } // Metodo para obtener la URL de la imagen

    public String getName() {
        return name;
    } //Metodo para obtener el nombre del personaje

    public String getIndice() {
        return indice;
    } // Metodo para obtener la descripción
}

