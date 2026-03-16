// -------------------------------------------------------------
// Assignment 1
// Part: Bus Class (Child class of Transportation Class)
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------
// Represents a bus transportation option.
// Cost = baseFare + ($5 x number of stops).

package travel;

public class Bus extends Transportation{
	
	// ================= ATTRIBUTES =================
	private String busCompany;
	private int numOfStops;
	private double baseFare;

	
	// ================= CONSTRUCTORS =================
	// Default Constructor
	public Bus() {};
	
	// Parameterized Constructor
	public Bus(String companyName, String departureCity, String arrivalCity, String busCompany, int numOfStops, double baseFare) {
		super(companyName, departureCity, arrivalCity);
		this.busCompany = busCompany;
		this.numOfStops = numOfStops;
		this.baseFare = baseFare;
	}
	
	// Copy Constructor
	public Bus(Bus otherBus) {
		super(otherBus);
		this.busCompany = otherBus.busCompany;
		this.numOfStops = otherBus.numOfStops;
		this.baseFare = otherBus.baseFare;
	}
	
	// ============= GETTERS ==============
	public String getBusCompany() {
		return busCompany;
	}
	
	public int getNumOfStops() {
		return numOfStops;
	}
	
	public double getBaseFare() {
		return baseFare;
	}
	
	// ============ SETTERS ============
	public void setBusCompany(String busCompany) {
		this.busCompany = busCompany;
	}
	
	public void setNumOfStops(int numOfStops) {
		if (numOfStops < 0) {
			this.numOfStops = 0;
		} else {
			this.numOfStops = numOfStops;			
		}
	}
	
	public void setBaseFare(double baseFare) {
		this.baseFare = baseFare;
	}
	
	// ========== CALCULATE COST METHOD ==========
	@Override
	public double calculateCost(int numberOfDays) {
	    return baseFare + (5 * numOfStops);
	}
	
	// ========== TO STRING METHOD ============
	@Override
	public String toString() {
		return super.toString() + "\n" + "Bus Company: " + busCompany + "\n" + "Stops: " + numOfStops + "\n" + "Base Fare: $" + baseFare;
	}

	
	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
	    if (!super.equals(obj)) return false;
	    
	    Bus other = (Bus) obj;
	    
	    return busCompany.equals(other.busCompany) &&
	           numOfStops == other.numOfStops &&
	           Double.compare(baseFare, other.baseFare) == 0;
	}
	
}
