package Datas;

//Libreria para poder guardar los datos en archivos txt
import java.io.Serializable;

//Estructura de la clase Group
public class Group implements Serializable {
    //Atributos
    private int id;
    private String name;
    private String description;
    //Constructor
    public Group(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    //Metodos Get
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
