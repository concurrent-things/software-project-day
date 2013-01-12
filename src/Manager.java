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
		super(scheduler);
	}


	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		// TODO register the day's events
		
	}


	@Override
	public void listenToAnswer(Employee relayTo) {
		// never will be called, because nobody knows more than the manager
		
	}


	@Override
	public void askQuestion(Employee relayedFrom) {
		// TODO pause for 10 seconds
		
	}
	
	
	
}
