import java.util.concurrent.ConcurrentLinkedQueue;


public abstract class Employee extends Thread {

	private ConcurrentLinkedQueue<Runnable> activeTaskQueue = new ConcurrentLinkedQueue<Runnable>();
	private Scheduler scheduler;
	// Runnables
	
	// TODO: This isn't done- it's just an example
	Runnable goToLunch = new Runnable() {
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
		activeTaskQueue.add(newActiveTask);
	}
	
	protected abstract void registerDaysEvents(Scheduler scheduler);

	/**
	 * TODO: Concurrency issues between checking queue and doing stuff
	 */
	public void run() {
		while (true) {
			try {
				wait();
				activeTaskQueue.poll().run();
			} catch (InterruptedException e) {
				// Day is over. Run will now terminate automatically
			}
		}
	}
}
