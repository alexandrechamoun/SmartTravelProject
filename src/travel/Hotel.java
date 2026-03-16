// -------------------------------------------------------------
// Assignment 1
// Part: Hotel Class (Child class of Accommodation Class)
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------
// Represents a hotel accommodation option.
// Cost = pricePerNight x numberOfDays x 1.10 (10% service fee applied).

/*
Extends Accommodation.
Adds star rating.
Pricing rule: pricePerNight × days × 1.10 — a 10% service fee is applied.
 */

package travel;

public class Hotel extends Accommodation{

	// ======== ATTRIBUTES =========
	private int starRating;
	
	// ======== CONSTRUCTORS ============
	// Default Constructor
	public Hotel() {};
	
	// Parameterized Constructor
	public Hotel(String name, String location, double pricePerNight, int starRating) {
		super(name, location, pricePerNight);
		this.starRating = starRating;
	}
	
	// Copy Constructor
	public Hotel(Hotel otherHotel) {
		super(otherHotel);
		this.starRating = otherHotel.starRating;
	}
	
	// ======== GETTERS =========
	public int getStarRating() {
		return starRating;
	}
	
	// ========= SETTERS =========
	public void setStarRating(int starRating) {
		if(starRating < 0) {
			this.starRating = 0;
		} else {
			this.starRating = starRating;
		}
	}
	
	// ========== CALCULATE COST METHOD ==========
	@Override
	public double calculateCost(int numberOfDays) {
		// pricePerNight multiplied by number of days gives the base stay cost.
		// Multiplying by 1.10 adds a 10% service fee on top.
		// Example: $200/night x 5 days x 1.10 = $1100
	    return getPricePerNight() * numberOfDays * 1.10;
	}
	
	
	// ========== TO STRING METHOD ============
	@Override
	public String toString() {
		return super.toString() + "\n" + "Type: Hotel\n" + "Rating (Stars): " + starRating ;
	}
	
	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		// Instead of repeating the null check, class check, and cast ourselves,
		// we call the parent class (Accommodation) equals() method first.
		// It handles all of that for us.
		// If the Accommodation fields are not equal, there is no point checking specific fields, so we return false immediately.
	    if (!super.equals(obj)) return false;
	    
	    Hotel other = (Hotel) obj;
	    
	    return starRating == other.starRating;
	}
	
}
