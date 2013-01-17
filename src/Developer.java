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
	
	protected Runnable endOfDayLeave;
	protected Runnable goToLunch;
	protected Runnable goToEndofDayMeeting;
	
	
	/**
	 * Developer Object Contructor 
	 * @param scheduler
	 * @param teamLeader
	 * @param teamNumber
	 * @param teamMemberNumber
	 */
	public Developer(Scheduler scheduler, TeamLeader teamLeader, int teamNumber, int teamMemberNumber) { 
		super(scheduler, teamLeader);
		this.setName("" + teamNumber + teamMemberNumber);

		this.teamNumber = teamNumber; 
		this.teamMemberNumber = teamMemberNumber; 
		
		initRunnables();
		registerDaysEvents(scheduler);
		
	}
	
	@Override
	protected void initRunnables() {
		endOfDayLeave = new Runnable() {

			public void run() {
				System.out.println("Developer " + teamNumber + teamMemberNumber + " is leaving work.");
				
				//Throw an interrupt which states that this developer thread can now be terminated
				//as the developer has now left. 
				interrupt();
			} 
		};
		
		goToLunch = new Runnable() { 
			
			public void run() { 
				
				System.out.println("Developer " + teamNumber + teamMemberNumber + " is going to lunch.");
				try {
					long devLunchTimeSleep = TimeUnit.MILLISECONDS.convert(devLunchTime, TimeUnit.NANOSECONDS);
					sleep(devLunchTimeSleep);
				} catch (InterruptedException e) {
					System.out.println("Exception found when developer trying to go to lunch.");
					e.printStackTrace();
				}
				System.out.println("Developer " + teamNumber + teamMemberNumber + " has returned from lunch." );
			}
		};
		
		goToEndofDayMeeting = new Runnable() { 
			
			public void run() { 
				
				
				System.out.println("Developer " + teamNumber + teamMemberNumber + " is going to the end of the day meeting at conference room.");
			}
		};
	}
	
	
	/** 
	 * Method that sets the simulated start time of the developer. 
	 */
	private void startDevTime(Scheduler scheduler)  { 
		
		devDayStartTime = System.nanoTime();
		long schedulerStartTime = scheduler.getStartTimeInNanos();
		devDayStartTime = devDayStartTime - schedulerStartTime;
		
	}
	
	/** 
	 * Method which generates a random number of questions. 
	 */
	private void scheduleRandomQuestion(Scheduler scheduler) {
		
		long timeToScheduleQuestions;
		
		//Generate random number of questions to ask max is 10 questions. 
		Random randomGen = new Random(); 
		int questionsToAsk = randomGen.nextInt(10);
		
		//For the randomly generated number of questions to ask it generates 
		//a random time to ask questions within the 8 hour work period. 
		for (int i = 0; i < questionsToAsk; i++) { 
		
			timeToScheduleQuestions = randomGen.nextInt(4800);
			timeToScheduleQuestions = TimeUnit.NANOSECONDS.convert(timeToScheduleQuestions, TimeUnit.MILLISECONDS);
			scheduler.registerEvent(askQuestion, this, timeToScheduleQuestions, true);
		}
	}
	
	/** 
	 * Method that randomly generates a simulated lunch time period for the developer 
	 */
	private void calculateDevLunch() { 
		
		Random randomGen = new Random(); 
		
		//Randomly generating a time to go to lunch 
		//It randomly selects a time between the hours of 12 and 1 in minutes. 
		long randomLunchStartTime = randomGen.nextInt(3000 - 2400 + 1) + 2400;
		devLunchStart = TimeUnit.NANOSECONDS.convert(randomLunchStartTime, TimeUnit.MILLISECONDS);
		
		//Randomly generating total timefor lunch 
		//It randomly generates a time between 0 - 60 minutes for time for lunch.
		int randomLunchTime = randomGen.nextInt(600 - 300 + 1) + 300;
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

	protected void registerDaysEvents(Scheduler scheduler) {
		
		long endofDayMeeting = TimeUnit.NANOSECONDS.convert(4800, TimeUnit.MILLISECONDS);
		
		//Register days event with scheduler. 
		startDevTime(scheduler);
		scheduleRandomQuestion(scheduler);
		calculateDevLunch();
		calculateDevEndTime();
		scheduler.registerEvent(endOfDayLeave, this, devDayEndTime, true);
		scheduler.registerEvent(goToLunch, this, devLunchStart, false);
		scheduler.registerEvent(goToEndofDayMeeting, this, endofDayMeeting, false);
	}

	/** 
	 * Not required for developer. 
	 */
	protected void onQuestionAsked(Employee askedTo) {
		
	}

	protected void onAnswerReceived(Employee receivedFrom) {
		
		System.out.println("Recieved an answer to question from " + receivedFrom.getName());
	}

	@Override
	protected void onQuestionCancelled(Employee notAvailable) {
		// TODO Auto-generated method stub
		
	}
}