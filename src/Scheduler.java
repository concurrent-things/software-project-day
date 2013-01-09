
public class Scheduler {
	private long startMillis;
	
	public Scheduler() {
		this.startMillis = System.nanoTime();
	}
	
	
	public void registerEvent(Runnable event, Employee employee, long millisFromNow) {
		
	}
	
	public long getStartTimeInMillis() {
		return startMillis;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
