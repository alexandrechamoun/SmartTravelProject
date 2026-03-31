// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a hotel accommodation option.
// Cost = pricePerNight x numberOfDays x 1.10 (10% service fee).
// A2: star rating must be 1-5.
// A3 changes:
//   - toCsvRow() implemented (required by CsvPersistable via Accommodation)
//   - static fromCsvRow() factory method added

package travel;

import exceptions.InvalidAccommodationDataException;

public class Hotel extends Accommodation {

	// ================= ATTRIBUTES =================
	// Unchanged from A2
	private int starRating;


	// ================= CONSTRUCTORS =================
	// Unchanged from A2

	public Hotel() {}

	public Hotel(String name, String location, double pricePerNight, int starRating)
			throws InvalidAccommodationDataException {
		super(name, location, pricePerNight);
		validateStars(starRating);
		this.starRating = starRating;
	}

	public Hotel(Hotel otherHotel) {
		super(otherHotel);
		this.starRating = otherHotel.starRating;
	}


	// ================= VALIDATION =================
	// Unchanged from A2
	private static void validateStars(int stars) throws InvalidAccommodationDataException {
		if (stars < 1 || stars > 5)
			throw new InvalidAccommodationDataException(
					"Hotel star rating must be between 1 and 5 (got " + stars + ").");
	}


	// ================= GETTERS =================
	// Unchanged from A2
	public int getStarRating() { return starRating; }


	// ================= SETTERS =================
	// Unchanged from A2
	public void setStarRating(int starRating) throws InvalidAccommodationDataException {
		validateStars(starRating);
		this.starRating = starRating;
	}


	// ================= CALCULATE COST =================
	// Unchanged from A2
	@Override
	public double calculateCost(int numberOfDays) {
		return getPricePerNight() * numberOfDays * 1.10;
	}


	// ================= A3: CsvPersistable =================

	// A3: new — implements abstract toCsvRow() from Accommodation
	// Format matches A2 AccommodationFileManager output:
	// HOTEL;AccommodationID;name;location;pricePerNight;starRating
	@Override
	public String toCsvRow() {
		return "HOTEL;" + getAccommodationId() + ";" + getName() + ";"
				+ getLocation() + ";" + getPricePerNight() + ";" + starRating;
	}

	// A3: new — reconstructs a Hotel from one CSV line
	// Called by Accommodation.fromCsvRow() when the type prefix is "HOTEL"
	public static Hotel fromCsvRow(String csvLine) throws InvalidAccommodationDataException {
		String[] parts = csvLine.split(";");
		if (parts.length < 6)
			throw new InvalidAccommodationDataException("Invalid HOTEL CSV row: " + csvLine);
		String id    = parts[1].trim();
		String name  = parts[2].trim();
		String loc   = parts[3].trim();
		double price = Double.parseDouble(parts[4].trim());
		int    stars = Integer.parseInt(parts[5].trim());
		Hotel h = new Hotel(name, loc, price, stars);
		h.setAccommodationId(id); // restore original ID from file
		return h;
	}


	// ================= TO STRING / EQUALS =================
	// Unchanged from A2

	@Override
	public String toString() {
		return super.toString()
				+ "\nType: Hotel"
				+ "\nRating (Stars): " + starRating;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Hotel other = (Hotel) obj;
		return starRating == other.starRating;
	}
}