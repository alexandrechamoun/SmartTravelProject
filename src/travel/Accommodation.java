// -------------------------------------------------------------
// Assignment 2
// Written by: Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Abstract class representing an accommodation option.
// A2 additions: pricePerNight must be > $0 (InvalidAccommodationDataException).
// Subclasses add their own additional rules (Hotel: stars 1-5,
// Hostel: price <= $150/night).

package travel;

import exceptions.InvalidAccommodationDataException;

abstract public class Accommodation {

	// ================= ATTRIBUTES =================
	private String accommodationId;
	private String name;
	private String location;
	private double pricePerNight;

	// Id counter shared by all Accommodation objects.
	private static int nextId = 4001;


	// ================= VALIDATION HELPERS =================

	protected static void validatePricePerNight(double price) throws InvalidAccommodationDataException {
		if (price <= 0)
			throw new InvalidAccommodationDataException("Price per night must be greater than $0 (got $" + price + ").");
	}


	// ================= CONSTRUCTORS =================

	// Default Constructor
	public Accommodation() {
		this.accommodationId = "A" + nextId++;
	}

	// Parameterized Constructor
	public Accommodation(String name, String location, double pricePerNight) throws InvalidAccommodationDataException {
		validatePricePerNight(pricePerNight);
		this.name = name;
		this.location = location;
		this.pricePerNight = pricePerNight;
		this.accommodationId = "A" + nextId++;
	}

	// Copy Constructor
	public Accommodation(Accommodation otherAccommodation) {
		this.name = otherAccommodation.name;
		this.location = otherAccommodation.location;
		this.pricePerNight = otherAccommodation.pricePerNight;
		this.accommodationId = "A" + nextId++;
	}


	// ============== GETTERS ============
	public String getAccommodationId() { return accommodationId; }
	public String getName()            { return name; }
	public String getLocation()        { return location; }
	public double getPricePerNight()   { return pricePerNight; }


	// ============== SETTERS ============
	public void setAccommodationId(String accommodationId) { this.accommodationId = accommodationId; }
	public void setName(String name)         { this.name = name; }
	public void setLocation(String location) { this.location = location; }

	public void setPricePerNight(double pricePerNight) throws InvalidAccommodationDataException {
		validatePricePerNight(pricePerNight);
		this.pricePerNight = pricePerNight;
	}


	// ========== CALCULATE COST METHOD ===========
	abstract public double calculateCost(int numberOfDays);


	// ========== TO STRING METHOD ============
	@Override
	public String toString() {
		return "Accommodation ID: " + accommodationId
				+ "\nName: " + name
				+ "\nLocation: " + location
				+ "\nPrice/Night: $" + pricePerNight;
	}


	// ================= EQUALS METHOD ==============
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