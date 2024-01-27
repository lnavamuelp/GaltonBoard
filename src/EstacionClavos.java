import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;

public class EstacionClavos extends EstacionProduccion {

    public EstacionClavos(BlockingQueue<Componente> buffer, CyclicBarrier barrera) {
        super(buffer, barrera);
    }

    @Override
    public void run() {
        try {
            buffer.put(new Clavo());
            barrera.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

