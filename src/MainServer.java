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

        limpiarListaButton = new JButton("Limpiar Lista");
        limpiarListaButton.setBounds(100, 200, 150, 25);
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
        add(limpiarListaButton);

        limpiarListaButton.setBackground(new Color(0, 166, 75));
        limpiarListaButton.setForeground(Color.WHITE);



        try {
            LocateRegistry.createRegistry(1099);

            generator = new ImplementacionTraductor(2);

            Naming.rebind("//192.168.0.2/TraductorMorse", generator);

            System.out.println("Servidor RMI está listo.");
        } catch (Exception ex) {
            System.err.println("Error al iniciar el servidor RMI: " + ex.getMessage());
            ex.printStackTrace();
        }

        setVisible(true);
    }

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
