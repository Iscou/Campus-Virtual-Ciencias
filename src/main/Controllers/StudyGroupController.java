package Controllers;
//Se importa de Datas todo el contenido de los archivos StudyGroup, GroupData y Group
import Datas.Group;
import Datas.GroupData;
import Datas.StudyGroup;
import java.util.List;

public class StudyGroupController {

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
    //Agrega un nuevo grupo de estudio
    public static void addStudyGroup(String name, String description) {
        //Obtiene la lista de grupos actual
        List<Group> groups = GroupData.getGroups();
        //Crea el nuevo grupo y lo agrega en la lista
        GroupData.addGroup(new StudyGroup(groups.size() + 1, name, description));
    }
    //Retorna una lista de los grupos de estudio
    public static List<Group> getStudyGroups() {
        return GroupData.getGroups().stream()
                .filter(group -> group instanceof StudyGroup)
                .toList();
    }
}


