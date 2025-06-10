package take_nft.ru.takeNft.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class DuelTimeoutService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit unit) {
        return scheduler.schedule(task, delay, unit);
    }

    public void cancel(ScheduledFuture<?> task) {
        if (task != null && !task.isDone()) {
            task.cancel(true);
        }
    }
}
