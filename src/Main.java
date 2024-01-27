import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

public class Main extends JFrame {
    private final JButton startButton;
    private final JButton displayButton;
    private final JTextField textFieldSlots;
    private final JTextField textFieldBalls;
    private final JPanel mainPanel;
    private final JTextArea resultTextArea;


    public Main() {
        mainPanel = new JPanel(new BorderLayout());
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));

         textFieldSlots = createTextField("Número de clavos / topes", textPanel);
         textFieldBalls = createTextField("Número de bolas", textPanel);

        displayButton = new JButton("Mostrar tablero de Galton con los resultados");
        textPanel.add(displayButton);

        startButton = new JButton("Empezar Simulación Fabricación de Tableros");
        textPanel.add(startButton);

        resultTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        mainPanel.add(textPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setupStartButton();
        setupDisplayButton();

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private JTextField createTextField(String labelText, JPanel textPanel) {
        textPanel.add(new JLabel(labelText));
        JTextField textField = new JTextField(10);
        textPanel.add(textField);
        return textField;
    }

    public void simulateBoard(int topes, int bolas) {
        int num_slots = topes;
        int num_Balls = bolas;
        int[] slots = new int[num_slots];
        StringBuilder resultText = new StringBuilder();

        for (int i = 0; i < num_Balls; i++) {
            int position = 0;
            for (int j = 0; j < num_slots - 1; j++) {
                if (Math.random() < 0.5) {
                    resultText.append("L");
                } else {
                    resultText.append("R");
                    position++;
                }
            }
            resultText.append("\n");
            slots[position]++;
        }
        resultText.append("Resultados:\n");
        for (int i = 0; i < num_slots; i++) {
            resultText.append("Slot ").append(i).append(": ").append(slots[i]).append("\n");
        }

        resultTextArea.setText(resultText.toString());
    }

    private void setupStartButton() {
        startButton.addActionListener(e -> {

            startButton.setEnabled(false);
            displayButton.setEnabled(false);
            Thread assemblyLineThread = new Thread() {
                @Override
                public void run() {
                    // Inicializar las colas bloqueantes y la barrera
                    BlockingQueue<Componente> bufferTableros = new LinkedBlockingQueue<>();
                    BlockingQueue<Componente> bufferClavos = new LinkedBlockingQueue<>();
                    BlockingQueue<Componente> bufferGuias = new LinkedBlockingQueue<>();
                    CyclicBarrier barrera = new CyclicBarrier(3); // suponiendo que hay 3 hilos que deben llegar al punto de barrera

                    // Inicializar las estaciones
                    EstacionClavos estacionClavos = new EstacionClavos(bufferClavos, barrera);
                    EstacionTablero estacionTablero = new EstacionTablero(bufferTableros, barrera);
                    EstacionGuias estacionGuias = new EstacionGuias(bufferGuias, barrera);

                    // Iniciar las estaciones
                    estacionClavos.start();
                    estacionTablero.start();
                    estacionGuias.start();

                    // Inicializar e iniciar la linea de ensamblaje
                    LineaEnsamblaje lineaEnsamblaje = new LineaEnsamblaje(bufferTableros, bufferClavos, bufferGuias, barrera,Main.this);
                    lineaEnsamblaje.start();

                    resultTextArea.append("Resultados de la fabricación:\n");
                    // Agrega el código necesario para mostrar los resultados de la fabricación.

                    SwingUtilities.invokeLater(() -> {
                        startButton.setEnabled(true);
                        displayButton.setEnabled(true);
                    });
                }
            };
            assemblyLineThread.start();
        });
    }

    private void setupDisplayButton() {
        displayButton.addActionListener(e -> {

            int topes = Integer.parseInt(textFieldSlots.getText());
            int bolas = Integer.parseInt(textFieldBalls.getText());

            startButton.setEnabled(false);
            displayButton.setEnabled(false);

            simulateBoard(topes, bolas);

            SwingUtilities.invokeLater(() -> {
                startButton.setEnabled(true);
                displayButton.setEnabled(true);
            });
        });
    }

    public static void main(String[] args) {
        // Initialize GUI on the EDT
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setTitle("The Galton board");
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setAutoRequestFocus(true);
        });
    }
    public void appendToResultTextArea(String text) {
        resultTextArea.append(text);
    }
}
