// -------------------------------------------------------------
// Assignment 2
// Written by: Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a flight transportation option.
// Cost = baseFare + $10 per kg of luggage exceeding 20kg.
// A2 additions: luggageAllowance must be >= 0 (InvalidTransportDataException).

package travel;

import exceptions.InvalidTransportDataException;

public class Flight extends Transportation {

	// ================= ATTRIBUTES =================
	private String airlineName;
	private double luggageAllowance;
	private double baseFare;


	// ================= CONSTRUCTORS ===============

	// Default Constructor
	public Flight() {}

	// Parameterized Constructor
	public Flight(String companyName, String departureCity, String arrivalCity,
				  String airlineName, double luggageAllowance, double baseFare)
			throws InvalidTransportDataException {
		super(companyName, departureCity, arrivalCity);
		validateLuggage(luggageAllowance);
		this.airlineName      = airlineName;
		this.luggageAllowance = luggageAllowance;
		this.baseFare         = baseFare;
	}

	// Copy Constructor
	public Flight(Flight otherFlight) {
		super(otherFlight);
		this.airlineName      = otherFlight.airlineName;
		this.luggageAllowance = otherFlight.luggageAllowance;
		this.baseFare         = otherFlight.baseFare;
	}


	// ================= VALIDATION =================
	private static void validateLuggage(double luggage) throws InvalidTransportDataException {
		if (luggage < 0)
			throw new InvalidTransportDataException(
					"Luggage allowance cannot be negative (got " + luggage + "kg).");
	}


	// =============== GETTERS =============
	public String getAirlineName()     { return airlineName; }
	public double getLuggageAllowance(){ return luggageAllowance; }
	public double getBaseFare()        { return baseFare; }


	// =============== SETTERS ==============
	public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

	public void setLuggageAllowance(double luggageAllowance) throws InvalidTransportDataException {
		validateLuggage(luggageAllowance);
		this.luggageAllowance = luggageAllowance;
	}

	public void setBaseFare(double baseFare) { this.baseFare = baseFare; }


	// ========== CALCULATE COST METHOD ==========
	@Override
	public double calculateCost(int numberOfDays) {
		double cost = baseFare;
		if (luggageAllowance > 20)
			cost += (luggageAllowance - 20) * 10;
		return cost;
	}


	// ============= TO STRING METHOD ===============
	@Override
	public String toString() {
		return super.toString()
				+ "\nAirline: " + airlineName
				+ "\nLuggage: " + luggageAllowance + "kg"
				+ "\nBase Fare: $" + baseFare;
	}


	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Flight other = (Flight) obj;
		return airlineName.equals(other.airlineName)
				&& Double.compare(luggageAllowance, other.luggageAllowance) == 0
				&& Double.compare(baseFare, other.baseFare) == 0;
	}
}