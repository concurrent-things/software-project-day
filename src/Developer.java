import java.util.Random;


/**
 * 
 * @author Shun Mok Bhark
 *
 */
public class Developer extends Employee{
	
	private int teamNumber;
	private int teamMemberNumber;
	private long devDayStartTime; 
	private long devLunchTime; 
	private long devDayEndTime; 
	
	/**
	 * Developer Object Contructor 
	 * @param scheduler
	 * @param teamLeader
	 * @param teamNumber
	 * @param teamMemberNumber
	 */
	public Developer(Scheduler scheduler, TeamLeader teamLeader, int teamNumber, int teamMemberNumber) { 
		
		super(scheduler, teamLeader);
		this.teamNumber = teamNumber; 
		this.teamMemberNumber = teamMemberNumber; 
		
		//Register days event with scheduler. 
		startDevTime();
		calculateDevLunch();
		calculateDevEndTime();
		
	}
	
	private void startDevTime()  { 
		
		devDayStartTime = System.nanoTime();
	}
	
	private void calculateDevLunch() { 
		
		//Randomly generating time for lunch 
		Random randomGen = new Random(); 
		int randomLunchTime = randomGen.nextInt(600) + 300;
		
	}
	
	private void calculateDevEndTime() { 
		
		
	}
	
	public int getDevTeamNumber() { 
		
		return teamNumber; 
	}
	
	public int getDevTeamMemberNumber() { 
		
		return teamMemberNumber; 
	}
	
	public void goToLunch() { 
		 	
	}
	
	public void provideStatusUpdated() { 
		
	}

	protected void registerDaysEvents(Scheduler scheduler) {
		
	}

	protected void onQuestionAsked(Employee askedTo) {
		
	}

	protected void onAnswerReceived(Employee receivedFrom) {
		
	}
}