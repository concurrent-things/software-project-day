import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/**
 * 
 *
 * @author Kevin Hartman <kfh6034@rit.edu>
 */
public abstract class Employee extends Thread {
	protected final Employee supervisor;
	private ConcurrentLinkedQueue<Runnable> activeTaskQueue = new ConcurrentLinkedQueue<Runnable>();
	private Scheduler scheduler;
	private final Object newItemLock = new Object();
	private final Semaphore binarySemaphore = new Semaphore(1);
	private final Semaphore blockProcessing = new Semaphore(1);
	private boolean isBlocking = false;
	
	// Runnables
	
	final protected Runnable askQuestion = new Runnable() {
		@Override
		public void run() {
			if (supervisor == null) return;
			Employee.this.lockProcessing();
			supervisor.registerSpontaneousTask(new Runnable() {

				@Override
				public void run() {
					Stack<Employee> callStack = new Stack<Employee>();
					callStack.add(Employee.this);
					supervisor.askQuestion(callStack);
				}
				
			});
			Employee.this.onQuestionAsked(Employee.this.supervisor);
		}
	};
	
	// TODO: This isn't done- it's just an example
	final protected Runnable goToLunch = new Runnable() {
		@Override
		public void run() {
			try {
				Random rand = new Random();
				Thread.sleep((long)(300+300*rand.nextDouble()));
			} catch (InterruptedException e) {
				
			}
		}
	};
	
	public Employee(Scheduler scheduler, Employee supervisor) {
		this.scheduler = scheduler;
		this.supervisor = supervisor;
		registerDaysEvents(scheduler);
	}
	
	protected boolean canAnswerQuestion() {
		return new Random().nextBoolean();
	}
	
	final public void askQuestion(final Stack<Employee> relayedFrom) {
		if (supervisor == null || canAnswerQuestion()) {
			relayedFrom.peek().resumeWithAnswer(new Runnable() {

				@Override
				public void run() {
					relayedFrom.pop().listenToAnswer(relayedFrom);
				}
			});
			return;
		}
		
		
		Employee.this.lockProcessing();
		
		supervisor.registerSpontaneousTask(new Runnable() {

			@Override
			public void run() {
				relayedFrom.push(Employee.this);
				supervisor.askQuestion(relayedFrom);
			}
		});
	}
	
	final public void listenToAnswer(final Stack<Employee> relayTo) {
		onAnswerReceived(this.supervisor);
		
		if (relayTo.peek() == null) return;
		
		relayTo.peek().resumeWithAnswer(new Runnable() {

			@Override
			public void run() {
				relayTo.pop().listenToAnswer(relayTo);				
			}
			
		});
	}
	
	final public void enqueueTask(Runnable newActiveTask) {
		try {
			binarySemaphore.acquire();
		} catch (InterruptedException e) {
			System.err.println("The scheduler thread has been unexpectedly interrupted.");
			return;
		}
		
		synchronized (newItemLock) {
			activeTaskQueue.add(newActiveTask);
			binarySemaphore.release();
			newItemLock.notify();
		}
	}
		
	protected abstract void onQuestionAsked(Employee askedTo);
	protected abstract void onAnswerReceived(Employee receivedFrom);
	
	final public void registerSpontaneousTask(Runnable r) {
		scheduler.registerEvent(r, this, 10);
	}
	
	final public void resumeWithAnswer(Runnable answer) {
		try {
			binarySemaphore.acquire();
		} catch (InterruptedException e) {
			System.err.println("The scheduler thread has been unexpectedly interrupted.");
			return;
		}
		
		ConcurrentLinkedQueue<Runnable> queueBackup = new ConcurrentLinkedQueue<Runnable>();
		
		while (!activeTaskQueue.isEmpty()) {
			queueBackup.offer(activeTaskQueue.poll());
		}
		
		activeTaskQueue.offer(answer);
		
		while (!queueBackup.isEmpty()) {
			activeTaskQueue.offer(queueBackup.poll());
		}
		
		unlockProcessing();
	}
	
	protected abstract void registerDaysEvents(Scheduler scheduler);

	final public void run() {
		try {
			while (true) {	
				boolean mustRelease = false;
				while (!activeTaskQueue.isEmpty()) {
					blockProcessing.acquire(); // try catch finally
					activeTaskQueue.poll().run();
					blockProcessing.release();
					
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
	
	final private void lockProcessing() {
		if (isBlocking) return;
		
		try {
			blockProcessing.acquire();
			isBlocking = true;
		} catch (InterruptedException e) {
			System.err.println("Could not block employee processing.");
		}
	}
	
	final private void unlockProcessing() {
		if (!isBlocking) return;
		blockProcessing.release();
	}
}
