/**
 * Creates the speaker
 */
public class Speaker implements Runnable {

	public static long time = System.currentTimeMillis();
	public String name;

	public Speaker(String string) { // Constructor Used To Set Thread Name
		this.name = string;
	}

	public void run() {
		setName(name);

		while (Movie.isMuseumOpen()) { // Only Keep In Loop While The Museum Is Open
			try {
				if (Movie.isTheaterFull())
					Movie.getGiveTicketSemaphore().acquire(); // Acquires Semaphore In To Keep From Giving Tickets Until
																// The Appropriate Time
				if (Movie.isMuseumOpen() != true)
					break; // Breaks From Loop If Semaphore Is Released After Closing The Museum
				giveTickets(); // Run Function To Give Tickets

				Movie.getSpeakersemaphore().acquire(); // Acquires Semaphore to Keep From Releasing Visitors Too Early

				for (int i = 0; i < Movie.theatherCapacity; i++) {

					Movie.getLobbySemaphore().release(); // Allows More Visitors Into The Theater

				}
				Movie.setTheaterFull(false); // Sets TheaterFull To False

			} catch (InterruptedException e) {

				msg("Speaker Malfunctioning Please Call A Technician"); // Shouldn't Go Here
			}
		}

		msg("Museum Closed");

	}

	public final void setName(String NameToSet) { // Sets The Name of The Thread For Output

		Thread.currentThread().setName(NameToSet);

	}

	public void msg(String m) {
		System.out.println(
				"[" + (System.currentTimeMillis() - time) + "] " + Thread.currentThread().getName() + ": " + m); // create
																													// message
																													// using
																													// template
																													// given
	}

	public void giveTickets() { // Sets If Tickets Were Given And Displays It

		int difference = Movie.theatherCapacity / Movie.party_ticket; // Used For Finding The Amount Of Groups

		int remainder = Movie.theatherCapacity % Movie.party_ticket; // Used For Groups That may Be Less Than The
																		// Party_Tickets Variable
		Movie.setGaveTickets(true);

		for (int p = 0; p < (difference); p++) {
			msg("I Gave " + Movie.party_ticket * difference + " Tickets"); // Gives Tickets For Groups Of Party_Tickets
		}

		if (remainder > 0)
			msg("I Gave " + remainder + " Tickets"); // Gives Tickets To Remaining Groups
	}
}
