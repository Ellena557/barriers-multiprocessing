import org.junit.Test;

public class Comparator {
    @Test
    public void iterateForTtas() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            TtasBarrierTest test = new TtasBarrierTest();
            test.measureTtasTime();
            test.printTime();
        }
    }

    @Test
    public void iterateForArray() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            ArrayBarrierTest test = new ArrayBarrierTest();
            test.measureArrayTime();
            test.printTime();
        }
    }
}
