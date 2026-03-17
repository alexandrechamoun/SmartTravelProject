// -------------------------------------------------------------
// Assignment 2
// Written by: Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a bus transportation option.
// Cost = baseFare + ($5 x number of stops).
// A2 additions: numOfStops must be >= 1 (business rule for Bus)
// enforced via InvalidTransportDataException.

package travel;

import exceptions.InvalidTransportDataException;

public class Bus extends Transportation {

	// ================= ATTRIBUTES =================
	private String busCompany;
	private int    numOfStops;
	private double baseFare;


	// ================= CONSTRUCTORS =================

	// Default Constructor
	public Bus() {}

	// Parameterized Constructor
	public Bus(String companyName, String departureCity, String arrivalCity,
			   String busCompany, int numOfStops, double baseFare)
			throws InvalidTransportDataException {
		super(companyName, departureCity, arrivalCity);
		validateStops(numOfStops);
		this.busCompany = busCompany;
		this.numOfStops = numOfStops;
		this.baseFare   = baseFare;
	}

	// Copy Constructor
	public Bus(Bus otherBus) {
		super(otherBus);
		this.busCompany = otherBus.busCompany;
		this.numOfStops = otherBus.numOfStops;
		this.baseFare   = otherBus.baseFare;
	}


	// ================= VALIDATION =================
	private static void validateStops(int stops) throws InvalidTransportDataException {
		if (stops < 1)
			throw new InvalidTransportDataException(
					"Bus must have at least 1 stop (got " + stops + ").");
	}


	// ============= GETTERS ==============
	public String getBusCompany() { return busCompany; }
	public int    getNumOfStops() { return numOfStops; }
	public double getBaseFare()   { return baseFare; }


	// ============ SETTERS ============
	public void setBusCompany(String busCompany) { this.busCompany = busCompany; }

	public void setNumOfStops(int numOfStops) throws InvalidTransportDataException {
		validateStops(numOfStops);
		this.numOfStops = numOfStops;
	}

	public void setBaseFare(double baseFare) { this.baseFare = baseFare; }


	// ========== CALCULATE COST METHOD ==========
	@Override
	public double calculateCost(int numberOfDays) {
		return baseFare + (5 * numOfStops);
	}


	// ========== TO STRING METHOD ============
	@Override
	public String toString() {
		return super.toString()
				+ "\nBus Company: " + busCompany
				+ "\nStops: " + numOfStops
				+ "\nBase Fare: $" + baseFare;
	}


	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Bus other = (Bus) obj;
		return busCompany.equals(other.busCompany)
				&& numOfStops == other.numOfStops
				&& Double.compare(baseFare, other.baseFare) == 0;
	}
}