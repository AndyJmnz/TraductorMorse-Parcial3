import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class MainServer extends JFrame {
    private JButton limpiarListaButton;
    private ImplementacionTraductor generator;
    private JTextArea textArea;
    private JButton mostrarCombinedButton;

    public MainServer() {
        setTitle("Traductor Server");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        getContentPane().setBackground(new Color(244, 132, 0));
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // Botón para limpiar la lista
        limpiarListaButton = new JButton("Limpiar Lista");
        limpiarListaButton.setBounds(225, 260, 125, 25);
        limpiarListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generator.clearData();
                    System.out.println("Lista limpiada.");
                    actualizarTextArea();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        limpiarListaButton.setBackground(new Color(0, 166, 75));
        limpiarListaButton.setForeground(Color.WHITE);
        add(limpiarListaButton);

        // JTextArea para mostrar el combinedArray
        textArea = new JTextArea();
        textArea.setBounds(50, 50, 300, 200);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 50, 300, 200);
        add(scrollPane);

        // Botón para mostrar el combinedArray
        mostrarCombinedButton = new JButton("Mostrar");
        mostrarCombinedButton.setBounds(50, 260, 125, 25);
        mostrarCombinedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCombined();
            }
        });
        add(mostrarCombinedButton);

        mostrarCombinedButton.setBackground(new Color(0, 166, 75));
        mostrarCombinedButton.setForeground(Color.WHITE);

        try {
            // Crear registro RMI en el puerto 1099
            LocateRegistry.createRegistry(1099);

            // Instanciar la implementación del traductor
            generator = new ImplementacionTraductor(2);

            // Enlazar el objeto remoto al registro RMI
            Naming.rebind("//localhost/TraductorMorse", generator);

            System.out.println("Servidor RMI está listo.");
        } catch (Exception ex) {
            System.err.println("Error al iniciar el servidor RMI: " + ex.getMessage());
            ex.printStackTrace();
        }

        setVisible(true);
    }

    // Actualizar el contenido del JTextArea con el combinedArray
    private void actualizarTextArea() {
        try {
            char[] combinedData = generator.combineArrays();
            String combinedText = new String(combinedData);
            textArea.setText(combinedText);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    // Mostrar el contenido del combinedArray
    private void mostrarCombined() {
        try {
            char[] combinedData = generator.combineArrays();
            String combinedText = new String(combinedData);
            JOptionPane.showMessageDialog(this, combinedText, "Combined Array", JOptionPane.INFORMATION_MESSAGE);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainServer();
    }
}
