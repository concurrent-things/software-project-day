
public class Developer extends Employee{
	private TeamLeader teamLeader;
	private int teamNumber;
	private int teamMemberNumber;
	
	final Runnable askQuestion = new Runnable() {
		@Override
		public void run() {
			Developer.this.lockProcessing();
			teamLeader.registerSpontaneousTask(new Runnable() {

				@Override
				public void run() {
					teamLeader.askQuestion(Developer.this);
				}
				
			});
		}
	};
	
	
	
	public Developer(Scheduler scheduler, TeamLeader teamLeader, int teamNumber, int teamMemberNumber) { 
		super(scheduler);
		this.teamLeader = teamLeader;
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
	public void listenToAnswer(Employee relayTo) {
		// TODO print message about question being answered before unlocking
		unlockProcessing();
		
	}

	@Override
	public void askQuestion(Employee relayedFrom) {
		// never will be called because nobody knows less than the developer
		
	}
}