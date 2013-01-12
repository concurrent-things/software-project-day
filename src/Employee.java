import java.util.concurrent.ConcurrentLinkedQueue;


public abstract class Employee extends Thread {

	private ConcurrentLinkedQueue<Runnable> activeTaskQueue = new ConcurrentLinkedQueue<Runnable>();
	private Scheduler scheduler;
	private Object lock = new Object();
	
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
		// lock.aquire
		synchronized (lock) {
			activeTaskQueue.add(newActiveTask);
			notify();
		}
	}
	
	protected abstract void registerDaysEvents(Scheduler scheduler);

	/**
	 * TODO: Concurrency issues between checking queue and doing stuff
	 */
	public void run() {
		try {
			while (true) {	
				wait();
				synchronized (lock) {
					activeTaskQueue.poll().run();
				}
				// lock.release
				
			}	
		} catch (InterruptedException e) {
				// Day is over. Run will now terminate automatically
		}
	}
}
