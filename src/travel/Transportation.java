// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Abstract class representing a transportation option.
// A2: company name, departure, and arrival city must be non-empty.
// A3 changes:
//   - implements Identifiable:              getId() added
//   - implements CsvPersistable:            toCsvRow() declared abstract (each subclass
//                                           has a different CSV format), fromCsvRow()
//                                           static dispatcher added
//   - implements Comparable<Transportation>: compareTo() added (descending by base fare)
//   - abstract getBaseFare() added so compareTo() can access fare from any subclass

package travel;

import exceptions.InvalidTransportDataException;
import interfaces.Identifiable;    // A3: new import
import interfaces.CsvPersistable;  // A3: new import

// A3: added "implements Identifiable, CsvPersistable, Comparable<Transportation>"
// A2: abstract public class Transportation {
abstract public class Transportation
		implements Identifiable, CsvPersistable, Comparable<Transportation> {

	// ================= ATTRIBUTES =================
	// Unchanged from A2
	private String transportId;
	private String companyName;
	private String departureCity;
	private String arrivalCity;

	private static int nextId = 3001;


	// ================= VALIDATION =================
	// Unchanged from A2
	protected static void validateNonEmpty(String value, String fieldName)
			throws InvalidTransportDataException {
		if (value == null || value.trim().isEmpty())
			throw new InvalidTransportDataException(fieldName + " cannot be empty.");
	}


	// ================= CONSTRUCTORS =================
	// Unchanged from A2

	public Transportation() {
		this.transportId = "TR" + nextId++;
	}

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

	public Transportation(Transportation otherTransportation) {
		this.companyName   = otherTransportation.companyName;
		this.departureCity = otherTransportation.departureCity;
		this.arrivalCity   = otherTransportation.arrivalCity;
		this.transportId   = "TR" + nextId++;
	}


	// ================= GETTERS =================
	// Unchanged from A2
	public String getTransportId()   { return transportId; }
	public String getCompanyName()   { return companyName; }
	public String getDepartureCity() { return departureCity; }
	public String getArrivalCity()   { return arrivalCity; }

	// A3: new — satisfies the Identifiable interface
	@Override
	public String getId() { return transportId; }


	// ================= SETTERS =================
	// Unchanged from A2
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


	// ================= ABSTRACT METHODS =================

	// Unchanged from A2
	public abstract double calculateCost(int numberOfDays);

	// A3: new abstract — each subclass (Flight/Train/Bus) overrides with its own CSV format
	// Declared abstract here because Transportation itself doesn't know the subclass fields
	@Override
	public abstract String toCsvRow();

	// A3: new abstract — each subclass exposes its base fare so compareTo() works generically
	// without needing instanceof checks
	public abstract double getBaseFare();


	// ================= A3: CsvPersistable — fromCsvRow dispatcher =================

	// A3: new — static factory that reads the type prefix and delegates to the right subclass
	// Format: FLIGHT;... or TRAIN;... or BUS;...
	// This mirrors how A2's TransportFileManager dispatched on the type prefix when loading
	public static Transportation fromCsvRow(String csvLine)
			throws InvalidTransportDataException {
		String[] parts = csvLine.split(";");
		if (parts.length < 1)
			throw new InvalidTransportDataException("Empty transport CSV row.");
		String type = parts[0].trim().toUpperCase();
		switch (type) {
			case "FLIGHT": return Flight.fromCsvRow(csvLine);
			case "TRAIN":  return Train.fromCsvRow(csvLine);
			case "BUS":    return Bus.fromCsvRow(csvLine);
			default: throw new InvalidTransportDataException(
					"Unknown transportation type: " + type);
		}
	}


	// ================= A3: Comparable<Transportation> =================

	// A3: new — defines natural ordering for Transportation
	// Business rule: premium (higher fare) transport comes first
	// Reversed comparison (other vs this) makes Collections.sort() produce descending order
	// getBaseFare() is abstract so this works for Flight, Train, and Bus without casting
	@Override
	public int compareTo(Transportation other) {
		return Double.compare(other.getBaseFare(), this.getBaseFare());
	}


	// ================= TO STRING / EQUALS =================
	// Unchanged from A2

	@Override
	public String toString() {
		return "Transport ID: " + transportId
				+ "\nCompany: "    + companyName
				+ "\nDeparture: "  + departureCity
				+ "\nArrival: "    + arrivalCity;
	}

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