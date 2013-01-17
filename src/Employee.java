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
	private final Object advancingLock = new Object();
	private boolean isBlocking = false;
	
	// Runnables
	
	final protected Runnable askQuestion = new Runnable() {
		@Override
		public void run() {
			if (supervisor == null) return;
			//Employee.this.lockProcessing();
			
			if (supervisor.registerSpontaneousTask(new Runnable() {

				@Override
				public void run() {
					Stack<Employee> callStack = new Stack<Employee>();
					callStack.add(Employee.this);
					supervisor.askQuestion(callStack);
				}
				
			})) {
				Employee.this.onQuestionAsked(Employee.this.supervisor);
			} else {
				Employee.this.onQuestionCancelled(Employee.this.supervisor);
				//unlockProcessing();
			}
			synchronized (advancingLock) { try {
				advancingLock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} }
		}
	};
	
	protected abstract void onQuestionCancelled(Employee notAvailable);
	
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

	}
	
	protected abstract void initRunnables();
	
	protected boolean canAnswerQuestion() {
		return new Random().nextBoolean();
	}
	
	final public void askQuestion(final Stack<Employee> relayedFrom) {
		Employee.this.onQuestionAsked(supervisor);

		if (supervisor == null || canAnswerQuestion()) {
			relayedFrom.peek().resumeWithAnswer(new Runnable() {  //TODO: double check for bugs

				@Override
				public void run() {
					relayedFrom.pop().listenToAnswer(relayedFrom);
				}
			});
			return;
		}
		
		
//		Employee.this.lockProcessing();
		
		supervisor.registerSpontaneousTask(new Runnable() {

			@Override
			public void run() {
				relayedFrom.push(Employee.this);
				supervisor.askQuestion(relayedFrom);
			}
		});
		
		Employee.this.onQuestionAsked(supervisor);

		
		synchronized (advancingLock) { try {
			advancingLock.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }
		
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
	
	final public boolean enqueueTask(Runnable newActiveTask, boolean lastTask) {
		// TODO: check to see if we can enqueue		
		
		try {
			binarySemaphore.acquire();
		} catch (InterruptedException e) {
			System.err.println("The scheduler thread has been unexpectedly interrupted.");
			return false;
		}

		synchronized (newItemLock) {
			try {
				activeTaskQueue.offer(newActiveTask);
				binarySemaphore.release();
				newItemLock.notify();
			} catch (Throwable t) {
				System.err.println("Someone attempted to schedule a null task.");
				return false;
			}
		}
		return true;
	}
		
	protected abstract void onQuestionAsked(Employee askedTo);
	protected abstract void onAnswerReceived(Employee receivedFrom);
	
	final public boolean registerSpontaneousTask(Runnable r) {
		return scheduler.registerEvent(r, this, 10, false);
	}
	
	final public void resumeWithAnswer(Runnable answer) {
		try {
			binarySemaphore.acquire();
		} catch (Exception e) {
			System.err.println("The scheduler thread has been unexpectedly interrupted.");
			return;
		}
		
		ConcurrentLinkedQueue<Runnable> queueBackup = new ConcurrentLinkedQueue<Runnable>();
		
		while (!activeTaskQueue.isEmpty()) {
			queueBackup.offer(activeTaskQueue.poll());
		}
		
		enqueueTask(answer, false);
		
		while (!queueBackup.isEmpty()) {
			enqueueTask(queueBackup.poll(), false);
		}
		synchronized (this.advancingLock) {
			this.advancingLock.notify();
		}
	}
	
	protected abstract void registerDaysEvents(Scheduler scheduler);

	final public void run() {
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
			System.out.println("TEST: Thread interrupted successfully.");
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
	
	public Employee getSupervisor() {
		return this.supervisor;
	}
}
