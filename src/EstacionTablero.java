import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;

public class EstacionTablero extends EstacionProduccion {

    public EstacionTablero(BlockingQueue<Componente> buffer, CyclicBarrier barrera) {
        super(buffer, barrera);
    }

    @Override
    public void run() {
        try {
            buffer.put(new Tablero());
            barrera.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

