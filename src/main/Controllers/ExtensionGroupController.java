package Controllers;
//Se importa de Datas todo el contenido de los archivos ExtensionGroup, GroupData y Group
import Datas.ExtensionGroup;
import Datas.Group;
import Datas.GroupData;
import java.util.List;

public class ExtensionGroupController {
    
    //Carga los grupos desde el archivo que los almacena (groups.txt)
    public static void loadGroupsFromFile() {
        //Llama a la funcion que hace la lectura de los grupos del archivo
        GroupData.loadGroupsFromFile();
    }
    //Guarda los grupos en memoria en el archivo de groups
    public static void saveGroupsToFile() {
        //Llama a la funcion guarda los grupos en memoria en el archivo groups
        GroupData.saveGroupsToFile();
    }
    //Agrega un nuevo grupo de extension
    public static void addExtensionGroup(String name, String description) {
        //Obtiene la lista de grupos actual
        List<Group> groups = GroupData.getGroups();
        //Crea el nuevo grupo y lo agrega en la lista
        GroupData.addGroup(new ExtensionGroup(groups.size() + 1, name, description));
    }

    //Retorna una lista de los grupos de extension
    public static List<Group> getExtensionGroups() {
        return GroupData.getGroups().stream()
                .filter(group -> group instanceof ExtensionGroup)
                .toList();
    }
}
