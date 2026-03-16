// -------------------------------------------------------------
// Assignment 1
// Part: Train Class (Child class of Transportation class)
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------
// Represents a train transportation option.
// Cost = baseFare x 1.50 if seat class is First Class, otherwise baseFare.
/*
Extends Transportation.
Adds train type, seat class, and base fare.
Pricing rule: baseFare × 1.50 if First Class, otherwise baseFare.
 */

package travel;

public class Train extends Transportation {
	
	// ========= ATTRIBUTES ===========
	private String trainType;
	private String seatClass;
	private double baseFare;
	
	// ========= CONSTRUCTORS ===========
	// Default Constructor
	public Train() {};
	
	// Parameterized Constructor
	public Train(String companyName, String departureCity, String arrivalCity, String trainType, String seatClass, double baseFare){
		super(companyName, departureCity, arrivalCity);
		this.trainType = trainType;
		this.seatClass = seatClass;
		this.baseFare = baseFare;
	}
	
	// Copy Constructor
	public Train(Train otherTrain) {
		super(otherTrain);
		this.trainType = otherTrain.trainType;
		this.seatClass = otherTrain.seatClass;
		this.baseFare = otherTrain.baseFare;
	}
	
	// ========= GETTERS ===========
	public String getTrainType() {
		return trainType;
	}
	
	public String getSeatClass() {
		return seatClass;
	}
	
	public double getBaseFare() {
		return baseFare;
	}
	
	// ======== SETTERS =========
	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}
	
	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}
	
	public void setBaseFare(double baseFare) {
		this.baseFare = baseFare;
	}
	
	// ========== CALCULATE COST METHOD ==========
	@Override
	public double calculateCost(int numberOfDays) {
		// Check if the seat class is First Class (case-insensitive).
		// equalsIgnoreCase() is used so "first class", "First Class",
		// "FIRST CLASS" all match correctly.
	    if (seatClass.equalsIgnoreCase("First Class"))
	        return baseFare * 1.50;

		// Any other seat class (Economy, Business, etc.)
		// just pays the base fare with no markup
	    return baseFare;
	}
	
	// ============= TO STRING METHOD ==============
	@Override
	public String toString() {
		return super.toString() + "\n" + "Train Type: " + trainType + "\n" + "Seat Class: " + seatClass + "\n" + "Base Fare: $" + baseFare;
	}
	
	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		// Instead of repeating the null check, class check, and cast ourselves,
		// we call the parent class (Transportation) equals() method first.
		// It handles all of that for us.
		// If the Transportation fields are not equal, there is no point checking specific fields, so we return false immediately.
	    if (!super.equals(obj)) return false;
	    
	    Train other = (Train) obj;
	    
	    return trainType.equals(other.trainType) &&
	           seatClass.equals(other.seatClass) &&
	           Double.compare(baseFare, other.baseFare) == 0;
	}
	
}
