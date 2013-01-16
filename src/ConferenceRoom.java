import java.util.concurrent.CyclicBarrier;


public class ConferenceRoom extends MeetingPlace {

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
	public void onLeaveRoom() {
		System.out.println("<insert thread name here> left conference room.");
		
	}

	@Override
	public void onEnterRoom() {
		System.out.println("<insert thread name here> entered conference room");
		
	}



}
