/**
 * 
 *
 * @author Jonathon Shippling <jjs5471@rit.edu>
 */

public class Manager extends Employee{

	//how long, in ms, it takes to complete various tasks
	private long manLunchTime = 600L; 
	private long manExecMeetTime = 600L;
	private long manStatusMeetTime = 150L;
	
	// These longs represent the earliest time a manager can begin a task
	private long manDevMeetStart = 0L;			//8:00 am, waits in office for
													//all leaders to arrive by 8:30
	
	private long manExecMeet1Start = 1200000000L; 	//10:00 am, unless a question is in his queue
	private long manLunchStart = 2400000000L; 		//12:00 pm, unless a question is in his queue
	private long manExecMeet2Start = 3600000000L;	//2:00 pm, unless a question is in his queue
	
	private long manStatusMeetStart = 4800000000L;	//4:00 pm, waits in conference room for
													//all team members to arrive by 4:15
	
	private long manEndDayTime = 5400000000L;		//5:00 pm, unless a question in in	 his queue
	
	/**
	 * 
	 */
	public Manager(Scheduler scheduler){
		super(scheduler, null);
		registerDaysEvents(scheduler);
	}

	final protected Runnable goToDevMeeting = new Runnable() {
		@Override
		public void run(){
			System.out.println("Manager goes to morning developer meeting");
			// TODO wait for people to arrive
			try{
				Thread.sleep(manStatusMeetTime);
			} catch (InterruptedException e){
				
			}
		}
	};
	
	/**
	 * Runnable for the scheduled meetings from 10-11 and 2-3
	 */
	final protected Runnable goToExecMeeting = new Runnable() {	
		@Override
		public void run(){
			System.out.println("Manager goes to an Executive Meeting");
			try {
				Thread.sleep(manExecMeetTime);
			} catch (InterruptedException e) {

			}
			System.out.println("Manager has left an Executive Meeting");
		}
	};


	final protected Runnable goToLunch = new Runnable(){
		@Override
		public void run() {
			System.out.println("Manager is going to lunch");
			try {
				Thread.sleep(manLunchTime);
			} catch (InterruptedException e) {
	
			}
			System.out.println("Manager has returned from lunch");
		}
		
	};
	/**
	 * Runnable for the status update meeting taking place after 4 pm
	 */
	final protected Runnable goToStatusUpdateMeeting = new Runnable(){
		@Override
		public void run() {
			System.out.println("Manager is acquiring developers for the project"
					+ " status update meeting");
			// TODO acquire lock on conference room
			try {
				Thread.sleep(manStatusMeetTime);
			} catch (InterruptedException e) {

			}
			System.out.println("Manager has adjourned project status update" +
					" meeting");
		}

	};

	final protected Runnable endOfDayLeave = new Runnable() {
		public void run() {
			System.out.println("Manager is leaving work.");
			
			//Throw an interrupt which states that this developer thread can now be terminated
			//as the developer has now left. 
			interrupt();
		} 
	};

	
	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		scheduler.registerEvent(goToDevMeeting, this, manDevMeetStart);
		scheduler.registerEvent(goToExecMeeting, this, manExecMeet1Start);
		scheduler.registerEvent(goToLunch, this, manLunchStart);
		scheduler.registerEvent(goToExecMeeting, this, manExecMeet2Start);
		scheduler.registerEvent(goToStatusUpdateMeeting, this, manStatusMeetTime);
		scheduler.registerEvent(endOfDayLeave, this, manEndDayTime);

	}

	private void startManagerTime(){
		// TODO do something?
	}
	
	@Override
	protected boolean canAnswerQuestion() {
		return true;
	}

	@Override
	protected void onQuestionAsked(Employee askedTo) {
		System.out.println("Manager received a question from "+askedTo.getName());
		try {
			Thread.sleep(100L);
		} catch (InterruptedException e) {

		}
		System.out.println("Manager answered a question from "+askedTo.getName());
		
	}

	@Override
	protected void onAnswerReceived(Employee receivedFrom) {
		// Managers never receive answers, only ask questions
		return;
	}	
}
