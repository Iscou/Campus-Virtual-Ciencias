package Datas;
//Libreria para manejar archivos 
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GroupData {

    //Se define la ruta del archivo donde se guardaran los grupos
    private static final String FILE_PATH = "Datas/groups.txt";
    //Lista de grupos para cargarlos en memoria

    private static final String FILE_PATH = "src/main/Datas/groupData.txt";

    private static List<Group> groups = new ArrayList<>();

    //Devuelve la lista de groups
    public static List<Group> getGroups() {
        return groups;
    }

    //Agrega un nuevo grupo y lo guarda en el archivo 
    public static void addGroup(Group group) {
        groups.add(group);
        saveGroupsToFile();
    }

    //Carga todos los grupos a memoria desde el archivo de groups
    public static void loadGroupsFromFile() {
        //Limpia la lista de grupos instanceados en memoria 
        groups.clear();
        File file = new File(FILE_PATH);

        // Verificar si el archivo existe antes de intentar cargarlo
        if (!file.exists()) {
            return;
        }
        //Lee el archivo para cargar los grupos guardados. Si ocurre un error, crea una lsita vacia
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            groups = (List<Group>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            groups = new ArrayList<>();
        }
    }

    //Guarda los grupos en su respectivo archivo
    public static void saveGroupsToFile() {
        File file = new File(FILE_PATH);

        // Asegurar que la carpeta "Datas" exista
        file.getParentFile().mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(groups);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


