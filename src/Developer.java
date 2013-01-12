
public class Developer extends Employee{
	
	private int teamNumber;
	private int teamMemberNumber;
	
	
	public Developer(Scheduler scheduler, int teamNumber, int teamMemberNumber) { 
		super(scheduler);
		this.teamNumber = teamNumber; 
		this.teamMemberNumber = teamMemberNumber; 
	}
	
	public void askQuestion() { 	
		
	}
	
	public void goToLunch() { 
		 	
	}
	
	public void provideStatusUpdated() { 
		
	}
	
	public void run() { 
		
	}

	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		// TODO Auto-generated method stub
		
	}
}