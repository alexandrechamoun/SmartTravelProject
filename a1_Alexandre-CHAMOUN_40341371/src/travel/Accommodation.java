// -------------------------------------------------------------
// Assignment 1
// Part: Accommodation Class
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------
// Abstract class representing an accommodation option.
// Cannot be instantiated directly — use Hotel or Hostel.
// Each subclass defines its own cost calculation formula.
// Accommodation IDs are auto-generated starting at A4001.

package travel;

abstract public class Accommodation {
	
	// ================= ATTRIBUTES =================
	private String accommodationId;
	private String name;
	private String location;
	private double pricePerNight;
	
	// Id counter shared by all Transportation objects.
	private static int nextId = 4001;
	
	// ================= CONSTRUCTORS =================
	// Default Constructor
	public Accommodation() {
		this.accommodationId = "A" + nextId++;
	};
	
	// Parameterized Constructor
	public Accommodation(String name, String location, double pricePerNight) {
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
	public String getAccommodationId() {
		return accommodationId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public double getPricePerNight() {
		return pricePerNight;
	}
	
	// ============== SETTERS ============
	public void setAccommodationId(String accommodationId) {
		this.accommodationId = accommodationId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setPricePerNight(double pricePerNight) {
		if (pricePerNight < 0) {
			this.pricePerNight = 0;
		} else {
			this.pricePerNight = pricePerNight;
		}
	}
	
	// ========== CALCULATE COST METHOD ===========
	// Abstract method (unused). This is for the child classes.
	abstract public double calculateCost(int numberOfDays);
	
	
	// ========== TO STRING METHOD ============
	@Override
	public String toString() {
		return "Accommodation ID: " + accommodationId + "\n" + "Name: " + name + "\n" + "Location: " + location + "\n" + "Price/Night: " + pricePerNight;
	}
	
	
	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (getClass() != obj.getClass()) return false;
	    
	    Accommodation other = (Accommodation) obj;
	    
	    return name.equals(other.name) &&
	           location.equals(other.location) &&
	           Double.compare(pricePerNight, other.pricePerNight) == 0;
	}
}
