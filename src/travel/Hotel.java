// -------------------------------------------------------------
// Assignment 2
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a hotel accommodation option.
// Cost = pricePerNight x numberOfDays x 1.10 (10% service fee).
// A2 additions: star rating must be 1-5 (InvalidAccommodationDataException).

package travel;

import exceptions.InvalidAccommodationDataException;

public class Hotel extends Accommodation {

	// ======== ATTRIBUTES =========
	private int starRating;


	// ======== CONSTRUCTORS ============

	// Default Constructor
	public Hotel() {}

	// Parameterized Constructor
	public Hotel(String name, String location, double pricePerNight, int starRating)
			throws InvalidAccommodationDataException {
		super(name, location, pricePerNight);
		validateStars(starRating);
		this.starRating = starRating;
	}

	// Copy Constructor
	public Hotel(Hotel otherHotel) {
		super(otherHotel);
		this.starRating = otherHotel.starRating;
	}


	// ================= VALIDATION =================
	private static void validateStars(int stars) throws InvalidAccommodationDataException {
		if (stars < 1 || stars > 5)
			throw new InvalidAccommodationDataException(
					"Hotel star rating must be between 1 and 5 (got " + stars + ").");
	}


	// ======== GETTERS =========
	public int getStarRating() { return starRating; }


	// ========= SETTERS =========
	public void setStarRating(int starRating) throws InvalidAccommodationDataException {
		validateStars(starRating);
		this.starRating = starRating;
	}


	// ========== CALCULATE COST METHOD ==========
	@Override
	public double calculateCost(int numberOfDays) {
		return getPricePerNight() * numberOfDays * 1.10;
	}


	// ========== TO STRING METHOD ============
	@Override
	public String toString() {
		return super.toString()
				+ "\nType: Hotel"
				+ "\nRating (Stars): " + starRating;
	}


	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Hotel other = (Hotel) obj;
		return starRating == other.starRating;
	}
}