import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 *
 * @author Kevin Hartman <kfh6034@rit.edu>
 */
public class Scheduler extends ScheduledThreadPoolExecutor {
	private long startNanos;
	
	public Scheduler(int corePoolSize) {
		super(corePoolSize);
		this.startNanos = System.nanoTime();
	}
	
	
	public boolean registerEvent(final Runnable event, 
			final Employee employee, long nanosFromNow, final boolean lastTask) {
		try {
			return this.schedule(new Callable<Boolean>(){

				@Override
				public Boolean call() {
					return employee.enqueueTask(event, lastTask);
				}
				
			}, nanosFromNow, TimeUnit.NANOSECONDS).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
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
