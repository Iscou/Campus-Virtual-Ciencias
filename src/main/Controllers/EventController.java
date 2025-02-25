package Controllers;

//Importa la clase EventData del paquete de Datas
import Datas.EventData;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class EventController {
    //Almacena la ruta donde se guardaran los eventos
    private final String filePath = "src/main/Datas/events.txt";
    //Se instancia una lista para almacenar eventos
    private List<EventData> events;

    //Se inicia la lista de eventos, se verifica la existencia del archivo y se cargan en memoria los eventos
    public EventController() {
        events = new ArrayList<>();
        ensureFileExists();
        loadEventsFromFile();
    }
    //Verificar existencia del archivo
    private void ensureFileExists() {
        File file = new File(filePath);
        File directory = new File("src/main/Datas"); // Asegurar la carpeta correcta

        if (!directory.exists()) {
            directory.mkdirs(); // Crear la carpeta si no existe
        }

        if (!file.exists()) {
            try {
                file.createNewFile(); // Crear el archivo vacío
                saveEventsToFile();   // Guardar una lista vacía para evitar errores de lectura
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //Agregar evento
    public void addEvent(String name, String description, String organizer) {
        //Crea el nuevo evento
        int id = events.size() + 1;
        EventData newEvent = new EventData(id, name, description, organizer);
        //Lo agrega a la lista 
        events.add(newEvent);
        //Lo guarda en el archivo
        saveEventsToFile();
    }
    //Editar Evento
    public void updateEvent(int index, String name, String description, String organizer) {
        //Verifica que el indice del evento sea valido
        if (index >= 0 && index < events.size()) {
            //Obtiene acceso al evento
            EventData event = events.get(index);
            //Modofica los atributos del evento
            event.setName(name);
            event.setDescription(description);
            event.setOrganizer(organizer);
            //Guarda los cambios
            saveEventsToFile();
        }
    }
    //Retorna la lista de Eventos
    public List<EventData> getEvents() {
        return events;
    }
    //Guarda los eventos en los archivos
    private void saveEventsToFile() {
        //Abre el archivo en modo de escritura
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            //Se escrbie cada evento en una linea separando en comas sus atributos
            for (EventData event : events) {
                writer.write(event.getId() + "," + event.getName() + "," + event.getDescription() + "," + event.getOrganizer());
                writer.newLine();
            }
            //Si hay un fallo avisa que hubo un error
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Cargar eventos del archivo
    private void loadEventsFromFile() {
        //Limpia la lista de eventos para evitar fallas
        events.clear();
        //Abre el archivo para leer cada linea
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            //Segmenta la linea por cada coma
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                //Verifica que la linea segmentada derive en los 4 atributos
                if (parts.length == 4) {
                    //Si es asi, crea la instancia evento
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String description = parts[2];
                    String organizer = parts[3];
                    //Y lo agrega a la lista
                    events.add(new EventData(id, name, description, organizer));
                }
            }
            //De haber fallo en la lectura, notifica que hubo un error
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
