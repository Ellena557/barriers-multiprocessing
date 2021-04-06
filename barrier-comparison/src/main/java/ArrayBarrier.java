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
            }
            locks[myIndex] = 2;
        }

        // first thread
        if (myIndex == 0) {
            locks[myIndex] = 1;
            while (locks[myIndex + 1] != 2) {
                // wait
            }
        }

        // middle threads
        if (myIndex > 0 && myIndex < threadNumber - 1) {
            while (locks[myIndex - 1] != 1) {
                // wait
            }
            
            locks[myIndex] = 1;

            while (locks[myIndex + 1] != 2) {
                // wait
            }

            locks[myIndex] = 2;
        }
    }
}
