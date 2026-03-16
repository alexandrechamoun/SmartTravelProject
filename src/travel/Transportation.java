// -------------------------------------------------------------
// Assignment 1
// Part: Transportation Class
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------
// Abstract class representing a transportation option.
// Cannot be instantiated directly — use Flight, Train, or Bus.
// Each subclass defines its own cost calculation formula.
// Transport IDs are auto-generated starting at TR3001.

/*
Abstract base class for all transportation types.
Holds shared attributes like company name, departure city, and arrival city.
Declares calculateCost(int numberOfDays) as abstract, forcing each subclass to implement its own pricing formula.
IDs start at TR3001.
 */

package travel;

abstract public class Transportation {
	
	// ================= ATTRIBUTES =================
	private String transportId;
	private String companyName;
	private String departureCity;
	private String arrivalCity;
	
	// Id counter shared by all Transportation objects.
    private static int nextId = 3001;  // Shared by all Trip objects

	
	// ================= CONSTRUCTORS =================
	// Default Constructor
	public Transportation() {
		this.transportId = "TR" + nextId++;
	};
	
	// Parameterized Constructor
	public Transportation(String companyName, String departureCity, String arrivalCity) {
		this.companyName = companyName;
		this.departureCity = departureCity;
		this.arrivalCity = arrivalCity;
		this.transportId = "TR" + nextId++;
	}
	
	// Copy Constructor
	public Transportation(Transportation otherTransportation) {
		this.companyName = otherTransportation.companyName;
		this.departureCity = otherTransportation.departureCity;
		this.arrivalCity = otherTransportation.arrivalCity;
		this.transportId = "TR" + nextId++;
	}
	
	// =========== GETTERS ===========
	public String getTransportId() {
		return transportId;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public String getDepartureCity() {
		return departureCity;
	}
	
	public String getArrivalCity() {
		return arrivalCity;
	}
	
	// ============ SETTERS ============
	public void setTransportId(String transportId) {
		this.transportId = transportId;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}
	
	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}
	
	// ========== CALCULATE COST METHOD ==========
	// Abstract method (unused). This is for the child classes.
	abstract public double calculateCost(int numberOfDays);
	
	
	// ============= TO STRING METHOD ==============
	@Override
	public String toString() {
		return "Transport ID: " + transportId + "\n" + "Company: " + companyName + "\n" + "Departure: " + departureCity + "\n" + "Arrival: " + arrivalCity;
	}
	
	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		// If the object being compared is null, it cannot be equal to anything.
		if (obj == null) return false;

		// If the two objects are not the exact same class (e.g. comparing a Client to a Trip),
		if (getClass() != obj.getClass()) return false;

		// At this point we know obj is not null and is a Transportation.
		// We cast obj from type Object to type Transportation so we can
		// access Transportation-specific fields.
	    Transportation other = (Transportation) obj;
	    
	    return companyName.equals(other.companyName) &&
	           departureCity.equals(other.departureCity) &&
	           arrivalCity.equals(other.arrivalCity);
	}
	
}
