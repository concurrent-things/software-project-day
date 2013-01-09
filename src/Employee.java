import java.util.concurrent.ConcurrentLinkedQueue;


public abstract class Employee extends Thread {

	private ConcurrentLinkedQueue<Runnable> activeTaskQueue = new ConcurrentLinkedQueue<Runnable>();
	
	public void enqueueTask(Runnable newActiveTask) {
		activeTaskQueue.add(newActiveTask);
	}
	
	public abstract void registerDaysEvents();


}
