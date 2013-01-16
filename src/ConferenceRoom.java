import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;


public class ConferenceRoom extends MeetingPlace {
	private ReentrantLock roomKey = new ReentrantLock(true);
	private Object nextGroup = new Object();
	private Thread owner = null;
	
	public ConferenceRoom(int numberOfAttendees) {
		super(numberOfAttendees);
	}

	@Override
	public void conductMeeting() {
		System.out.println("The conference meeting has started.");
		try {
			Thread.sleep(400L); //TODO: correct sleeping time
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void enterRoom() {
		boolean enteredMeeting = false;
		
		while (!enteredMeeting) {
			if (Thread.currentThread() instanceof TeamLeader) {
				roomKey.lock();
				owner = Thread.currentThread();
				nextGroup.notifyAll();
				super.enterRoom();
				nextGroup.notifyAll();
				roomKey.unlock();
			} else if (Thread.currentThread() instanceof Developer) {
				if (((Developer) Thread.currentThread()).getSupervisor() == owner) {
					super.enterRoom();
				} else {
					nextGroup.wait();
				}
			} else {	// not supposed to be in conf room
				System.err.println("Perhaps a manager is in the conf room by accident.");
				return;
			}
		}
	}

	@Override
	public void onLeaveRoom() {
		System.out.println("<insert thread name here> left conference room.");
		
	}

	@Override
	public void onEnterRoom() {
		System.out.println("<insert thread name here> entered conference room");
		
	}



}
