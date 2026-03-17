// -------------------------------------------------------------
// Assignment 2
// Written by: Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a hostel accommodation option.
// Cost = pricePerNight x numberOfDays x 0.85 (15% discount).
// A2 additions: price per night must be <= $150 (InvalidAccommodationDataException).

package travel;

import exceptions.InvalidAccommodationDataException;

public class Hostel extends Accommodation {

	// ======== ATTRIBUTES =========
	private int numOfSharedBedsPerRoom;

	private static final double MAX_HOSTEL_PRICE = 150.0;


	// ======== CONSTRUCTORS ========

	// Default Constructor
	public Hostel() {}

	// Parameterized Constructor
	public Hostel(String name, String location, double pricePerNight, int numOfSharedBedsPerRoom)
			throws InvalidAccommodationDataException {
		super(name, location, pricePerNight); // base validates price > 0
		validateHostelPrice(pricePerNight);
		validateBeds(numOfSharedBedsPerRoom);
		this.numOfSharedBedsPerRoom = numOfSharedBedsPerRoom;
	}

	// Copy Constructor
	public Hostel(Hostel otherHostel) {
		super(otherHostel);
		this.numOfSharedBedsPerRoom = otherHostel.numOfSharedBedsPerRoom;
	}


	// ================= VALIDATION =================
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


	// ========= GETTERS ==========
	public int getNumOfSharedBedsPerRoom() { return numOfSharedBedsPerRoom; }


	// ========= SETTERS =========
	public void setNumOfSharedBedsPerRoom(int numOfSharedBedsPerRoom)
			throws InvalidAccommodationDataException {
		validateBeds(numOfSharedBedsPerRoom);
		this.numOfSharedBedsPerRoom = numOfSharedBedsPerRoom;
	}

	@Override
	public void setPricePerNight(double pricePerNight) throws InvalidAccommodationDataException {
		super.setPricePerNight(pricePerNight); // validates > 0
		validateHostelPrice(pricePerNight);    // validates <= $150
	}


	// ========== CALCULATE COST METHOD ==========
	@Override
	public double calculateCost(int numberOfDays) {
		return getPricePerNight() * numberOfDays * 0.85;
	}


	// ========== TO STRING METHOD ============
	@Override
	public String toString() {
		return super.toString()
				+ "\nType: Hostel"
				+ "\nShared Beds/Room: " + numOfSharedBedsPerRoom;
	}


	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Hostel other = (Hostel) obj;
		return numOfSharedBedsPerRoom == other.numOfSharedBedsPerRoom;
	}
}
