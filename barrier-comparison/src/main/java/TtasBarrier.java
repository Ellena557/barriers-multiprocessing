public class TtasBarrier implements MyBarrier {
    private int threadNumber;
    private int counter;
    private TtasLock ttasLock;
    private final Object waitObj;


    public TtasBarrier(int threadNumber) {
        this.threadNumber = threadNumber;
        this.counter = 0;
        this.ttasLock = new TtasLock();
        this.waitObj = new Object();
    }

    public void doSpin() {
        ttasLock.lock();
        try {
            counter += 1;
        } finally {
            ttasLock.unlock();
        }

        // waiting
        synchronized (waitObj) {
            while (counter < threadNumber) {
                try {
                    waitObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            waitObj.notifyAll();
        }
    }
}
