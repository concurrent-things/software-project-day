import java.util.concurrent.ConcurrentLinkedQueue;


public abstract class Employee {

	private ConcurrentLinkedQueue<Runnable> activeTaskQueue = new ConcurrentLinkedQueue<Runnable>();
	
	public void enqueueTask(Runnable newActiveTask) {
		activeTaskQueue.add(newActiveTask);
	}


}
