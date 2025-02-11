import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class EventManager {
    private static final String FILE_NAME = "events.txt";
    private ArrayList<EventData> events;

    public EventManager() {
        events = loadEvents();
    }

    private ArrayList<EventData> loadEvents() {
        ArrayList<EventData> eventList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String description = parts[2];
                    String organizer = parts[3];
                    eventList.add(new EventData(id, name, description, organizer));
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe, retornamos una lista vacía
        }
        return eventList;
    }

    public void saveEvents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (EventData event : events) {
                writer.write(event.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEvent(EventData event) {
        events.add(event);
        saveEvents();
    }

    public ArrayList<EventData> getEvents() {
        return events;
    }

    public static void main(String[] args) {
        EventManager manager = new EventManager();
        JFrame frame = new JFrame("Gestor de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField organizerField = new JTextField();
        JButton addButton = new JButton("Agregar Evento");
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nameField);
        panel.add(new JLabel("Descripción:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Organizador:"));
        panel.add(organizerField);
        panel.add(addButton);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String description = descriptionField.getText();
                String organizer = organizerField.getText();
                manager.addEvent(new EventData(id, name, description, organizer));
                StringBuilder sb = new StringBuilder();
                for (EventData ev : manager.getEvents()) {
                    sb.append(ev.toString()).append("\n");
                }
                displayArea.setText(sb.toString());
            }
        });
        
        frame.setVisible(true);
    }
}
