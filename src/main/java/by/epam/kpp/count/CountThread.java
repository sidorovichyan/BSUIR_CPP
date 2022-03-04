package by.epam.kpp.count;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountThread {
    ExecutorService service = Executors.newCachedThreadPool();          // пул соединений
    CountAtomic countAtomic = new CountAtomic();
    public  void startThread()
    {
        service.submit(new Runnable() {
            @Override
            public void run() {
            countAtomic.increment();

            }
        });
    }
}
