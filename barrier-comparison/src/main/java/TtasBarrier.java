public class TtasBarrier implements MyBarrier {
    private int threadNumber;
    private volatile int counter;
    private TtasLock ttasLock;

    public TtasBarrier(int threadNumber) {
        this.threadNumber = threadNumber;
        this.counter = 0;
        this.ttasLock = new TtasLock();
    }

    public void doSpin() {
        ttasLock.lock();
        try {
            counter += 1;
        } finally {
            ttasLock.unlock();
        }

        while(counter < threadNumber) {
            // wait
        }
    }
}
