import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class MainServer extends JFrame {
    JButton limpiarListaButton;
    ImplementacionTraductor generator;
    JTextArea textArea;

    public MainServer() {
        setTitle("Traductor Server");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        getContentPane().setBackground(new Color(244, 132, 0));
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // Botón para limpiar lista
        limpiarListaButton = new JButton("Limpiar Lista");
        limpiarListaButton.setBounds(100, 200, 150, 25);
        limpiarListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generator.clearData();
                    System.out.println("Lista limpiada.");
                    actualizarTextArea(); // Actualiza el contenido del JTextArea
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(limpiarListaButton);

        limpiarListaButton.setBackground(new Color(0, 166, 75));
        limpiarListaButton.setForeground(Color.WHITE);

        // JTextArea para mostrar texto combinado
        textArea = new JTextArea();
        textArea.setEditable(false); // Para que no sea editable por el usuario
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 50, 300, 120);
        add(scrollPane);

        try {
            // Crea el registro RMI en el puerto 1099
            LocateRegistry.createRegistry(1099);

            // Crea la implementación del traductor
            generator = new ImplementacionTraductor(2); // Cambia 2 por el número total de clientes esperado

            // Registra el objeto remoto con el nombre especificado
            Naming.rebind("//192.168.0.2/TraductorMorse", generator);

            System.out.println("Servidor RMI está listo.");
        } catch (Exception ex) {
            System.err.println("Error al iniciar el servidor RMI: " + ex.getMessage());
            ex.printStackTrace();
        }

        setVisible(true);
    }

    // Método para actualizar el contenido del JTextArea con el texto combinado
    private void actualizarTextArea() {
        try {
            char[] combinedData = generator.combineArrays();
            String combinedText = new String(combinedData);
            textArea.setText(combinedText);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainServer();
    }
}
