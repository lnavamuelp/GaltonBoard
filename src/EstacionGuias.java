import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;

public class EstacionGuias extends EstacionProduccion {

    public EstacionGuias(BlockingQueue<Componente> buffer, CyclicBarrier barrera) {
        super(buffer, barrera);
    }

    @Override
    public void run() {
        try {
            buffer.put(new Guia());
            barrera.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

