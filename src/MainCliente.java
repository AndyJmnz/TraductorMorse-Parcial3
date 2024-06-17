import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class MainCliente extends JFrame implements ActionListener {

    JLabel ingresarJLabel, resultado1JLabel, resultado2JLabel, resultado3JLabel, tiempo1JLabel, tiempo2JLabel, tiempo3JLabel;
    JTextArea texto_Ingresado, texto_ResultadoMergAreae, texto_ResultadoFork, texto_ResultadoExecutor;
    JTextField TiempoMergeField, TiempoForkField, TiempoExecutorField;
    JButton JbuttonMerge, JbuttonForkJoin, JbuttonExecutor, limpiarButton, combinarButton;
    private static final HashMap<Character, String> CodigoMorse = new HashMap<>();

    char[] combinedArray;
    InterfazTraductor generator = null;
    int puerto = 1099;
    String url = "//192.168.0.2:" + puerto + "/TraductorMorse";

    static {
        CodigoMorse.put('a', ".-");
        CodigoMorse.put('b', "-...");
        CodigoMorse.put('c', "-.-.");
        CodigoMorse.put('d', "-..");
        CodigoMorse.put('e', ".");
        CodigoMorse.put('f', "..-.");
        CodigoMorse.put('g', "--.");
        CodigoMorse.put('h', "....");
        CodigoMorse.put('i', "..");
        CodigoMorse.put('j', ".---");
        CodigoMorse.put('k', "-.-");
        CodigoMorse.put('l', ".-..");
        CodigoMorse.put('m', "--");
        CodigoMorse.put('n', "-.");
        CodigoMorse.put('o', "---");
        CodigoMorse.put('p', ".--.");
        CodigoMorse.put('q', "--.-");
        CodigoMorse.put('r', ".-.");
        CodigoMorse.put('s', "...");
        CodigoMorse.put('t', "-");
        CodigoMorse.put('u', "..-");
        CodigoMorse.put('v', "...-");
        CodigoMorse.put('w', ".--");
        CodigoMorse.put('x', "-..-");
        CodigoMorse.put('y', "-.--");
        CodigoMorse.put('z', "--..");

        CodigoMorse.put('0', "-----");
        CodigoMorse.put('1', ".----");
        CodigoMorse.put('2', "..---");
        CodigoMorse.put('3', "...--");
        CodigoMorse.put('4', "....-");
        CodigoMorse.put('5', ".....");
        CodigoMorse.put('6', "-....");
        CodigoMorse.put('7', "--...");
        CodigoMorse.put('8', "---..");
        CodigoMorse.put('9', "----.");

        CodigoMorse.put('.', ".-.-.-");
        CodigoMorse.put(',', "--..--");
        CodigoMorse.put('?', "..--..");
        CodigoMorse.put('!', "-.-.--");
        CodigoMorse.put('\'', ".----.");
        CodigoMorse.put('"', ".-..-.");
        CodigoMorse.put(':', "---...");
        CodigoMorse.put(';', "-.-.-.");
        CodigoMorse.put('(', "-.--.");
        CodigoMorse.put(')', "-.--.-");
        CodigoMorse.put('=', "-...-");
        CodigoMorse.put('+', ".-.-.");
        CodigoMorse.put('-', "-....-");
        CodigoMorse.put('_', "..--.-");
        CodigoMorse.put('$', "...-..-");
        CodigoMorse.put('@', ".--.-.");
        CodigoMorse.put(' ', " ");
    }

    public MainCliente() {
        setTitle("Traductor de Texto a Código Morse-CLIENTE");
        setSize(520, 630);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Colores de fondo y fuente personalizada
        getContentPane().setBackground(new Color(222, 255, 245));
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        ingresarJLabel = new JLabel("Texto a Traducir:");
        ingresarJLabel.setBounds(50, 40, 150, 30);
        ingresarJLabel.setFont(labelFont);
        add(ingresarJLabel);

        JScrollPane scrollPane1 = new JScrollPane();
        texto_Ingresado = new JTextArea();
        scrollPane1.setViewportView(texto_Ingresado);
        scrollPane1.setBounds(50, 70, 400, 50);
        add(scrollPane1);

        resultado1JLabel = new JLabel("Merge Sort");
        resultado1JLabel.setBounds(50, 140, 150, 25);
        resultado1JLabel.setFont(labelFont);
        add(resultado1JLabel);

        JScrollPane scrollPane12 = new JScrollPane();
        texto_ResultadoMergAreae = new JTextArea();
        texto_ResultadoMergAreae.setEditable(false);
        scrollPane12.setViewportView(texto_ResultadoMergAreae);
        scrollPane12.setBounds(50, 170, 400, 50);
        add(scrollPane12);

        resultado2JLabel = new JLabel("Fork Join");
        resultado2JLabel.setBounds(50, 230, 150, 25);
        resultado2JLabel.setFont(labelFont);
        add(resultado2JLabel);

        JScrollPane scrollPane13 = new JScrollPane();
        texto_ResultadoFork = new JTextArea();
        texto_ResultadoFork.setEditable(false);
        scrollPane13.setViewportView(texto_ResultadoFork);
        scrollPane13.setBounds(50, 260, 400, 50);
        add(scrollPane13);

        resultado3JLabel = new JLabel("Executor Service");
        resultado3JLabel.setBounds(50, 320, 150, 25);
        resultado3JLabel.setFont(labelFont);
        add(resultado3JLabel);

        JScrollPane scrollPane14 = new JScrollPane();
        texto_ResultadoExecutor = new JTextArea();
        texto_ResultadoExecutor.setEditable(false);
        scrollPane14.setViewportView(texto_ResultadoExecutor);
        scrollPane14.setBounds(50, 350, 400, 50);
        add(scrollPane14);

        JLabel tiempo1JLabel = new JLabel("T. Secuencial:");
        tiempo1JLabel.setBounds(50, 420, 150, 25);
        tiempo1JLabel.setFont(labelFont);
        add(tiempo1JLabel);

        TiempoMergeField = new JTextField();
        TiempoMergeField.setBounds(50, 445, 100, 25);
        TiempoMergeField.setEditable(false);
        add(TiempoMergeField);

        JLabel tiempo2JLabel = new JLabel("T. ForkJoin:");
        tiempo2JLabel.setBounds(200, 420, 150, 25);
        tiempo2JLabel.setFont(labelFont);
        add(tiempo2JLabel);

        TiempoForkField = new JTextField();
        TiempoForkField.setBounds(200, 445, 100, 25);
        TiempoForkField.setEditable(false);
        add(TiempoForkField);

        JLabel tiempo3JLabel = new JLabel("T. Executor S.:");
        tiempo3JLabel.setBounds(350, 420, 150, 25);
        tiempo3JLabel.setFont(labelFont);
        add(tiempo3JLabel);

        TiempoExecutorField = new JTextField();
        TiempoExecutorField.setBounds(350, 445, 100, 25);
        TiempoExecutorField.setEditable(false);
        add(TiempoExecutorField);

        combinarButton = new JButton("Enviar");
        combinarButton.setBounds(200, 530, 100, 25);
        combinarButton.addActionListener(this);
        add(combinarButton);

        limpiarButton = new JButton("Limpiar");
        limpiarButton.setBounds(350, 530, 100, 25);
        limpiarButton.addActionListener(this);
        add(limpiarButton);

        JbuttonMerge = new JButton("Secuencial");
        JbuttonMerge.setBounds(50, 480, 100, 25);
        JbuttonMerge.addActionListener(this);
        add(JbuttonMerge);

        JbuttonForkJoin = new JButton("ForkJoin");
        JbuttonForkJoin.setBounds(200, 480, 100, 25);
        JbuttonForkJoin.addActionListener(this);
        add(JbuttonForkJoin);

        JbuttonExecutor = new JButton("Execute");
        JbuttonExecutor.setBounds(350, 480, 100, 25);
        JbuttonExecutor.addActionListener(this);
        add(JbuttonExecutor);

        JbuttonMerge.setBackground(new Color(77, 144, 142));
        JbuttonMerge.setForeground(Color.WHITE);

        JbuttonForkJoin.setBackground(new Color(77, 144, 142));
        JbuttonForkJoin.setForeground(Color.WHITE);

        JbuttonExecutor.setBackground(new Color(77, 144, 142));
        JbuttonExecutor.setForeground(Color.WHITE);

        limpiarButton.setBackground(new Color(249, 65, 68));
        limpiarButton.setForeground(Color.WHITE);

        combinarButton.setBackground(new Color(249, 65, 68));
        combinarButton.setForeground(Color.WHITE);
    }

    public static void main(String[] args) {
        MainCliente cliente = new MainCliente();
        cliente.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == combinarButton) {
            try {
                generator = (InterfazTraductor) Naming.lookup(url);
                String inputText = texto_Ingresado.getText();
                generator.addArray(inputText.toCharArray());
                //actualizarTextos();
                JOptionPane.showMessageDialog(this, "Texto enviado y combinado recibido del servidor.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al enviar texto al servidor: " + ex.getMessage());
            }
        } else if (e.getSource() == limpiarButton) {
            texto_Ingresado.setText("");
            texto_ResultadoMergAreae.setText("");
            texto_ResultadoFork.setText("");
            texto_ResultadoExecutor.setText("");
            TiempoMergeField.setText("");
            TiempoForkField.setText("");
            TiempoExecutorField.setText("");
        } else if (e.getSource() == JbuttonMerge) {
            if (combinedArray != null) {
                handleMerge();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, combine texto primero.");
            }
        } else if (e.getSource() == JbuttonForkJoin) {
            if (combinedArray != null) {
                handleForkJoin();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, combine texto primero.");
            }
        } else if (e.getSource() == JbuttonExecutor) {
            if (combinedArray != null) {
                handleExecutor();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, combine texto primero.");
            }
        }
    }

    // Método para actualizar los textos de resultados en la interfaz gráfica
    /*private void actualizarTextos() {
        try {
            char[] combinedArray = generator.combineArrays();
            String morseResult = convertToMorse(combinedArray);
            texto_ResultadoMergAreae.setText(morseResult);
            texto_ResultadoFork.setText(morseResult);
            texto_ResultadoExecutor.setText(morseResult);
        } catch (RemoteException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener el texto combinado del servidor: " + ex.getMessage());
        }
    }*/

    private void handleExecutor() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        executorService.submit(() -> {
            long startTime = System.nanoTime();
            String morseResult = LogicaExecutorService.traducirTextoConExecutorService(combinedArray, CodigoMorse);
            long endTime = System.nanoTime();

            SwingUtilities.invokeLater(() -> {
                texto_ResultadoExecutor.setText(morseResult);
                double executionTimeInMillis = (endTime - startTime) / 1_000_000.0;
                TiempoExecutorField.setText(String.format("%.2f ms", executionTimeInMillis));
            });
        });

        executorService.shutdown();
    }

    private void handleMerge() {
        long startTime = System.nanoTime();

        // Realizar Merge Sort y traducción simultáneamente
        LogicaMergeSort logicaMergeSort = new LogicaMergeSort();
        logicaMergeSort.mergeSortAndTranslate(combinedArray, 0, combinedArray.length - 1, CodigoMorse);

        // Calcular tiempo de ejecución total
        long endTime = System.nanoTime();
        double executionTimeInMillis = (endTime - startTime) / 1_000_000.0;
        TiempoMergeField.setText(String.format("%.2f ms", executionTimeInMillis));

        // Mostrar el resultado traducido en la interfaz de usuario
        String morseResult = new String(combinedArray); // Suponiendo que el arreglo combinado ahora está en Morse
        texto_ResultadoMergAreae.setText(morseResult);
    }

    private void handleForkJoin() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // Iniciar el Fork/Join Task y traducción simultáneamente
        LogicaForkJoin mergeSortTask = new LogicaForkJoin(combinedArray, 0, combinedArray.length - 1, CodigoMorse);

        // Calcular tiempo de inicio
        long startTime = System.nanoTime();

        // Ejecutar el Fork/Join Task
        forkJoinPool.invoke(mergeSortTask);

        // Calcular tiempo de fin
        long endTime = System.nanoTime();
        double executionTimeInMillis = (endTime - startTime) / 1_000_000.0;
        TiempoForkField.setText(String.format("%.2f ms", executionTimeInMillis));

        // Mostrar el resultado traducido en la interfaz de usuario
        String morseResult = new String(combinedArray); // Suponiendo que el arreglo combinado ahora está en Morse
        texto_ResultadoFork.setText(morseResult);
    }

    private String convertToMorse(char[] charArray) {
        StringBuilder morseCode = new StringBuilder();
        for (char c : charArray) {
            if (CodigoMorse.containsKey(c)) {
                morseCode.append(CodigoMorse.get(c)).append(" ");
            } else {
                morseCode.append(" ");
            }
        }
        return morseCode.toString();
    }
}