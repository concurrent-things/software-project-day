import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Scheduler extends ScheduledThreadPoolExecutor {
	private long startMillis;
	
	public Scheduler(int corePoolSize) {
		super(corePoolSize);
		this.startMillis = System.nanoTime();
	}
	
	
	public void registerEvent(final Runnable event, final Employee employee, long millisFromNow) {
		this.schedule(new Runnable(){

			@Override
			public void run() {
				employee.enqueueTask(event);
			}
			
		}, millisFromNow, TimeUnit.MILLISECONDS);
	}
	
	public long getStartTimeInMillis() {
		return startMillis;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
