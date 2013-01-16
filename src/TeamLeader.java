import java.util.Random;

public class TeamLeader extends Employee{
	
	private int teamNumber;
	private int teamMemberNumber;
	private long lunchTime;
	private Developer[] team;
	
	final protected Runnable goToLunch = new Runnable() {
		
		public void run() {
			try {

				System.out.println("Developer " + teamNumber + teamMemberNumber + " is going to lunch.");
				
				Thread.sleep(lunchTime);

				System.out.println("Developer " + teamNumber + teamMemberNumber + " has returned from lunch." );
				
			} catch (InterruptedException e) {
				
			}
		}
	};
	
	final protected Runnable endOfDayLeave = new Runnable() {

		public void run() {
			System.out.println("Developer " + teamNumber + teamMemberNumber + " is leaving work.");
			//Throw an interrupt which states that this developer thread can now be terminated
			//as the developer has now left. 
			interrupt();
		} 
	};
	
	final protected Runnable teamSchedulingMeeting = new Runnable() {

		public void run() {

			//grab all employees in team
			
			//acquire lock for conference room
			
			//have meeting
			try{
				
				Thread.sleep(0);
				
			}catch(Exception e){
				
			}
			
			//release team
			
			//release conference room lock
			
		}
		
	};
	
	public TeamLeader(Scheduler scheduler, Manager manager, int teamNumber, int teamMemberNumber) {
		
		super(scheduler, manager);
		this.teamNumber = teamNumber;
		this.teamMemberNumber = teamMemberNumber;
		
	}
	
	public void assignTeam(Developer[] team){
		
		this.team = team;
		
	}

	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		
		lunchTime = (long) (300 + 300 * Math.random());
		
		long leaveTime = 4800 + lunchTime;
		
		scheduler.registerEvent(endOfDayLeave, this, leaveTime);
		scheduler.registerEvent(goToLunch, this, (long)(600 * 4 + 600 * Math.random()));
		
		//can ask up to 3 questions a day
		int numQuestions = (int)(3 * Math.random());
		
		for (int i = 0; i < numQuestions; i++){
			
			scheduler.registerEvent(askQuestion, this, (long)(Math.random() * leaveTime));
			
		}
		
		
	}

	@Override
	protected void onQuestionAsked(Employee askedTo) {

		System.out.println("Team Leader " + teamNumber + teamMemberNumber + " answered a question.");	
		
	}

	@Override
	protected void onAnswerReceived(Employee receivedFrom) {

		System.out.println("Team Leader " + teamNumber + teamMemberNumber + " received an answer.");		
		
	}

	@Override
	protected void initRunnables() {
		// TODO Auto-generated method stub
		
	}	
}
