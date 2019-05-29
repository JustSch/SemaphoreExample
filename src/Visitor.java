/**
 * Create Visitor
 */
public class Visitor implements Runnable {

	public static long time = System.currentTimeMillis(); // used to find time as specified in the given template
	public String name; // used to set name
	public int visitorNum; // used to set id that was passed in
	public boolean sawMovie = false;

	public Visitor(int visitorNum) { // Constructor To Set The Visitors Name
		this.name = "Visitor- " + visitorNum;
		this.visitorNum = visitorNum;
	}

	public void run() {
		setName(name);

		while (Movie.isMuseumOpen()) {

			if (sawMovie) {
				break;// Used To Prevent Visitor From Re-entering theater
			}
			try {
				if (Movie.isTheaterFull())
					msg("I Am in The Lobby");

				Movie.getLobbySemaphore().acquire(); // Acquires Semaphore To "Stay In Lobby"

				if (Movie.isMuseumOpen() != true)	//Allows To Break If Released From Semaphore After Musuem Closed
					break;

				if (Movie.getGaveTickets())
					msg("I Got A Ticket");
				msg("I Was Able To Go In The Theater");

				Movie.getSeatCounterSemaphore().acquire();// Sets Mutex Semaphore For Checking Available Seats

				Movie.setAvailableSeats(Movie.getAvailableSeats() - 1); // Decrements Available Seats

				sawMovie = true;

				if (Movie.getAvailableSeats() == 0) {

					Movie.setTheaterFull(true);

					Movie.setAvailableSeats(Movie.theatherCapacity); // Resets Available Seats Counter

					Movie.getGiveTicketSemaphore().release(); // Releases Semaphore To Allow The Speaker To Give Tickets

				}

				Movie.getSeatCounterSemaphore().release(); // Releases Mutex Semaphore

				Movie.getWatchingMovieSemaphore().acquire(); // Visitor Starts Watching Movie aka Acquires Semaphore To
																// Keep In Theater

			} catch (InterruptedException e) {
				msg("I Left Because I Didn't Like The Movie");
			}

		}

		if (sawMovie) {

			try { // Displays These If Visitor Got To See Movie

				msg("Enjoyed The Movie");
				Thread.sleep(2000);
				msg("Begins to Browse");
				Thread.sleep(2000);
				msg("Finishes Browsing");
				Thread.sleep(4000);
				msg("Exits");
			} catch (InterruptedException e) {
				msg("Help I Can't Find The Exit");
			}

		}

		else { // Displays These If The Museum Closed Before They Could See The Movie
			msg("I Didn't Get To See The Movie");
			msg("Exits Unhappily");
		}

	}

	public void msg(String m) {
		System.out.println(
				"[" + (System.currentTimeMillis() - time) + "] " + Thread.currentThread().getName() + ": " + m);// Creates
																												// Message
																												// Using
																												// Template
																												// Given
	}

	public final void setName(String NameToSet) { // Sets Thread Name

		Thread.currentThread().setName(NameToSet);

	}

}
