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
	private long devLunchStart;
	
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
		
		// WARING: The registerDaysEvents hook will be called before
		// anything in this constructor. Therefore Initialization of timing
		// needs to happen in that method.
		
	}
	
	//Runnables 
	final protected Runnable endOfDayLeave = new Runnable() {

		public void run() {
			System.out.println("Developer " + teamNumber + teamMemberNumber + " is leaving work.");
			
			//Throw an interrupt which states that this developer thread can now be terminated
			//as the developer has now left. 
			interrupt();
		} 
	};
	
	
	final protected Runnable goToLunch = new Runnable() { 
		
		public void run() { 
			
			System.out.println("Developer " + teamNumber + teamMemberNumber + " is going to lunch.");
			try {
				sleep(devLunchTime);
			} catch (InterruptedException e) {
				System.out.println("Exception when developer trying to go to lunch.");
				e.printStackTrace();
			}
			System.out.println("Developer " + teamNumber + teamMemberNumber + " has returned from lunch." );
		}
	};
	
	/** 
	 * Method that sets the simulated start time of the developer. 
	 */
	private void startDevTime()  { 
		
		devDayStartTime = System.nanoTime();
	}
	
	/** 
	 * Method which generates a random number of questions. 
	 */
	private void scheduleRandomQuestion(Scheduler scheduler) {
		
		long timeToScheduleQuestions;
		//Generate random number of questions to ask max is 10 questions. 
		Random randomGen = new Random(); 
		int questionsToAsk = randomGen.nextInt(10);
		
		
		for (int i = 0; i < questionsToAsk; i++) { 
		
			timeToScheduleQuestions = randomGen.nextInt(8);
			timeToScheduleQuestions = TimeUnit.NANOSECONDS.convert(timeToScheduleQuestions, TimeUnit.HOURS);
			scheduler.registerEvent(askQuestion, this, timeToScheduleQuestions);
		}
	}
	
	/** 
	 * Method that randomly generates a simulated lunch time period for the developer 
	 */
	private void calculateDevLunch() { 
		
		Random randomGen = new Random(); 
		
		//Randomly generating a time to go to lunch 
		long randomLunchStartTime = randomGen.nextInt(250 - 240 + 1) + 240;
		devLunchStart = TimeUnit.NANOSECONDS.convert(randomLunchStartTime, TimeUnit.MINUTES);
		
		//Randomly generating total timefor lunch 
		int randomLunchTime = randomGen.nextInt(60 - 30 + 1) + 30;
		devLunchTime = TimeUnit.NANOSECONDS.convert(randomLunchTime, TimeUnit.MINUTES);
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

	protected void registerDaysEvents(Scheduler scheduler) {
		
		//Register days event with scheduler. 
		startDevTime();
		scheduleRandomQuestion(scheduler);
		calculateDevLunch();
		calculateDevEndTime();
		scheduler.registerEvent(endOfDayLeave, this, devDayEndTime);
		scheduler.registerEvent(goToLunch, this, devLunchStart);
	}

	/** 
	 * Not required for developer. 
	 */
	protected void onQuestionAsked(Employee askedTo) {
		
	}

	protected void onAnswerReceived(Employee receivedFrom) {
		
		System.out.println("Recieved an answer to question from " + receivedFrom.getName());
	}
}