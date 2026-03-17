// -------------------------------------------------------------
// Assignment 2
// Written by: Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Abstract class representing a transportation option.
// Cannot be instantiated directly — use Flight, Train, or Bus.
// A2 additions: company name, departure, and arrival city
// must be non-empty (InvalidTransportDataException).

package travel;

import exceptions.InvalidTransportDataException;

abstract public class Transportation {

	// ================= ATTRIBUTES =================
	private String transportId;
	private String companyName;
	private String departureCity;
	private String arrivalCity;

	// Id counter shared by all Transportation objects.
	private static int nextId = 3001;


	// ================= VALIDATION HELPERS =================

	protected static void validateNonEmpty(String value, String fieldName)
			throws InvalidTransportDataException {
		if (value == null || value.trim().isEmpty())
			throw new InvalidTransportDataException(fieldName + " cannot be empty.");
	}


	// ================= CONSTRUCTORS =================

	// Default Constructor
	public Transportation() {
		this.transportId = "TR" + nextId++;
	}

	// Parameterized Constructor
	public Transportation(String companyName, String departureCity, String arrivalCity)
			throws InvalidTransportDataException {
		validateNonEmpty(companyName,   "Company name");
		validateNonEmpty(departureCity, "Departure city");
		validateNonEmpty(arrivalCity,   "Arrival city");
		this.companyName   = companyName;
		this.departureCity = departureCity;
		this.arrivalCity   = arrivalCity;
		this.transportId   = "TR" + nextId++;
	}

	// Copy Constructor
	public Transportation(Transportation otherTransportation) {
		this.companyName   = otherTransportation.companyName;
		this.departureCity = otherTransportation.departureCity;
		this.arrivalCity   = otherTransportation.arrivalCity;
		this.transportId   = "TR" + nextId++;
	}


	// =========== GETTERS ===========
	public String getTransportId()   { return transportId; }
	public String getCompanyName()   { return companyName; }
	public String getDepartureCity() { return departureCity; }
	public String getArrivalCity()   { return arrivalCity; }


	// ============ SETTERS ============
	public void setTransportId(String transportId) { this.transportId = transportId; }

	public void setCompanyName(String companyName) throws InvalidTransportDataException {
		validateNonEmpty(companyName, "Company name");
		this.companyName = companyName;
	}

	public void setDepartureCity(String departureCity) throws InvalidTransportDataException {
		validateNonEmpty(departureCity, "Departure city");
		this.departureCity = departureCity;
	}

	public void setArrivalCity(String arrivalCity) throws InvalidTransportDataException {
		validateNonEmpty(arrivalCity, "Arrival city");
		this.arrivalCity = arrivalCity;
	}


	// ========== CALCULATE COST METHOD ==========
	abstract public double calculateCost(int numberOfDays);


	// ============= TO STRING METHOD ==============
	@Override
	public String toString() {
		return "Transport ID: " + transportId
				+ "\nCompany: " + companyName
				+ "\nDeparture: " + departureCity
				+ "\nArrival: " + arrivalCity;
	}


	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Transportation other = (Transportation) obj;
		return companyName.equals(other.companyName)
				&& departureCity.equals(other.departureCity)
				&& arrivalCity.equals(other.arrivalCity);
	}
}