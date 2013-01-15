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
	/**
	 * 
	 */
	public Manager(Scheduler scheduler){
		super(scheduler, null);
		registerDaysEvents(scheduler);
	}

	/**
	 * Scheduled meeting from 11-12 and 2-3
	 */
	final protected Runnable goToExecMeeting = new Runnable() {	
		@Override
		public void run(){
			try {
				Thread.sleep(600L);
			} catch (InterruptedException e) {

			}
		}
	};


	final protected Runnable statusUpdate = new Runnable(){
		@Override
		public void run() {
			// TODO acquire lock on conference room
			try {
				Thread.sleep(150L);
			} catch (InterruptedException e) {

			}
		}

	};

	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		scheduler.registerEvent(endOfDayLeave, this, 0);
		

	}

	@Override
	protected boolean canAnswerQuestion() {
		return true;
	}

	@Override
	protected void onQuestionAsked(Employee askedTo) {
		askedTo.resumeWithAnswer(new Runnable(){
			public void run(){
				try {
					Thread.sleep(600L);
				} catch (InterruptedException e) {
					
				}
			}
		});

	}

	@Override
	protected void onAnswerReceived(Employee receivedFrom) {
		// TODO Auto-generated method stub

	}	
}
