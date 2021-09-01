package lebah.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestScheduledUpdater {
	
	public static void main(String[] args) throws InterruptedException {
		
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		service.scheduleAtFixedRate(new ScheduledUpdater(), 0, 1, TimeUnit.SECONDS);
		
        Thread.sleep(10000);
        service.shutdown();
	}

}
