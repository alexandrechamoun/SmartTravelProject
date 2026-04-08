// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Abstract class representing an accommodation option.
// A2: pricePerNight must be > $0.
// A3 changes:
//   - implements Identifiable:               getId() added
//   - implements CsvPersistable:             toCsvRow() declared abstract (Hotel and Hostel
//                                            have different CSV formats), fromCsvRow()
//                                            static dispatcher added
//   - implements Comparable<Accommodation>:  compareTo() added (descending by pricePerNight)

package travel;

import exceptions.InvalidAccommodationDataException;
import interfaces.Identifiable;    // A3: new import
import interfaces.CsvPersistable;  // A3: new import

// A3: added "implements Identifiable, CsvPersistable, Comparable<Accommodation>"
// A2: abstract public class Accommodation {
abstract public class Accommodation
		implements Identifiable, CsvPersistable, Comparable<Accommodation> {

	// ================= ATTRIBUTES =================
	// Unchanged from A2
	private String accommodationId;
	private String name;
	private String location;
	private double pricePerNight;

	private static int nextId = 4001;


	// ================= VALIDATION =================
	// Unchanged from A2
	protected static void validatePricePerNight(double price) throws InvalidAccommodationDataException {
		if (price <= 0)
			throw new InvalidAccommodationDataException(
					"Price per night must be greater than $0 (got $" + price + ").");
	}


	// ================= CONSTRUCTORS =================
	// Unchanged from A2

	public Accommodation() {
		this.accommodationId = "A" + nextId++;
	}

	public Accommodation(String name, String location, double pricePerNight)
			throws InvalidAccommodationDataException {
		validatePricePerNight(pricePerNight);
		this.name            = name;
		this.location        = location;
		this.pricePerNight   = pricePerNight;
		this.accommodationId = "A" + nextId++;
	}

	public Accommodation(Accommodation otherAccommodation) {
		this.name            = otherAccommodation.name;
		this.location        = otherAccommodation.location;
		this.pricePerNight   = otherAccommodation.pricePerNight;
		this.accommodationId = "A" + nextId++;
	}


	// ================= GETTERS =================
	// Unchanged from A2
	public String getAccommodationId() { return accommodationId; }
	public String getName()            { return name; }
	public String getLocation()        { return location; }
	public double getPricePerNight()   { return pricePerNight; }

	// A3: new — satisfies the Identifiable interface
	@Override
	public String getId() { return accommodationId; }


	// ================= SETTERS =================
	// Unchanged from A2
	public void setAccommodationId(String accommodationId) { this.accommodationId = accommodationId; }
	public void setName(String name)         { this.name = name; }
	public void setLocation(String location) { this.location = location; }

	public void setPricePerNight(double pricePerNight) throws InvalidAccommodationDataException {
		validatePricePerNight(pricePerNight);
		this.pricePerNight = pricePerNight;
	}


	// ================= ABSTRACT METHODS =================

	// Unchanged from A2
	public abstract double calculateCost(int numberOfDays);

	// A3: new abstract — each subclass (Hotel/Hostel) overrides with its own CSV format
	// Declared abstract here because Accommodation itself doesn't know the subclass fields
	@Override
	public abstract String toCsvRow();


	// ================= A3: CsvPersistable — fromCsvRow dispatcher =================

	// A3: new — static factory that reads the type prefix and delegates to Hotel or Hostel
	// Format: HOTEL;... or HOSTEL;...
	// This mirrors how A2's AccommodationFileManager dispatched on the type prefix when loading
	public static Accommodation fromCsvRow(String csvLine)
			throws InvalidAccommodationDataException {
		String[] parts = csvLine.split(";");
		if (parts.length < 1)
			throw new InvalidAccommodationDataException("Empty accommodation CSV row.");
		String type = parts[0].trim().toUpperCase();
		switch (type) {
			case "HOTEL":  return Hotel.fromCsvRow(csvLine);
			case "HOSTEL": return Hostel.fromCsvRow(csvLine);
			default: throw new InvalidAccommodationDataException(
					"Unknown accommodation type: " + type);
		}
	}


	// ================= A3: Comparable<Accommodation> =================

	// A3: new — defines natural ordering for Accommodation
	// luxury (higher pricePerNight) options come first
	// Reversed comparison (other vs this) makes Collections.sort() produce descending order
	@Override
	public int compareTo(Accommodation other) {
		return Double.compare(other.pricePerNight, this.pricePerNight);
	}


	// ================= TO STRING / EQUALS =================
	// Unchanged from A2

	@Override
	public String toString() {
		return "Accommodation ID: " + accommodationId
				+ "\nName: "           + name
				+ "\nLocation: "       + location
				+ "\nPrice/Night: $"   + pricePerNight;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Accommodation other = (Accommodation) obj;
		return name.equals(other.name)
				&& location.equals(other.location)
				&& Double.compare(pricePerNight, other.pricePerNight) == 0;
	}
}
