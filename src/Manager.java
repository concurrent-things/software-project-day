/**
 * Class for the Manager of the Software Project Day project.  Manager answers
 * team leader questions, holds meetings, goes to meetings, and goes to lunch,
 * and when not doing those things, does whatever managers do.
 *
 * @author Jonathon Shippling <jjs5471@rit.edu>
 */

public class Manager extends Employee{
	// The manager's own office
	private final Office office;
	
	//The conference room for the morning meeting
	private final ConferenceRoom conferenceRoom;
	private final AfternoonConferenceRoom afternoonConferenceRoom;
	
	//how long, in ms, it takes to complete various tasks
	private long manLunchTime = 600L; 
	private long manExecMeetTime = 600L;
	
	// The earliest time a manager can begin a task, in ns
	private long manDevMeetStart = 0L;				//8:00 am, waits in office for
													//all leaders to arrive by 8:30
	private long manExecMeet1Start = 1200000000L; 	//10:00 am, unless a question is in his queue
	private long manLunchStart = 2400000000L; 		//12:00 pm, unless a question is in his queue
	private long manExecMeet2Start = 3600000000L;	//2:00 pm, unless a question is in his queue
	private long manStatusMeetStart = 4800000000L;	//4:00 pm, waits in conference room for
													//all team members to arrive by 4:15
	private long manEndDayStart = 5400000000L;		//5:00 pm, unless a question in in his queue
	
	// The runnables scheduled
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
	public Manager(Scheduler scheduler, int numTeamLeads, ConferenceRoom 
			conferenceRoom, AfternoonConferenceRoom afternoonConferenceRoom){
		super(scheduler, null);
		this.setName("Manager");
		this.office = new Office(numTeamLeads + 1, this);
		this.conferenceRoom = conferenceRoom;
		this.afternoonConferenceRoom = afternoonConferenceRoom;
		initRunnables();
		registerDaysEvents(scheduler);
	}

	/**
	 * Initialization of the Manager's runnables for scheduling
	 */
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
				afternoonConferenceRoom.enterRoom();
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
	
	/**
	 * Manager registering his runnables with the scheduler
	 */
	@Override
	protected void registerDaysEvents(Scheduler scheduler) {
		scheduler.registerEvent(goToDevMeeting, this, manDevMeetStart, false);
		scheduler.registerEvent(goToExecMeeting, this, manExecMeet1Start, false);
		scheduler.registerEvent(goToLunch, this, manLunchStart, false);
		scheduler.registerEvent(goToExecMeeting, this, manExecMeet2Start, false);
		scheduler.registerEvent(goToStatusUpdateMeeting, this, manStatusMeetStart, false);
		scheduler.registerEvent(endOfDayLeave, this, manEndDayStart, true);

	}

	/**
	 * Returns the Manager's office
	 * 
	 * @return
	 */
	public Office getOffice(){
		return office;
	}
	
	/**
	 * The manager is able to answer questions always
	 */
	@Override
	protected boolean canAnswerQuestion() {
		return true;
	}

	/**
	 * Manager receives the question and then sleeps for the appropriate 
	 * amount of time
	 */
	@Override
	protected void onQuestionAsked(Employee askedTo) {
		System.out.println("Manager received a question from "+askedTo.getName());
		try {
			Thread.sleep(100L);
		} catch (InterruptedException e) {

		}
		System.out.println("Manager answered a question from "+askedTo.getName());
		
	}

	/**
	 * Managers never receive answers, only ask questions
	 */
	@Override
	protected void onAnswerReceived(Employee receivedFrom) {
		return;
	}

	@Override
	protected void onQuestionCancelled(Employee notAvailable) {
		// TODO Auto-generated method stub
		
	}


}
