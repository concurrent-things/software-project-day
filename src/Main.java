public class Main {
	public static final int POOLS_MAX_THREADS = 5;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler(POOLS_MAX_THREADS);
		
		Manager manager1 = new Manager(scheduler);

		// 3 teams of developers consisting of 1 lead and 3 devs
		for (int i = 1; i <= 3; i++) {
			TeamLeader teamLead = new TeamLeader(scheduler, i, 1);
			Developer dev1 = new Developer(scheduler, i, 2);
			Developer dev2 = new Developer(scheduler, i, 3);
			Developer dev3 = new Developer(scheduler, i, 4);
		}

	}

}
