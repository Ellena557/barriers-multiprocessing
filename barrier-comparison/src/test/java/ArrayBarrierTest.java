import org.junit.Before;
import org.junit.Test;

public class ArrayBarrierTest {
    private int numThreads = 10000;
    private long startTime;
    private long endTime;

    @Before
    public void measureArrayTime() {
        ArrayBarrier arrayBarrier = new ArrayBarrier(numThreads);
        startTime = System.nanoTime();
        for (int i = 0; i < numThreads; i++) {
            new Thread(() -> {
                foo();
                arrayBarrier.doSpin();
                bar();
            }).start();
        }
    }

    @Test
    public void printTime() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Array Barrier with " + numThreads +
                " threads: " + (endTime - startTime) / 1_000_000 + " ms");
    }

    public void foo() {
        //System.out.println("foo");
    }

    public void bar() {
        //System.out.println("bar");
        endTime = System.nanoTime();
    }
}