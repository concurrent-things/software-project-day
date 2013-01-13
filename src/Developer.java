

public class Developer extends Employee{
	private int teamNumber;
	private int teamMemberNumber;
	
	public Developer(Scheduler scheduler, TeamLeader teamLeader, int teamNumber, int teamMemberNumber) { 
		super(scheduler, teamLeader);
		this.teamNumber = teamNumber; 
		this.teamMemberNumber = teamMemberNumber; 
	}
	
	public void goToLunch() { 
		 	
	}
	
	public void provideStatusUpdated() { 
		
	}

	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		// TODO register the day's events
		
	}

	@Override
	protected void onQuestionAsked(Employee askedTo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onAnswerReceived(Employee receivedFrom) {
		// TODO Auto-generated method stub
		
	}
}