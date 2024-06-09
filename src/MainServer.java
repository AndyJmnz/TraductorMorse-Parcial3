import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class MainServer extends JFrame implements ActionListener {
    JButton limpiarListaButton;
    ImplementacionTraductor generator;

    public MainServer() {
        setTitle("Traductor Server");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        getContentPane().setBackground(new Color(244, 132, 0));
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        limpiarListaButton = new JButton("Limpiar Lista");
        limpiarListaButton.setBounds(70, 70, 150, 25);
        limpiarListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generator.clearCombinedArrays();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(limpiarListaButton);

        limpiarListaButton.setBackground(new Color(0, 166, 75));
        limpiarListaButton.setForeground(Color.WHITE);

        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);

            generator = new ImplementacionTraductor(2);

            Naming.rebind("//192.168.0.28/TraductorMorse", generator);

            System.out.println("Servidor RMI está listo.");
        } catch (Exception ex) {
            System.err.println("Error al iniciar el servidor RMI: " + ex.getMessage());
            ex.printStackTrace();
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainServer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}