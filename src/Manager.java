/**
 * 
 *
 * @author Jonathon Shippling <jjs5471@rit.edu>
 */

public class Manager extends Employee{
	
	final protected Runnable endOfDayLeave = new Runnable() {
		public void run() {
			System.out.println("Manager is leaving work.");
			
			//Throw an interrupt which states that this developer thread can now be terminated
			//as the developer has now left. 
			interrupt();
		} 
	};

	private long manLunchTime = 600L; 
	private long manLunchStart = 2400000000L; 
	private long manExecMeet1Time;
	private long manExecMeet1Start; 
	private long manExecMeet2Time;
	private long manExecMeet2Start;
	
	/**
	 * 
	 */
	public Manager(Scheduler scheduler){
		super(scheduler, null);
		registerDaysEvents(scheduler);
	}

	/**
	 * Runnable for the scheduled meetings from 11-12 and 2-3
	 */
	final protected Runnable goToExecMeeting = new Runnable() {	
		@Override
		public void run(){
			System.out.println("Manager goes to an Executive Meeting");
			try {
				Thread.sleep(manLunchTime);
			} catch (InterruptedException e) {

			}
			System.out.println("Manager has left an Executive Meeting");
		}
	};


	/**
	 * Runnable for the status update meeting taking place after 4 pm
	 */
	final protected Runnable statusUpdate = new Runnable(){
		@Override
		public void run() {
			System.out.println("Manager is acquiring developers for the project"
					+ " status update meeting");
			// TODO acquire lock on conference room
			try {
				Thread.sleep(150L);
			} catch (InterruptedException e) {

			}
			System.out.println("Manager has adjourned project status update" +
					" meeting");
		}

	};

	final protected Runnable goToLunch = new Runnable(){
		@Override
		public void run() {
			System.out.println("Manager is going to lunch");
			try {
				Thread.sleep(150L);
			} catch (InterruptedException e) {

			}
			System.out.println("Manager has returned from lunch");
		}
		
	};
	
	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		scheduler.registerEvent(endOfDayLeave, this, 0);
		

	}

	private void startManagerTime(){
		
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
