// -------------------------------------------------------------
// Assignment 2
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a train transportation option.
// Cost = baseFare x 1.50 if First Class, otherwise baseFare.
// A2 additions: trainType and seatClass must be non-empty
// (InvalidTransportDataException).

package travel;

import exceptions.InvalidTransportDataException;

public class Train extends Transportation {

	// ========= ATTRIBUTES ===========
	private String trainType;
	private String seatClass;
	private double baseFare;


	// ========= CONSTRUCTORS ===========

	// Default Constructor
	public Train() {}

	// Parameterized Constructor
	public Train(String companyName, String departureCity, String arrivalCity,
				 String trainType, String seatClass, double baseFare)
			throws InvalidTransportDataException {
		super(companyName, departureCity, arrivalCity);
		validateNonEmpty(trainType,  "Train type");
		validateNonEmpty(seatClass,  "Seat class");
		this.trainType = trainType;
		this.seatClass = seatClass;
		this.baseFare  = baseFare;
	}

	// Copy Constructor
	public Train(Train otherTrain) {
		super(otherTrain);
		this.trainType = otherTrain.trainType;
		this.seatClass = otherTrain.seatClass;
		this.baseFare  = otherTrain.baseFare;
	}


	// ========= GETTERS ===========
	public String getTrainType() { return trainType; }
	public String getSeatClass() { return seatClass; }
	public double getBaseFare()  { return baseFare; }


	// ======== SETTERS =========
	public void setTrainType(String trainType) throws InvalidTransportDataException {
		validateNonEmpty(trainType, "Train type");
		this.trainType = trainType;
	}

	public void setSeatClass(String seatClass) throws InvalidTransportDataException {
		validateNonEmpty(seatClass, "Seat class");
		this.seatClass = seatClass;
	}

	public void setBaseFare(double baseFare) { this.baseFare = baseFare; }


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
		return super.toString()
				+ "\nTrain Type: " + trainType
				+ "\nSeat Class: " + seatClass
				+ "\nBase Fare: $" + baseFare;
	}


	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Train other = (Train) obj;
		return trainType.equals(other.trainType)
				&& seatClass.equals(other.seatClass)
				&& Double.compare(baseFare, other.baseFare) == 0;
	}
}