// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a bus transportation option.
// Cost = baseFare + ($5 x number of stops).
// A2: numOfStops must be >= 1.
// A3 changes:
//   - getBaseFare() implemented (required by abstract method in Transportation for compareTo)
//   - toCsvRow() implemented (required by CsvPersistable via Transportation)
//   - static fromCsvRow() factory method added

package travel;

import exceptions.InvalidTransportDataException;

public class Bus extends Transportation {

	// ================= ATTRIBUTES =================
	// Unchanged from A2
	private String busCompany;
	private int    numOfStops;
	private double baseFare;


	// ================= CONSTRUCTORS =================
	// Unchanged from A2

	public Bus() {}

	public Bus(String companyName, String departureCity, String arrivalCity,
			   String busCompany, int numOfStops, double baseFare)
			throws InvalidTransportDataException {
		super(companyName, departureCity, arrivalCity);
		validateStops(numOfStops);
		this.busCompany = busCompany;
		this.numOfStops = numOfStops;
		this.baseFare   = baseFare;
	}

	public Bus(Bus otherBus) {
		super(otherBus);
		this.busCompany = otherBus.busCompany;
		this.numOfStops = otherBus.numOfStops;
		this.baseFare   = otherBus.baseFare;
	}


	// ================= VALIDATION =================
	// Unchanged from A2
	private static void validateStops(int stops) throws InvalidTransportDataException {
		if (stops < 1)
			throw new InvalidTransportDataException(
					"Bus must have at least 1 stop (got " + stops + ").");
	}


	// ================= GETTERS =================
	// Unchanged from A2
	public String getBusCompany() { return busCompany; }
	public int    getNumOfStops() { return numOfStops; }

	// A3: new — implements the abstract getBaseFare() declared in Transportation
	// Needed so Transportation.compareTo() can compare fares without casting to Bus
	@Override
	public double getBaseFare() { return baseFare; }


	// ================= SETTERS =================
	// Unchanged from A2
	public void setBusCompany(String busCompany) { this.busCompany = busCompany; }

	public void setNumOfStops(int numOfStops) throws InvalidTransportDataException {
		validateStops(numOfStops);
		this.numOfStops = numOfStops;
	}

	public void setBaseFare(double baseFare) { this.baseFare = baseFare; }


	// ================= CALCULATE COST =================
	// Unchanged from A2
	@Override
	public double calculateCost(int numberOfDays) {
		return baseFare + (5 * numOfStops);
	}


	// ================= A3: CsvPersistable =================

	// A3: new — implements abstract toCsvRow() from Transportation
	// Format matches A2 TransportFileManager output:
	// BUS;TransportID;companyName;departureCity;arrivalCity;baseFare;numOfStops
	@Override
	public String toCsvRow() {
		return "BUS;" + getTransportId() + ";" + getCompanyName() + ";"
				+ getDepartureCity() + ";" + getArrivalCity() + ";"
				+ baseFare + ";" + numOfStops;
	}

	// A3: new — reconstructs a Bus from one CSV line
	// Called by Transportation.fromCsvRow() when the type prefix is "BUS"
	public static Bus fromCsvRow(String csvLine) throws InvalidTransportDataException {
		String[] parts = csvLine.split(";");
		if (parts.length < 7)
			throw new InvalidTransportDataException("Invalid BUS CSV row: " + csvLine);
		String id      = parts[1].trim();
		String company = parts[2].trim();
		String dep     = parts[3].trim();
		String arr     = parts[4].trim();
		double fare    = Double.parseDouble(parts[5].trim());
		int    stops   = Integer.parseInt(parts[6].trim());
		Bus b = new Bus(company, dep, arr, company, stops, fare);
		b.setTransportId(id); // restore original ID from file
		return b;
	}


	// ================= TO STRING / EQUALS =================
	// Unchanged from A2

	@Override
	public String toString() {
		return super.toString()
				+ "\nBus Company: " + busCompany
				+ "\nStops: "       + numOfStops
				+ "\nBase Fare: $"  + baseFare;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Bus other = (Bus) obj;
		return busCompany.equals(other.busCompany)
				&& numOfStops == other.numOfStops
				&& Double.compare(baseFare, other.baseFare) == 0;
	}
}