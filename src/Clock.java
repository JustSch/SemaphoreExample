/**
 * Creates The Clock
 */
public class Clock implements Runnable {
	public static long time = System.currentTimeMillis();
	public String clockName;

	public Clock() {

	}

	public Clock(String clockName) { // Constructor Used To Set Thread Name

		setName(clockName);
		this.clockName = clockName;
	}

	public final void setName(String clockNameToSet) {

		Thread.currentThread().setName(clockNameToSet); // Sets name of Clock

	}

	public void run() {
		setName(clockName);
		Movie.setMuseumOpen(true);// opens museum

		Thread speakerThread = new Thread(new Speaker("Speaker"));
		speakerThread.start();

		while (hasShowings()) {

			try {

				msg("The Movie Has Started");

				Thread.sleep(3000); // The Movie Lasts 3 Second. 1 Second Representing 10 Minutes

				msg("The Movie Is Over");

				for (int m = 0; m < Movie.theatherCapacity; m++) { // Releases All Visitors From The Theater
					Movie.getWatchingMovieSemaphore().release();
				}

				releaseSeats(); // Will Signal Speaker To Allow More Visitors In The Theater

			} catch (InterruptedException e) {
				msg("I Am Broken Please Send A Technician");
			}

		}
		// After Museum Closes

		Movie.setMuseumOpen(false);

		Movie.getGiveTicketSemaphore().release();//Release Speaker
		if (Movie.getLobbySemaphore().hasQueuedThreads()) {//Signal All Remaining Visitors
			for (int l = 0; l < 2 * ((Movie.numVisitors / Movie.theatherCapacity)
					+ (Movie.numVisitors % Movie.theatherCapacity)); l++) {
				Movie.getLobbySemaphore().release();
			}

		}

		msg("Is Terminating");

	}

	public boolean hasShowings() { // Checks If Movie Has Showings Left
		if (Movie.showingsLeft > 0) {
			Movie.showingsLeft--;
			return true;
		}

		return false;
	}

	public void msg(String m) {
		System.out.println(
				"[" + (System.currentTimeMillis() - time) + "] " + Thread.currentThread().getName() + ": " + m); // Sets
																													// Message
																													// Using
																													// Template
																													// Given
	}

	public void releaseSeats() { // Will Signal Speaker To Allow More Visitors In The Theater
		if (Movie.getGaveTickets()) {
			Movie.getSpeakersemaphore().release();
		}

		else {
			msg("The Movie Ended Early"); // Should Never Go Here
		}
	}

}
