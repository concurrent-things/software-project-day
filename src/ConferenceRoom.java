
public class ConferenceRoom {

	private static ConferenceRoom cf;

	private ConferenceRoom() {};

	public static ConferenceRoom getConferenceRoom() {
		if (cf == null) {
			cf = new ConferenceRoom();
		}

		return cf;
	}

	public void conductMeeting(){


	}

	public void enterRoom(){


	}

	public void leaveRoom(){


	}



}
