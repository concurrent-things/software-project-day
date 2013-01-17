/**
 * 
 *
 * @author Jonathon Shippling <jjs5471@rit.edu>
 */

public class Manager extends Employee{
	private final Office office;
	//private final ConferenceRoom conferenceRoom;
	//how long, in ms, it takes to complete various tasks
	private long manLunchTime = 600L; 
	private long manExecMeetTime = 600L;
	
	// The earliest time a manager can begin a task
	private long manDevMeetStart = 0L;			//8:00 am, waits in office for
												//all leaders to arrive by 8:30
	
	private long manExecMeet1Start = 1200000000L; 	//10:00 am, unless a question is in his queue
	private long manLunchStart = 2400000000L; 		//12:00 pm, unless a question is in his queue
	private long manExecMeet2Start = 3600000000L;	//2:00 pm, unless a question is in his queue
	
	private long manStatusMeetStart = 4800000000L;	//4:00 pm, waits in conference room for
													//all team members to arrive by 4:15
	
	private long manEndDayStart = 5400000000L;		//5:00 pm, unless a question in in	 his queue
	
	protected Runnable goToDevMeeting;
	protected Runnable goToExecMeeting;
	protected Runnable goToLunch; 
	protected Runnable goToStatusUpdateMeeting;
	protected Runnable endOfDayLeave;
	
	/**
	 * Constructor for the Manager
	 * 
	 * @param scheduler the lone event scheduler
	 * @param numTeamLeads the number of team leaders
	 */
	public Manager(Scheduler scheduler, int numTeamLeads){
		super(scheduler, null);
		this.setName("Manager");
		this.office = new Office(numTeamLeads, this);
		initRunnables();
		registerDaysEvents(scheduler);
	}

	@Override
	protected void initRunnables() {
		
		/**
		 * Runnable for the developer meeting starting as early as 8am
		 */
		goToDevMeeting = new Runnable() {
			@Override
			public void run(){
				System.out.println("Manager goes to morning developer meeting");
				office.enterRoom();
			}
		};
		
		/**
		 * Runnable for the scheduled meetings from 10-11 and 2-3
		 */
		goToExecMeeting = new Runnable() {	
			@Override
			public void run(){
				System.out.println("Manager goes to an Executive Meeting");
				try {
					Thread.sleep(manExecMeetTime);
				} catch (InterruptedException e) {

				}
				System.out.println("Manager left an Executive Meeting");
			}
		};
		
		/**
		 * Runnable for the Manager's lunch time
		 */
		goToLunch = new Runnable(){
			@Override
			public void run() {
				System.out.println("Manager goes to lunch");
				try {
					Thread.sleep(manLunchTime);
				} catch (InterruptedException e) {
		
				}
				System.out.println("Manager returns from lunch");
			}
			
		};
		
		/**
		 * Runnable for the status update meeting taking place as early as 4 pm
		 */
		goToStatusUpdateMeeting = new Runnable(){
			@Override
			public void run() {
				System.out.println("Manager goes to the project status update"+
						" meeting");
				// TODO enter the afternoon conference meeting room
			}

		};
		
		/**
		 * Runnable for the manager leaving at the end of the day
		 */
		endOfDayLeave = new Runnable() {
			public void run() {
				System.out.println("Manager leaves work.");
				
				//Throw an interrupt which states that this developer thread can now be terminated
				//as the developer has now left. 
				interrupt();
			} 
		};
	}	
	
	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		scheduler.registerEvent(goToDevMeeting, this, manDevMeetStart);
		scheduler.registerEvent(goToExecMeeting, this, manExecMeet1Start);
		scheduler.registerEvent(goToLunch, this, manLunchStart);
		scheduler.registerEvent(goToExecMeeting, this, manExecMeet2Start);
		scheduler.registerEvent(goToStatusUpdateMeeting, this, manStatusMeetStart);
		scheduler.registerEvent(endOfDayLeave, this, manEndDayStart);

	}

	public Office getOffice(){
		return office;
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
