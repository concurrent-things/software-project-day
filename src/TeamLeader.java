

public class TeamLeader extends Employee{
	
	public TeamLeader(Scheduler scheduler, Manager manager, int teamNumber, int teamMemberNumber) {
		super(scheduler, manager);
	}

	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		
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
