import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TeamLeader extends Employee{	
	private int teamNumber;
	private int teamMemberNumber;
	private long lunchTime;
	private Developer[] team;
	
	protected Runnable goToLunch;
	protected Runnable endOfDayLeave;
	protected Runnable standUpMeeting;
	protected Runnable teamSchedulingMeeting;


	protected void initRunnables() {
		// TODO Auto-generated method stub
	
		goToLunch = new Runnable() {
			
			public void run() {
				try {
	
					System.out.println("Team Leader " + teamNumber + teamMemberNumber + " is going to lunch.");
					
					Thread.sleep(lunchTime);
	
					System.out.println("Team Leader " + teamNumber + teamMemberNumber + " has returned from lunch." );
					
				} catch (InterruptedException e) {
					
				}
			}
		};
		
		endOfDayLeave = new Runnable() {
	
			public void run() {
				System.out.println("Team Leader " + teamNumber + teamMemberNumber + " is leaving work.");
				//Throw an interrupt which states that this team leader thread can now be terminated
				//as the developer has now left. 
				interrupt();
			} 
		};
		
		teamSchedulingMeeting = new Runnable() {
	
			public void run() {
				System.out.println("Team Leader " + teamNumber + teamMemberNumber + " had the scheduling meeting.");
				//conferenceRoom.enterRoom()
				
			}
			
		};
		
		standUpMeeting = new Runnable() {
			
			public void run() {
				
				((Manager)getSupervisor()).getOffice().enterRoom();
				
			}
			
		};
	}
	
	public TeamLeader(Scheduler scheduler, Manager manager, int teamNumber, int teamMemberNumber) {
		
		super(scheduler, manager);
		this.teamNumber = teamNumber;
		this.teamMemberNumber = teamMemberNumber;
		this.setName("" + teamNumber + teamMemberNumber);
		this.teamMemberNumber = teamMemberNumber;
		this.teamNumber = teamNumber;
		initRunnables();
		registerDaysEvents(scheduler);
		
	}
	
	public void assignTeam(Developer[] team){
		
		this.team = team;
		
	}

	@Override
	protected void registerDaysEvents(Scheduler scheduler) {

		long endofDayMeeting = TimeUnit.NANOSECONDS.convert(4800, TimeUnit.MILLISECONDS);
		
		Random randomGen = new Random();
		
		//Randomly generating a time to go to lunch 
		//It randomly selects a time between the hours of 12 and 1 in minutes. 
		long randomlunchTime = randomGen.nextInt(3000 - 2400 + 1) + 2400;
		long devLunchStart = TimeUnit.NANOSECONDS.convert(randomlunchTime, TimeUnit.MILLISECONDS);

		lunchTime = randomGen.nextInt(600 - 300 + 1) + 300;
		lunchTime = TimeUnit.NANOSECONDS.convert(lunchTime, TimeUnit.MILLISECONDS);
		
		long leaveTime = 4800 + lunchTime;
		leaveTime = TimeUnit.NANOSECONDS.convert(leaveTime, TimeUnit.MILLISECONDS);
		
		scheduler.registerEvent(endOfDayLeave, this, leaveTime, true);
		scheduler.registerEvent(goToLunch, this, devLunchStart, false);
		scheduler.registerEvent(standUpMeeting, this, 0, false);
		scheduler.registerEvent(teamSchedulingMeeting, this, endofDayMeeting, false);
		//scheduler.registerEvent(teamSchedulingMeeting, this, 4800000000L);
		
		//can ask up to 3 questions a day
		int numQuestions = (int)(3 * Math.random());
		
		for (int i = 0; i < numQuestions; i++){
			
			scheduler.registerEvent(askQuestion, this, (long)(Math.random() * leaveTime), false);
			
		}
	}

	@Override
	protected void onQuestionAsked(Employee askedTo) {

		System.out.println("Team Leader " + this.getName() + " asked " + askedTo.getName() + "a question.");	
		
	}

	@Override
	protected void onAnswerReceived(Employee receivedFrom) {
		System.out.println("Team Leader " + this.getName() + " received an answer from " + receivedFrom.getName() + ".");		
	}

	@Override
	protected void onQuestionCancelled(Employee notAvailable) {
		System.out.println("Team Leader "+this.getName()+"'s question was " +
				"cancelled, "+notAvailable.getName()+" is not available");
	}
	
}
