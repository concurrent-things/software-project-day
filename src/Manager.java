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
	}


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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onAnswerReceived(Employee receivedFrom) {
		// TODO Auto-generated method stub
		
	}	
}
