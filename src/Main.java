public class Main {
	public static final int POOLS_MAX_THREADS = 5;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler(POOLS_MAX_THREADS);
		
		Manager manager1 = new Manager(scheduler);
		manager1.start();

		// 3 teams of developers consisting of 1 lead and 3 devs
		for (int i = 1; i <= 3; i++) {
//			TeamLeader teamLead = new TeamLeader(scheduler, manager1, i, 1);
//			teamLead.start();
//			
//			Developer dev1 = new Developer(scheduler, teamLead, i, 2);
//			dev1.start();
//			
//			Developer dev2 = new Developer(scheduler, teamLead, i, 3);
//			dev2.start();
//			
//			Developer dev3 = new Developer(scheduler, teamLead, i, 4);
//			dev3.start();
		}
		
	}

}
