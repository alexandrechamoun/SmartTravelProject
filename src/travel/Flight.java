// -------------------------------------------------------------
// Assignment 1
// Part: Flight Class (Child class of Transportation class)
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------
// Represents a flight transportation option.
// Cost = baseFare + $10 per kg of luggage exceeding 20kg.
/*
Extends Transportation. Adds airline name, luggage allowance, and base fare. Pricing rule: baseFare + $10 per kg over 20kg.
 */


package travel;

public class Flight extends Transportation{
	
	// ================= ATTRIBUTES =================
	private String airlineName;
	private double luggageAllowance;
	private double baseFare;
	
	// ================= CONSTRUCTORS ===============
	// Default Constructor
	public Flight() {};
	
	// Parameterized Constructor
	public Flight(String companyName, String departureCity, String arrivalCity, String airlineName, double luggageAllowance, double baseFare) {
		super(companyName, departureCity, arrivalCity);
		this.airlineName = airlineName;
		this.luggageAllowance = luggageAllowance;
		this.baseFare = baseFare;
	}
	
	// Copy Constructor
	public Flight(Flight otherFlight) {
		super(otherFlight);
		this.airlineName = otherFlight.airlineName;
		this.luggageAllowance = otherFlight.luggageAllowance;
		this.baseFare = otherFlight.baseFare;
	}
	
	// =============== GETTERS =============
	public String getAirlineName() {
		return airlineName;
	}
	
	public double getLuggageAllowance() {
		return luggageAllowance;
	}
	
	public double getBaseFare() {
		return baseFare;
	}
	
	// =============== SETTERS ==============
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	
	public void setLuggageAllowance(double luggageAllowance) {
		if (luggageAllowance < 0) {
			this.luggageAllowance = 0;
		} else {
			this.luggageAllowance = luggageAllowance;
		}
	}
	
	public void setBaseFare(double baseFare) {
		this.baseFare = baseFare;
	}
	
	
	// ========== CALCULATE COST METHOD ==========
	@Override
	public double calculateCost(int numberOfDays) {
		// Start with the base fare as the minimum cost
		double cost = baseFare;

		// Check if luggage exceeds the 20kg free allowance.
		// If it does, charge $10 for every kg above 20.
		// Example: 25kg luggage → (25-20) x $10 = $50 surcharge
	    if (luggageAllowance > 20)
	        cost += (luggageAllowance - 20) * 10;

		// numberOfDays is not used because flight cost
		// does not depend on trip duration
	    return cost;
	}
	
	
	// ============= TO STRING METHOD ===============
	@Override
	public String toString() {
		return super.toString() + "\n" + "Airline: " + airlineName + "\n" + "Luggage: " + luggageAllowance + "\n" + "Base Fare: $" + baseFare;
	}
	
	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		// Instead of repeating the null check, class check, and cast ourselves,
		// we call the parent class (Transportation) equals() method first.
		// It handles all of that for us.
		// If the Transportation fields are not equal, there is no point checking Flight-specific fields, so we return false immediately.
	    if (!super.equals(obj)) return false;
	    
	    Flight other = (Flight) obj;
	    
	    return airlineName.equals(other.airlineName) &&
	           Double.compare(luggageAllowance, other.luggageAllowance) == 0 &&
	           Double.compare(baseFare, other.baseFare) == 0;
	}

}
