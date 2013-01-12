import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;


public abstract class Employee extends Thread {

	private ConcurrentLinkedQueue<Runnable> activeTaskQueue = new ConcurrentLinkedQueue<Runnable>();
	private Scheduler scheduler;
	private final Object newItemLock = new Object();
	private final Semaphore ensureEnqueueAndNotifySemaphore = new Semaphore(1);
	
	// Runnables
	
	// TODO: This isn't done- it's just an example
	final Runnable goToLunch = new Runnable() {
		@Override
		public void run() {
			try {
				Thread.sleep(600L);
			} catch (InterruptedException e) {
				
			}
		}
	};
	
	public Employee(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	
	public void enqueueTask(Runnable newActiveTask) {
		try {
			/* ensure this thread enqueues a new item, notifies
			 * the employee thread, and that the employee thread
			 * removes the new item prior to allowing other threads
			 * to enqueue additional items
			 */
			ensureEnqueueAndNotifySemaphore.acquire();
			
		} catch (InterruptedException e) {
			System.err.println("The scheduler thread has been unexpectedly interrupted.");
		}
		activeTaskQueue.add(newActiveTask);
		newItemLock.notify();
	}
	
	protected abstract void registerDaysEvents(Scheduler scheduler);

	/**
	 * TODO: Concurrency issues between checking queue and doing stuff
	 */
	public void run() {
		try {
			while (true) {	
				newItemLock.wait();
				
				activeTaskQueue.poll().run();
				
				/* allow other threads to enqueue new tasks once more */
				ensureEnqueueAndNotifySemaphore.release();	
				
			}	
		} catch (InterruptedException e) {
				// Day is over. Run will now terminate automatically
		}
	}
}
