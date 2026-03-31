// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a train transportation option.
// Cost = baseFare x 1.50 if First Class, otherwise baseFare.
// A2: trainType and seatClass must be non-empty.
// A3 changes:
//   - getBaseFare() implemented (required by abstract method in Transportation for compareTo)
//   - toCsvRow() implemented (required by CsvPersistable via Transportation)
//   - static fromCsvRow() factory method added

package travel;

import exceptions.InvalidTransportDataException;

public class Train extends Transportation {

	// ================= ATTRIBUTES =================
	// Unchanged from A2
	private String trainType;
	private String seatClass;
	private double baseFare;


	// ================= CONSTRUCTORS =================
	// Unchanged from A2

	public Train() {}

	public Train(String companyName, String departureCity, String arrivalCity,
				 String trainType, String seatClass, double baseFare)
			throws InvalidTransportDataException {
		super(companyName, departureCity, arrivalCity);
		validateNonEmpty(trainType, "Train type");
		validateNonEmpty(seatClass, "Seat class");
		this.trainType = trainType;
		this.seatClass = seatClass;
		this.baseFare  = baseFare;
	}

	public Train(Train otherTrain) {
		super(otherTrain);
		this.trainType = otherTrain.trainType;
		this.seatClass = otherTrain.seatClass;
		this.baseFare  = otherTrain.baseFare;
	}


	// ================= GETTERS =================
	// Unchanged from A2
	public String getTrainType() { return trainType; }
	public String getSeatClass() { return seatClass; }

	// A3: new — implements the abstract getBaseFare() declared in Transportation
	// Needed so Transportation.compareTo() can compare fares without casting to Train
	@Override
	public double getBaseFare() { return baseFare; }


	// ================= SETTERS =================
	// Unchanged from A2
	public void setTrainType(String trainType) throws InvalidTransportDataException {
		validateNonEmpty(trainType, "Train type");
		this.trainType = trainType;
	}

	public void setSeatClass(String seatClass) throws InvalidTransportDataException {
		validateNonEmpty(seatClass, "Seat class");
		this.seatClass = seatClass;
	}

	public void setBaseFare(double baseFare) { this.baseFare = baseFare; }


	// ================= CALCULATE COST =================
	// Unchanged from A2
	@Override
	public double calculateCost(int numberOfDays) {
		if (seatClass.equalsIgnoreCase("First Class"))
			return baseFare * 1.50;
		return baseFare;
	}


	// ================= A3: CsvPersistable =================

	// A3: new — implements abstract toCsvRow() from Transportation
	// Format matches A2 TransportFileManager output:
	// TRAIN;TransportID;companyName;departureCity;arrivalCity;baseFare;trainType;seatClass
	@Override
	public String toCsvRow() {
		return "TRAIN;" + getTransportId() + ";" + getCompanyName() + ";"
				+ getDepartureCity() + ";" + getArrivalCity() + ";"
				+ baseFare + ";" + trainType + ";" + seatClass;
	}

	// A3: new — reconstructs a Train from one CSV line
	// Called by Transportation.fromCsvRow() when the type prefix is "TRAIN"
	public static Train fromCsvRow(String csvLine) throws InvalidTransportDataException {
		String[] parts = csvLine.split(";");
		if (parts.length < 8)
			throw new InvalidTransportDataException("Invalid TRAIN CSV row: " + csvLine);
		String id        = parts[1].trim();
		String company   = parts[2].trim();
		String dep       = parts[3].trim();
		String arr       = parts[4].trim();
		double fare      = Double.parseDouble(parts[5].trim());
		String trainType = parts[6].trim();
		String seatClass = parts[7].trim();
		Train t = new Train(company, dep, arr, trainType, seatClass, fare);
		t.setTransportId(id); // restore original ID from file
		return t;
	}


	// ================= TO STRING / EQUALS =================
	// Unchanged from A2

	@Override
	public String toString() {
		return super.toString()
				+ "\nTrain Type: " + trainType
				+ "\nSeat Class: " + seatClass
				+ "\nBase Fare: $" + baseFare;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		Train other = (Train) obj;
		return trainType.equals(other.trainType)
				&& seatClass.equals(other.seatClass)
				&& Double.compare(baseFare, other.baseFare) == 0;
	}
}