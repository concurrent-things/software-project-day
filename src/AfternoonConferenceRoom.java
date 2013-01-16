
public class AfternoonConferenceRoom extends MeetingPlace{

	public AfternoonConferenceRoom(int numberOfAttendees) {
		super(numberOfAttendees);
	}

	public void conductMeeting() {
		System.out.println("Afternoon status meeting has begun.");
		try {
			Thread.sleep(150L);
		} catch (InterruptedException e) {
			System.out.println("Exception when trying to conduct afternoon meeting.");
			e.printStackTrace();
		}
		
	}

	public void onLeaveRoom() {
		System.out.println(Thread.currentThread().getName() + " has left conference room.");
	}

	public void onEnterRoom() {
		System.out.println(Thread.currentThread().getName() + " has entered conference room.");
	}

}
