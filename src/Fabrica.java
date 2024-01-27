import java.util.concurrent.*;

public class Fabrica {
    public static void main(String[] args) {
        CyclicBarrier barrera = new CyclicBarrier(4); // 3 estaciones de trabajo + 1 línea de ensamblaje

        BlockingQueue<Componente> bufferTableros = new LinkedBlockingDeque<>();
        BlockingQueue<Componente> bufferClavos = new LinkedBlockingDeque<>();
        BlockingQueue<Componente> bufferGuias = new LinkedBlockingDeque<>();

        EstacionProduccion estacionTablero = new EstacionTablero(bufferTableros, barrera);
        EstacionProduccion estacionClavo = new EstacionClavos(bufferClavos, barrera);
        EstacionProduccion estacionGuias = new EstacionGuias (bufferGuias, barrera);

        Main mainInstance = new Main();

        LineaEnsamblaje ensamblaje = new LineaEnsamblaje(bufferTableros, bufferClavos, bufferGuias, barrera,  mainInstance);

        estacionTablero.start();
        estacionClavo.start();
        estacionGuias.start();
        ensamblaje.start();
    }
}