package net.motodev.device;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yo on 24/06/2017.
 */
public class TestMain {


    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private int counter = 1;

    public static void main(String[] args) {
        TestMain t =  new TestMain();
        t.work();
        new Thread(() -> {
            while (true) {
                if (t.executor.isShutdown()) {
                    System.out.println("executor closed");
                    t.counter = 0;
                    t.executor = Executors.newSingleThreadScheduledExecutor();
                    t.work();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).run();
    }

    private void work() {
        executor.scheduleAtFixedRate(() -> {
            System.out.println("hello " + counter);
            counter++;
            if (counter % 5 == 0) {
                executor.shutdownNow();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }
}
