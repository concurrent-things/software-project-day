

public class TeamLeader extends Employee{
	
	public TeamLeader(Scheduler scheduler, Manager manager, int teamNumber, int teamMemberNumber) {
		super(scheduler, manager);
		this.setName("" + teamNumber + teamMemberNumber);
		initRunnables();
		registerDaysEvents(scheduler);
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

	@Override
	protected void initRunnables() {
		// TODO Auto-generated method stub
		
	}	
}
