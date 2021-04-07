import org.junit.Before;
import org.junit.Test;

public class TtasBarrierTest {
    private int numThreads = 10000;
    private long startTime;
    private long endTime;

    @Before
    public void measureTtasTime() {
        TtasBarrier ttasBarrier = new TtasBarrier(numThreads);
        startTime = System.nanoTime();
        for (int i = 0; i < numThreads; i++) {
            new Thread(() -> {
                foo();
                ttasBarrier.doSpin();
                bar();
            }).start();
        }
    }

    @Test
    public void printTime() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Ttas Barrier with " + numThreads +
                " threads: " + (endTime - startTime) / 1_000_000 + " ms");
    }

    public void foo() {
    }

    public void bar() {
        endTime = System.nanoTime();
    }
}