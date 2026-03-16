// -------------------------------------------------------------
// Assignment 1
// Part: Hostel Class (Child class of Accommodation class)
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------
// Represents a hostel accommodation option.
// Cost = pricePerNight x numberOfDays x 0.85 (15% discount applied).

package travel;

public class Hostel extends Accommodation {
	
	// ======== ATTRIBUTES =========
	private int numOfSharedBedsPerRoom;

	// ======== CONSTRUCTORS ========
	// Default Constructor
	public Hostel() {};
	
	// Parameterized Constructor
	public Hostel(String name, String location, double pricePerNight, int numOfSharedBedsPerRoom) {
		super(name, location, pricePerNight);
		this.numOfSharedBedsPerRoom = numOfSharedBedsPerRoom;
	}
	
	// Copy Constructor
	public Hostel(Hostel otherHostel) {
		super(otherHostel);
		this.numOfSharedBedsPerRoom = otherHostel.numOfSharedBedsPerRoom;
	}
	
	// ========= GETTERS ==========
	public int getNumOfSharedBedsPerRoom() {
		return numOfSharedBedsPerRoom;
	}
	
	// ========= SETTERS =========
	public void setNumOfSharedBedsPerRoom(int numOfSharedBedsPerRoom) {
		if (numOfSharedBedsPerRoom < 0) {
			this.numOfSharedBedsPerRoom = 0;
		} else {
			this.numOfSharedBedsPerRoom = numOfSharedBedsPerRoom;
		}
	}
	
	// ========== CALCULATE COST METHOD ==========
	@Override
	public double calculateCost(int numberOfDays) {
	    return getPricePerNight() * numberOfDays * 0.85;
	}
	
	// ========== TO STRING METHOD ============
	@Override
	public String toString() {
		return super.toString() + "\n" + "Type: Hostel\n" + "Shared Beds/Room: " + numOfSharedBedsPerRoom ;
	}
	
	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
	    if (!super.equals(obj)) return false;
	    
	    Hostel other = (Hostel) obj;
	    
	    return numOfSharedBedsPerRoom == other.numOfSharedBedsPerRoom;
	}
}
