import java.util.Random;
import java.util.concurrent.TimeUnit;


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
	
	//Runnables 
	final protected Runnable endOfDayLeave = new Runnable() {

		public void run() {
			
		} 
	};
	
	
	final protected Runnable goToLunch = new Runnable() { 
		
		public void run() { 
			
		}
	};
	
	/** 
	 * Method that sets the simulated start time of the developer. 
	 */
	private void startDevTime()  { 
		
		devDayStartTime = System.nanoTime();
	}
	
	/** 
	 * Method that randomly generates a simulated lunch time period for the developer 
	 */
	private void calculateDevLunch() { 
		
		//Randomly generating time for lunch 
		Random randomGen = new Random(); 
		int randomLunchTime = randomGen.nextInt(600) + 300;
		devLunchTime = TimeUnit.NANOSECONDS.convert(randomLunchTime, TimeUnit.MILLISECONDS);
		
	}
	
	/**
	 * Method that calculates the simulated end time of the developer. 
	 */
	private void calculateDevEndTime() { 
		
		long modifiedStartTime = devDayStartTime + devLunchTime;
		devDayEndTime = modifiedStartTime + (TimeUnit.NANOSECONDS.convert(4800, TimeUnit.MILLISECONDS));
		
	}
	
	/**
	 * Getter method to return team number of developer
	 * @return
	 */
	public int getDevTeamNumber() { 
		
		return teamNumber; 
	}
	
	/**
	 * Getter method to return the number of the developer in the team
	 * @return
	 */
	public int getDevTeamMemberNumber() { 
		
		return teamMemberNumber; 
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