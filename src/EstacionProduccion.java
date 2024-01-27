import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;

public abstract class EstacionProduccion extends Thread {
    protected BlockingQueue<Componente> buffer;
    protected CyclicBarrier barrera;

    public EstacionProduccion(BlockingQueue<Componente> buffer, CyclicBarrier barrera) {
        this.buffer = buffer;
        this.barrera = barrera;
    }

    public abstract void run();
}