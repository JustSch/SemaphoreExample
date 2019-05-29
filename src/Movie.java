import java.util.concurrent.Semaphore;

/**
 * This Contain All Shared Variables and Methods To Manipulate Them
 */
public class Movie {
	public static  int numVisitors; //Number Of Visitors
	public static  int theatherCapacity = 5; //Theater Capacity. Default is 5
	public static int party_ticket = 3; //The Amount of Party Tickets To Give. Default is 3.
	
	public static  int showingsLeft = 3; //The Number of Shows Left. Default is 3;
	
	public static int totalShowings = showingsLeft; //Creates Copy Of ShowingLeft That Won't Be Decremented
	
	
	public static  Thread visitorArray[]; //Contains All Visitor Threads To Allow Them To Be Initialized
	
	private static boolean theaterFull = false;  //used to tell if theater is full 
	
	private static boolean museumOpen = false; //Flag to keep track of if the museum is still open
	

	private static final Semaphore lobbySemaphore = new Semaphore(theatherCapacity); //A Semaphore To Represent The Lobby When 
																					//The Theater Is Full
	

	private static final Semaphore giveTicketsSemaphore = new Semaphore(0); //Blocking Semaphore To Prevent Speaker From Giving Tickets
																			//Until The Appropriate Time
	
	private static final Semaphore speakerSemaphore = new Semaphore(0);		//Blocking Semaphore Used To Prevent Speaker From Releasing Threads Too Early

	
	private static final Semaphore seatCounterSemaphore = new Semaphore(1); //Mutex Semaphore used To Allow Only One Thread To Access The Available Seats
	
	private static final Semaphore watchingMovieSemaphore = new Semaphore(0); //Blocking Semaphore Too Keep Visitors In Theater Until Movie Is Over

	private static int availableSeats; //Used To Set Available Seats
	
	private static boolean gaveTickets = false;  //Flag to keep track of the speaker giving tickets
	
	
	public static boolean isTheaterFull() {
		return theaterFull;
	}
	public static void setTheaterFull(boolean theaterFull) {
		Movie.theaterFull = theaterFull;
	}
	public  static boolean isMuseumOpen() {
		return museumOpen;
	}
	public  static void setMuseumOpen(boolean museumOpen) {
		Movie.museumOpen = museumOpen;
	}
	

	public static Semaphore getLobbySemaphore() {
		return lobbySemaphore;
	}
	
	public static Semaphore getSpeakersemaphore() {
		return speakerSemaphore;
	}
	public static boolean getGaveTickets() {
		return gaveTickets;
	}
	public static void setGaveTickets(boolean gaveTickets) {
		Movie.gaveTickets = gaveTickets;
	}
	public static Semaphore getSeatCounterSemaphore() {
		return seatCounterSemaphore;
	}
	public static int getAvailableSeats() {
		return availableSeats;
	}
	public static void setAvailableSeats(int movieCapacity) {
		Movie.availableSeats = movieCapacity;
	}
	
	public static Semaphore getWatchingMovieSemaphore() {
		return watchingMovieSemaphore;
	}
	
	public static Semaphore getGiveTicketSemaphore() {
		return giveTicketsSemaphore;
	}
}


