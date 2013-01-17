
public class Office extends MeetingPlace {
	private final Manager manager;
	
	public Office(int numberOfAttendees, Manager manager) {
		super(numberOfAttendees);
		this.manager = manager;
	}

	@Override
	public void conductMeeting() {
		System.out.println("Manager's office meeting has begun.");
		try {
			Thread.sleep(150L);
		} catch (InterruptedException e) {
			
		}
	}

	@Override
	public void onLeaveRoom() {
		System.out.println(Thread.currentThread().getName()+" left the Manager's office.");
		
	}

	@Override
	public void onEnterRoom() {
		System.out.println(Thread.currentThread().getName()+" entered the Manager's office.");
		
	}

}
