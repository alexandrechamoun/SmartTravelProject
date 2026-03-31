// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a flight transportation option.
// Cost = baseFare + $10 per kg of luggage exceeding 20kg.
// A2: luggageAllowance must be >= 0.
// A3 changes:
//   - getBaseFare() implemented (required by abstract method in Transportation for compareTo)
//   - toCsvRow() implemented (required by CsvPersistable via Transportation)
//   - static fromCsvRow() factory method added

package travel;

import exceptions.InvalidTransportDataException;

public class Flight extends Transportation {

	// ================= ATTRIBUTES =================
	// Unchanged from A2
	private String airlineName;
	private double luggageAllowance;
	private double baseFare;


	// ================= CONSTRUCTORS =================
	// Unchanged from A2

	public Flight() {}

	public Flight(String companyName, String departureCity, String arrivalCity,
				  String airlineName, double luggageAllowance, double baseFare)
			throws InvalidTransportDataException {
		super(companyName, departureCity, arrivalCity);
		validateLuggage(luggageAllowance);
		this.airlineName      = airlineName;
		this.luggageAllowance = luggageAllowance;
		this.baseFare         = baseFare;
	}

	public Flight(Flight otherFlight) {
		super(otherFlight);
		this.airlineName      = otherFlight.airlineName;
		this.luggageAllowance = otherFlight.luggageAllowance;
		this.baseFare         = otherFlight.baseFare;
	}


	// ================= VALIDATION =================
	// Unchanged from A2
	private static void validateLuggage(double luggage) throws InvalidTransportDataException {
		if (luggage < 0)
			throw new InvalidTransportDataException(
					"Luggage allowance cannot be negative (got " + luggage + "kg).");
	}


	// ================= GETTERS =================
	// Unchanged from A2
	public String getAirlineName()      { return airlineName; }
	public double getLuggageAllowance() { return luggageAllowance; }

	// A3: new — implements the abstract getBaseFare() declared in Transportation
	// Needed so Transportation.compareTo() can compare fares without casting to Flight
	@Override
	public double getBaseFare() { return baseFare; }


	// ================= SETTERS =================
	// Unchanged from A2
	public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

	public void setLuggageAllowance(double luggageAllowance) throws InvalidTransportDataException {
		validateLuggage(luggageAllowance);
		this.luggageAllowance = luggageAllowance;
	}

	public void setBaseFare(double baseFare) { this.baseFare = baseFare; }


	// ================= CALCULATE COST =================
	// Unchanged from A2
	@Override
	public double calculateCost(int numberOfDays) {
		double cost = baseFare;
		if (luggageAllowance > 20)
			cost += (luggageAllowance - 20) * 10;
		return cost;
	}


	// ================= A3: CsvPersistable =================

	// A3: new — implements abstract toCsvRow() from Transportation
	// Format matches A2 TransportFileManager output:
	// FLIGHT;TransportID;companyName;departureCity;arrivalCity;baseFare;luggageAllowance
	@Override
	public String toCsvRow() {
		return "FLIGHT;" + getTransportId() + ";" + getCompanyName() + ";"
				+ getDepartureCity() + ";" + getArrivalCity() + ";"
				+ baseFare + ";" + luggageAllowance;
	}

	// A3: new — reconstructs a Flight from one CSV line
	// Called by Transportation.fromCsvRow() when the type prefix is "FLIGHT"
	public static Flight fromCsvRow(String csvLine) throws InvalidTransportDataException {
		String[] parts = csvLine.split(";");
		if (parts.length < 7)
			throw new InvalidTransportDataException("Invalid FLIGHT CSV row: " + csvLine);
		String id      = parts[1].trim();
		String company = parts[2].trim();
		String dep     = parts[3].trim();
		String arr     = parts[4].trim();
		double fare    = Double.parseDouble(parts[5].trim());
		double luggage = Double.parseDouble(parts[6].trim());
		Flight f = new Flight(company, dep, arr, company, luggage, fare);
		f.setTransportId(id); // restore original ID from file
		return f;
	}


	// ================= TO STRING / EQUALS =================
	// Unchanged from A2

	@Override
	public String toString() {
		return super.toString()
				+ "\nAirline: "    + airlineName
				+ "\nLuggage: "    + luggageAllowance + "kg"
				+ "\nBase Fare: $" + baseFare;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Flight other = (Flight) obj;
		return airlineName.equals(other.airlineName)
				&& Double.compare(luggageAllowance, other.luggageAllowance) == 0
				&& Double.compare(baseFare, other.baseFare) == 0;
	}
}