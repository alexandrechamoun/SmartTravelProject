// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a hostel accommodation option.
// Cost = pricePerNight x numberOfDays x 0.85 (15% discount).
// A2: price per night must be <= $150, beds >= 1.
// A3 changes:
//   - toCsvRow() implemented (required by CsvPersistable via Accommodation)
//   - static fromCsvRow() factory method added

package travel;

import exceptions.InvalidAccommodationDataException;

public class Hostel extends Accommodation {

	// ================= ATTRIBUTES =================
	// Unchanged from A2
	private int numOfSharedBedsPerRoom;
	private static final double MAX_HOSTEL_PRICE = 150.0;


	// ================= CONSTRUCTORS =================
	// Unchanged from A2

	public Hostel() {}

	public Hostel(String name, String location, double pricePerNight, int numOfSharedBedsPerRoom)
			throws InvalidAccommodationDataException {
		super(name, location, pricePerNight);
		validateHostelPrice(pricePerNight);
		validateBeds(numOfSharedBedsPerRoom);
		this.numOfSharedBedsPerRoom = numOfSharedBedsPerRoom;
	}

	public Hostel(Hostel otherHostel) {
		super(otherHostel);
		this.numOfSharedBedsPerRoom = otherHostel.numOfSharedBedsPerRoom;
	}


	// ================= VALIDATION =================
	// Unchanged from A2
	private static void validateHostelPrice(double price) throws InvalidAccommodationDataException {
		if (price > MAX_HOSTEL_PRICE)
			throw new InvalidAccommodationDataException(
					"Hostel price cannot exceed $" + MAX_HOSTEL_PRICE + "/night (got $" + price + ").");
	}

	private static void validateBeds(int beds) throws InvalidAccommodationDataException {
		if (beds < 1)
			throw new InvalidAccommodationDataException(
					"Number of shared beds per room must be at least 1 (got " + beds + ").");
	}


	// ================= GETTERS =================
	// Unchanged from A2
	public int getNumOfSharedBedsPerRoom() { return numOfSharedBedsPerRoom; }


	// ================= SETTERS =================
	// Unchanged from A2
	public void setNumOfSharedBedsPerRoom(int numOfSharedBedsPerRoom)
			throws InvalidAccommodationDataException {
		validateBeds(numOfSharedBedsPerRoom);
		this.numOfSharedBedsPerRoom = numOfSharedBedsPerRoom;
	}

	@Override
	public void setPricePerNight(double pricePerNight) throws InvalidAccommodationDataException {
		super.setPricePerNight(pricePerNight);
		validateHostelPrice(pricePerNight);
	}


	// ================= CALCULATE COST =================
	// Unchanged from A2
	@Override
	public double calculateCost(int numberOfDays) {
		return getPricePerNight() * numberOfDays * 0.85;
	}


	// ================= A3: CsvPersistable =================

	// A3: new — implements abstract toCsvRow() from Accommodation
	// Format matches A2 AccommodationFileManager output:
	// HOSTEL;AccommodationID;name;location;pricePerNight;numOfSharedBedsPerRoom
	@Override
	public String toCsvRow() {
		return "HOSTEL;" + getAccommodationId() + ";" + getName() + ";"
				+ getLocation() + ";" + getPricePerNight() + ";" + numOfSharedBedsPerRoom;
	}

	// A3: new — reconstructs a Hostel from one CSV line
	// Called by Accommodation.fromCsvRow() when the type prefix is "HOSTEL"
	public static Hostel fromCsvRow(String csvLine) throws InvalidAccommodationDataException {
		String[] parts = csvLine.split(";");
		if (parts.length < 6)
			throw new InvalidAccommodationDataException("Invalid HOSTEL CSV row: " + csvLine);
		String id    = parts[1].trim();
		String name  = parts[2].trim();
		String loc   = parts[3].trim();
		double price = Double.parseDouble(parts[4].trim());
		int    beds  = Integer.parseInt(parts[5].trim());
		Hostel hs = new Hostel(name, loc, price, beds);
		hs.setAccommodationId(id); // restore original ID from file
		return hs;
	}


	// ================= TO STRING / EQUALS =================
	// Unchanged from A2

	@Override
	public String toString() {
		return super.toString()
				+ "\nType: Hostel"
				+ "\nShared Beds/Room: " + numOfSharedBedsPerRoom;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Hostel other = (Hostel) obj;
		return numOfSharedBedsPerRoom == other.numOfSharedBedsPerRoom;
	}
}