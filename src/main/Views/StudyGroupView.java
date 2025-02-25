package Views;
//Se importan las datas pertinentes a la interfaz y las librerias graficas
import Datas.Group;
import Datas.GroupData;
import Datas.StudyGroup;
import java.awt.*;
import javax.swing.*;

public class StudyGroupView {
    //Panel donde se mostrarán los grupos de estudio existentes
    private JPanel groupListPanel;
    //Constructor
    public StudyGroupView() {
        //Se cargan los grupos desde un archivo 
        GroupData.loadGroupsFromFile();
        //Se construye la interfaz grafica
        showMainInterface();
    }
    //Metodo para mostrar la venta principal 
    private void showMainInterface() {
        //Se crea la ventana de Gestión de Grupos de estudio con sus respectivas dimensiones
        JFrame frame = new JFrame("Gestión de Grupos de Estudio");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        //Se crea un boton para crear nuevos grupos
        JPanel topPanel = new JPanel(new FlowLayout());
        JButton createButton = new JButton("Crear Grupo");
        createButton.addActionListener(e -> showCreateGroupDialog());
        topPanel.add(createButton);
        frame.add(topPanel, BorderLayout.NORTH);
        // Panel para listar los grupos de estudio
        groupListPanel = new JPanel();
        groupListPanel.setLayout(new BoxLayout(groupListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(groupListPanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        //Actualizacion de la lsita de grupos de estudio
        updateGroupList();
        frame.setVisible(true);
    }
    //Dialogo para que el usuario pueda ingresar los datos del evento
    private void showCreateGroupDialog() {
        //Abre el dialogo modal 
        JDialog dialog = new JDialog((Frame) null, "Crear Grupo de Estudio", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(3, 2));
        dialog.setLocationRelativeTo(null);
        //Camppos de texto para ingresar los datos
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JButton createButton = new JButton("Crear");
        //Se crean las respectivas etiquetas y campos para los datos
        dialog.add(new JLabel("Nombre:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Descripción:"));
        dialog.add(descriptionField);
        dialog.add(createButton);
        //Funcionalidad del boton crear
        createButton.addActionListener(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            //Si los campos no estan vacios, entonces se procede a crear el grupo
            if (!name.isEmpty() && !description.isEmpty()) {
                if (GroupData.getGroups() == null) {
                    JOptionPane.showMessageDialog(dialog, "Error: No se pudo cargar la lista de grupos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Creación del grupo de estudio
                StudyGroup newGroup = new StudyGroup(GroupData.getGroups().size() + 1, name, description);
                GroupData.addGroup(newGroup);

                // Actualizar la vista
                updateGroupList();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios.");
            }
        });

        dialog.setVisible(true);
    }

    private void updateGroupList() {
        groupListPanel.removeAll();

        if (GroupData.getGroups() != null) {
            for (Group group : GroupData.getGroups()) {
                if (group instanceof StudyGroup) {  // Filtrar solo los grupos de estudio
                    StudyGroup studyGroup = (StudyGroup) group;
                    
                    JPanel groupPanel = new JPanel(new BorderLayout());
                    groupPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                    JLabel nameLabel = new JLabel("📖 " + studyGroup.getName());
                    JButton detailsButton = new JButton("🔍 Ver Detalles");

                    detailsButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                            "📢 Nombre: " + studyGroup.getName() + "\n📝 Descripción: " + studyGroup.getDescription(),
                            "Detalles del Grupo", JOptionPane.INFORMATION_MESSAGE));

                    groupPanel.add(nameLabel, BorderLayout.WEST);
                    groupPanel.add(detailsButton, BorderLayout.EAST);
                    groupListPanel.add(groupPanel);
                }
            }
        }

        groupListPanel.revalidate();
        groupListPanel.repaint();
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(StudyGroupView::new);
    }*/
}
