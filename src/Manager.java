/**
 * 
 *
 * @author Jonathon Shippling <jjs5471@rit.edu>
 */

public class Manager extends Employee{

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
		// TODO register the day's events

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
