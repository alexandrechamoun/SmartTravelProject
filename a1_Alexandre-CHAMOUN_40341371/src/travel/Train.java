// -------------------------------------------------------------
// Assignment 1
// Part: Train Class (Child class of Transportation class)
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------
// Represents a train transportation option.
// Cost = baseFare x 1.50 if seat class is First Class, otherwise baseFare.

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
	    if (seatClass.equalsIgnoreCase("First Class"))
	        return baseFare * 1.50;
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
	    if (!super.equals(obj)) return false;
	    
	    Train other = (Train) obj;
	    
	    return trainType.equals(other.trainType) &&
	           seatClass.equals(other.seatClass) &&
	           Double.compare(baseFare, other.baseFare) == 0;
	}
	
}
