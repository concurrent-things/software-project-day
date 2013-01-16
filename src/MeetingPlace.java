import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public abstract class MeetingPlace {
	private final CyclicBarrier barrier;
	private final int numberOfAttendees;
	
	public MeetingPlace(int numberOfAttendees) {
		this.numberOfAttendees = numberOfAttendees;
		this.barrier = new CyclicBarrier(numberOfAttendees, new Runnable(){

			@Override
			public void run() {
				conductMeeting();
			}});
	}

	public abstract void conductMeeting();

	public void enterRoom(){
		onEnterRoom();
		try {
			barrier.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		onLeaveRoom();
	}

	public abstract void onLeaveRoom();
	
	public abstract void onEnterRoom();
}
