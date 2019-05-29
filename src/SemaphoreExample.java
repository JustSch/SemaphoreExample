
public class SemaphoreExample {

	public static void main(String[] args) {

		
		setNumVisitors(23); //Sets Default NumVisitors To 23
		Movie.setAvailableSeats(Movie.theatherCapacity); //Sets Available Seats Used For Threads

		Movie.visitorArray = new Thread[Movie.numVisitors];	//Array Used To Create Threads

		Thread clockThread = new Thread(new Clock("Clock")); // Makes Clock Thread

		clockThread.start();

		for (int i = 0; i < Movie.numVisitors; i++) {				//Creates Each Thread With Array Used Earlier
			Thread visitorThread = new Thread(new Visitor(i + 1));
			Movie.visitorArray[i] = visitorThread;
		}

		for (Thread t : Movie.visitorArray)
			t.start(); // starts each thread in the visitor Array using For Each Loop

	}
	
	public static void setNumVisitors(int givenVisitors) {
		Movie.numVisitors = givenVisitors;
	}

}
