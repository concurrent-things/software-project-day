
public class TeamLeader extends Employee{
	private Manager manager;
	
	public TeamLeader(Scheduler scheduler, Manager manager, int teamNumber, int teamMemberNumber) {
		super(scheduler);
		this.manager = manager;
	}

	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listenToAnswer(Employee relayTo) {
		relayTo.listenToAnswer(null);
		
	}

	@Override
	public void askQuestion(final Employee relayedFrom) {
		manager.registerSpontaneousTask(new Runnable() {

			@Override
			public void run() {
				manager.askQuestion(TeamLeader.this);
				TeamLeader.this.listenToAnswer(relayedFrom);
			}
			
		});
		
	}

	

}
