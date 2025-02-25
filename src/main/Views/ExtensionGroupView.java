package Views;
//Se importan las datas pertinentes a la interfaz y las liberias graficas
import Datas.ExtensionGroup;
import Datas.GroupData;
import java.awt.*;
import javax.swing.*;


public class ExtensionGroupView {
    //Panel donde se mostrarán los grupos de extensión existentes
    public class ExtensionGroupView extends JFrame {

        private JPanel groupListPanel;

        //Constructor 
        public ExtensionGroupView() {

            //Se cargan los grupos desde un archivo 
            GroupData.loadGroupsFromFile();
            //Se construye la interfaz grafica

            GroupData.loadGroupsFromFile();        

            showMainInterface();
        }
        //Metodo para mostrar la venta principal 
        private void showMainInterface() {
            //Se crea la ventana de Gestión de Grupos de Extensión con sus respectivas dimensiones
            JFrame frame = new JFrame("Gestión de Grupos de Extensión");
            frame.setSize(600, 500);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());
            //Se crea un boton para crear nuevos grupos
            JPanel topPanel = new JPanel(new FlowLayout());
            JButton createButton = new JButton("Crear Grupo");
            //Al hacer clic en el se abre el formulario de creacion de eventos
            createButton.addActionListener(e -> showCreateGroupDialog());
            topPanel.add(createButton);
            frame.add(topPanel, BorderLayout.NORTH);
            //Se crea un panel central para listar visualmente los grupos
            groupListPanel = new JPanel();
            groupListPanel.setLayout(new BoxLayout(groupListPanel, BoxLayout.Y_AXIS));
            //Permite scrollear cuando hay muchos eventos
            JScrollPane scrollPane = new JScrollPane(groupListPanel);
            frame.add(scrollPane, BorderLayout.CENTER);
            //Actualiza la lista de grupos
            updateGroupList();
            //Muestra la ventana
            frame.setVisible(true);
        }
        //Metodo para mostrar la ventana emergente para creara un grupo
        private void showCreateGroupDialog() {
            //Se crea un diálogo con campos de texto para ingresar el nombre y descripción del grupo
            JDialog dialog = new JDialog((Frame) null, "Crear Grupo de Extensión", true);
            dialog.setSize(300, 200);
            dialog.setLayout(new GridLayout(3, 2));
            dialog.setLocationRelativeTo(null);
            //Boton para crear eventos
            JTextField nameField = new JTextField();
            JTextField descriptionField = new JTextField();
            JButton createButton = new JButton("Crear");

            dialog.add(new JLabel("Nombre:"));
            dialog.add(nameField);
            dialog.add(new JLabel("Descripción:"));
            dialog.add(descriptionField);
            dialog.add(createButton);

            createButton.addActionListener(e -> {
                String name = nameField.getText();
                String description = descriptionField.getText();
                //Si los campos estan llenos procede a 
                if (!name.isEmpty() && !description.isEmpty()) {
                    //Crear el grupo y agregarlo en la lista de GroupData
                    GroupData.addGroup(new ExtensionGroup(GroupData.getGroups().size() + 1, name, description));
                    //Actualiza la lista de grupos
                    updateGroupList();
                    dialog.dispose();
                } else {
                    //Si alguno de los campos esta vacio muestra esta ventana
                    JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios.");
                }
            });

            dialog.setVisible(true);
        }
        //Refresca la lista de grupos
        private void updateGroupList() {
            //Borra todos lo grupos mostrados en pantalla 
            groupListPanel.removeAll();
            //Busca en la lista de grupos todos los grupos de extension
            for (var group : GroupData.getGroups()) {
                //Si es un grupo de extension
                if (group instanceof ExtensionGroup) {
                    //Entonces lo agrega a la interfaz
                    JPanel groupPanel = new JPanel(new BorderLayout());
                    groupPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                    JLabel nameLabel = new JLabel("📌 " + group.getName());
                    JButton detailsButton = new JButton("🔍 Ver Detalles");

                    detailsButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                            "📢 Nombre: " + group.getName() + "\n📝 Descripción: " + group.getDescription(),
                            "Detalles del Grupo", JOptionPane.INFORMATION_MESSAGE));

                    groupPanel.add(nameLabel, BorderLayout.WEST);
                    groupPanel.add(detailsButton, BorderLayout.EAST);
                    groupListPanel.add(groupPanel);
                }
            }
            //Al final se revalida y repinta el panel para mostrar los cambios
            groupListPanel.revalidate();
            groupListPanel.repaint();
        }

        /*public static void main(String[] args) {
            SwingUtilities.invokeLater(ExtensionGroupView::new);
        }*/
    }
}
