import javax.swing.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;

public class LineaEnsamblaje extends Thread {
    private final Main mainFrame;
    private BlockingQueue<Componente> bufferTableros;
    private BlockingQueue<Componente> bufferClavos;
    private BlockingQueue<Componente> bufferGuias;
    private CyclicBarrier barrera;

    private StringBuilder ensamblajeResult = new StringBuilder();  // Nuevo

    public LineaEnsamblaje(BlockingQueue<Componente> bufferTableros, BlockingQueue<Componente> bufferClavos, BlockingQueue<Componente> bufferGuias, CyclicBarrier barrera, Main mainFrame) {
        this.bufferTableros = bufferTableros;
        this.bufferClavos = bufferClavos;
        this.bufferGuias = bufferGuias;
        this.barrera = barrera;
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {
        try {
            barrera.await();
            // Store the components
            Componente tableroActual = bufferTableros.take();
            ensamblajeResult.append("Recogiendo tablero: ").append(tableroActual.getNombre()).append("\n");

            Componente clavosActual = bufferClavos.take();
            ensamblajeResult.append("Recogiendo clavos: ").append(clavosActual.getNombre()).append("\n");

            Componente guiasActual = bufferGuias.take();
            ensamblajeResult.append("Recogiendo guias: ").append(guiasActual.getNombre()).append("\n");

            if (tableroActual != null && clavosActual != null && guiasActual != null) {
                ensamblajeResult.append("Maquina ensamblada\n");
            } else {
                ensamblajeResult.append("Error: faltan componentes para el ensamblaje.\n");
            }
            // Mostrar resultados del ensamblaje en el resultTextArea
            SwingUtilities.invokeLater(() -> {
                mainFrame.appendToResultTextArea(ensamblajeResult.toString());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}