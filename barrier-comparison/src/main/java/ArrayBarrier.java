import java.util.concurrent.atomic.AtomicInteger;

public class ArrayBarrier implements MyBarrier {
    private int threadNumber;
    private int[] locks;
    private AtomicInteger curIndex;

    public ArrayBarrier(int threadNumber) {
        this.threadNumber = threadNumber;
        this.locks = new int[threadNumber];
        this.curIndex = new AtomicInteger(0);
    }


    public void doSpin() {
        int myIndex = curIndex.getAndIncrement();

        // last thread
        if (myIndex == threadNumber - 1) {
            while (locks[myIndex - 1] != 1) {
                // wait
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            locks[myIndex] = 2;
            synchronized (this) {
                this.notifyAll();
            }
        }

        // first thread
        if (myIndex == 0) {
            locks[myIndex] = 1;
            while (locks[myIndex + 1] != 2) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // middle threads
        if (myIndex > 0 && myIndex < threadNumber - 1) {
            while (locks[myIndex - 1] != 1) {
                // wait
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            locks[myIndex] = 1;

            synchronized (this) {
                this.notifyAll();
            }

            while (locks[myIndex + 1] != 2) {
                // wait
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            locks[myIndex] = 2;
            synchronized (this) {
                this.notifyAll();
            }
        }
    }
}
