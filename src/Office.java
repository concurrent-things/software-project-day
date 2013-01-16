
public class Office extends MeetingPlace {
	private final Manager manager;
	
	public Office(int numberOfAttendees, Manager manager) {
		super(numberOfAttendees);
		this.manager = manager;
	}

	@Override
	public void conductMeeting() {
		System.out.println("Manager " + manager + "'s office meeting has begun."); //TODO: manager needs name
		try {
			Thread.sleep(400L); //TODO: correct sleeping time
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onLeaveRoom() {
		System.out.println("<insert thread name here> left " + manager + "'s office.");
		
	}

	@Override
	public void onEnterRoom() {
		System.out.println("<insert thread name here> entered " + manager + "'s office.");
		
	}

}
