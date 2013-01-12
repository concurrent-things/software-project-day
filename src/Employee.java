import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;


public abstract class Employee extends Thread {

	private ConcurrentLinkedQueue<Runnable> activeTaskQueue = new ConcurrentLinkedQueue<Runnable>();
	private Scheduler scheduler;
	private final Object newItemLock = new Object();
	private final Semaphore binarySemaphore = new Semaphore(1);
	
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
			binarySemaphore.acquire();
		} catch (InterruptedException e) {
			System.err.println("The scheduler thread has been unexpectedly interrupted.");
		}
		
		synchronized (newItemLock) {
			activeTaskQueue.add(newActiveTask);
			binarySemaphore.release();
			newItemLock.notify();
		}
	}
	
	protected abstract void registerDaysEvents(Scheduler scheduler);

	public void run() {
		try {
			while (true) {	
				boolean mustRelease = false;
				while (!activeTaskQueue.isEmpty()) {
					activeTaskQueue.poll().run();
					
					binarySemaphore.acquire();
					if (activeTaskQueue.size() == 0) {
						mustRelease = true;
						break;
					}
					binarySemaphore.release();
				}
				
				synchronized (newItemLock) {
					if (mustRelease) binarySemaphore.release();
					newItemLock.wait();
				}
			}
		} catch (InterruptedException e) {
				// Day is over. Run will now terminate automatically
		}
	}
}
