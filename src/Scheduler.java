import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Scheduler extends ScheduledThreadPoolExecutor {
	private long startNanos;
	
	public Scheduler(int corePoolSize) {
		super(corePoolSize);
		this.startNanos = System.nanoTime();
	}
	
	
	public void registerEvent(final Runnable event, final Employee employee, long nanosFromNow) {
		this.schedule(new Runnable(){

			@Override
			public void run() {
				employee.enqueueTask(event);
			}
			
		}, nanosFromNow, TimeUnit.NANOSECONDS);
	}
	
	public long getStartTimeInNanos() {
		return startNanos;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
