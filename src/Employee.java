import java.util.concurrent.ConcurrentLinkedQueue;


public abstract class Employee extends Thread {

	private ConcurrentLinkedQueue<Runnable> activeTaskQueue = new ConcurrentLinkedQueue<Runnable>();
	
	public void enqueueTask(Runnable newActiveTask) {
		activeTaskQueue.add(newActiveTask);
	}
	
	protected abstract void registerDaysEvents();

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
