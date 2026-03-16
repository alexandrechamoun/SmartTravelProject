// -------------------------------------------------------------
// Assignment 1
// Part: Trip Class
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------
// Represents a trip managed by SmartTravel.
// Each trip is linked to one client and optionally includes
// one transportation option and one accommodation option.
// Trip IDs are auto-generated starting at T2001.

package travel;

// Imports
import client.Client;

public class Trip {
	
	// ================= ATTRIBUTES =================
	private String tripId;
	private String destination;
	private int numOfDays;
	private double basePrice;
	private Client client;
	private Transportation transportation;
	private Accommodation accommodation;
	
	// Id counter shared by all Trip objects.
	private static int nextId = 2001; 

	
	// ================= CONSTRUCTORS =================
	// Default Constructor:
	public Trip() {
		this.tripId = "T" + nextId++;

	}
	
	// Parameterized Constructor:
	public Trip(String destination, int numOfDays, double basePrice, Client client) {
		this.tripId = "T" + nextId++;
		this.destination = destination;
		this.numOfDays = numOfDays;
		this.basePrice = basePrice;
		this.client = client;
	}
	
	// Copy Constructor:
	public Trip(Trip otherTrip) {
		// Regular copy constructor
		this.tripId = "T" + nextId++;
		this.destination = otherTrip.destination;
		this.numOfDays = otherTrip.numOfDays;
		this.basePrice = otherTrip.basePrice;
		
		// Deep copy of client attribute:
		this.client = (otherTrip.client != null) ? new Client(otherTrip.client) : null;
		
		// Deep copy of Transportation. Since abstract, we cannot instantiate it directly but through its subclasses.
	    if (otherTrip.transportation instanceof Flight)
	        this.transportation = new Flight((Flight) otherTrip.transportation);
	    else if (otherTrip.transportation instanceof Train)
	        this.transportation = new Train((Train) otherTrip.transportation);
	    else if (otherTrip.transportation instanceof Bus)
	        this.transportation = new Bus((Bus) otherTrip.transportation);
	    else
	        this.transportation = null;
	 
	    // Deep copy Accommodation. Since abstract, we cannot instantiate it directly but through its subclasses.
	    if (otherTrip.accommodation instanceof Hotel)
	        this.accommodation = new Hotel((Hotel) otherTrip.accommodation);
	    else if (otherTrip.accommodation instanceof Hostel)
	        this.accommodation = new Hostel((Hostel) otherTrip.accommodation);
	    else
	        this.accommodation = null;
   	}
	
	// ============== GETTERS ============
	public String getTripId() {
		return tripId;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public int getDurationInDays() {
		return numOfDays;
	}
	
	public double getBasePrice() {
		return basePrice;
	}
	
	public Client getClient() {
		return client;
	}
	
	public Transportation getTransportation(){ 
		return transportation; 
	}
	
	public Accommodation getAccommodation(){ 
		return accommodation; 
	}
	
	// ============ SETTERS ===========
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setDurationInDays(int numOfDays) {
		if (numOfDays < 0) {
			this.numOfDays = 0;
		} else {
			this.numOfDays = numOfDays;
		}
	}
	
	public void setBasePrice(double basePrice) {
		if (basePrice < 0) {
			this.basePrice = 0;
		} else {
			this.basePrice = basePrice;
		}
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void setTransportation(Transportation transportation) {
		this.transportation = transportation; 
	}
	
	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation; 
	}
	
	
	// 	========== GET TRIP TYPE METHOD ===========
	public String getTripType() {
		if (transportation instanceof Flight) return "Flight Trip";
	    if (transportation instanceof Train)  return "Train Trip";
	    if (transportation instanceof Bus)    return "Bus Trip";
	    return "No Transport";
	}
	
	// 	========== CALCULATE TOTAL COST METHOD ===========
	public double calculateTotalCost() {
		double total = basePrice;
	    if (transportation != null)
	        total += transportation.calculateCost(numOfDays);
	    if (accommodation != null)
	        total += accommodation.calculateCost(numOfDays);
	    return total;
	}
	
	// 	========== TO STRING METHOD ===========
	@Override
	public String toString() {
	    return "Trip ID: " + tripId + "\n" 
	    		+"Destination: " + destination 
	    		+ "\n" + "Days: " + numOfDays 
	    		+ "\n" + "Base Price: $" + basePrice 
	    		+ "\n" + "Total Cost: $" + calculateTotalCost() 
	    		+ "\n" + "Type: " + getTripType() 
	    		+ "\n  Client: " + (client != null ? client.toString() : "None") 
	    		+ "\n  Transportation: " + (transportation != null ? transportation.toString() : "None") 
	    		+ "\n  Accommodation: " + (accommodation != null ? accommodation.toString() : "None");
	}

	
	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (getClass() != obj.getClass()) return false;
	    
	    Trip other = (Trip) obj;
	    
	    if (!destination.equals(other.destination)) return false;
	    if (numOfDays != other.numOfDays) return false;
	    if (Double.compare(basePrice, other.basePrice) != 0) return false;
	    if (client == null && other.client != null) return false;
	    if (client != null && !client.equals(other.client)) return false;
	    if (transportation == null && other.transportation != null) return false;
	    if (transportation != null && !transportation.equals(other.transportation)) return false;
	    if (accommodation == null && other.accommodation != null) return false;
	    if (accommodation != null && !accommodation.equals(other.accommodation)) return false;
	    return true;
	}
}
