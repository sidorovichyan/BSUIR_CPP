package by.epam.kpp.count;

import java.util.concurrent.atomic.AtomicInteger;

public class CountAtomic {
    private static final AtomicInteger count = new AtomicInteger(0);            // счетчик подключений

    public void  increment() {
        count.getAndIncrement();
    }

    public static int value() {
        return count.get();
    }
}
