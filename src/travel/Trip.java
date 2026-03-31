// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a trip managed by SmartTravel.
// A2: basePrice >= $100, duration 1-60 days, client must be non-null.
// A3 changes:
//   - implements Identifiable:     getId() added
//   - implements Billable:         getTotalCost() added (getBasePrice() already existed)
//   - implements CsvPersistable:   toCsvRow() added
//   - implements Comparable<Trip>: compareTo() added (descending by total cost)
// Note: fromCsvRow() is not practical for Trip because loading requires already-resolved
// Client/Accommodation/Transportation objects — TripFileManager handles this as in A2.

package travel;

import client.Client;
import exceptions.InvalidTripDataException;
import interfaces.Identifiable;    // A3: new import
import interfaces.Billable;        // A3: new import
import interfaces.CsvPersistable;  // A3: new import

// A3: added "implements Identifiable, Billable, CsvPersistable, Comparable<Trip>"
// A2: public class Trip {
public class Trip implements Identifiable, Billable, CsvPersistable, Comparable<Trip> {

	// ================= ATTRIBUTES =================
	// Unchanged from A2
	private String        tripId;
	private String        destination;
	private int           numOfDays;
	private double        basePrice;
	private Client        client;
	private Transportation transportation;
	private Accommodation  accommodation;

	private static int nextId = 2001;

	private static final double MIN_BASE_PRICE = 100.00;
	private static final int    MIN_DAYS       = 1;
	private static final int    MAX_DAYS       = 60;


	// ================= VALIDATION HELPERS =================
	// Unchanged from A2

	private static void validateBasePrice(double price) throws InvalidTripDataException {
		if (price < MIN_BASE_PRICE)
			throw new InvalidTripDataException(
					"Base price must be at least $" + MIN_BASE_PRICE + " (got $" + price + ").");
	}

	private static void validateDuration(int days) throws InvalidTripDataException {
		if (days < MIN_DAYS || days > MAX_DAYS)
			throw new InvalidTripDataException(
					"Duration must be between " + MIN_DAYS + " and " + MAX_DAYS
							+ " days (got " + days + ").");
	}

	private static void validateClient(Client client) throws InvalidTripDataException {
		if (client == null)
			throw new InvalidTripDataException("A trip must be associated with a valid client.");
	}


	// ================= CONSTRUCTORS =================
	// Unchanged from A2

	public Trip() {
		this.tripId = "T" + nextId++;
	}

	public Trip(String destination, int numOfDays, double basePrice, Client client)
			throws InvalidTripDataException {
		validateClient(client);
		validateBasePrice(basePrice);
		validateDuration(numOfDays);
		this.tripId      = "T" + nextId++;
		this.destination = destination;
		this.numOfDays   = numOfDays;
		this.basePrice   = basePrice;
		this.client      = client;
	}

	public Trip(Trip otherTrip) {
		this.tripId      = "T" + nextId++;
		this.destination = otherTrip.destination;
		this.numOfDays   = otherTrip.numOfDays;
		this.basePrice   = otherTrip.basePrice;
		this.client      = (otherTrip.client != null) ? new Client(otherTrip.client) : null;
		if      (otherTrip.transportation instanceof Flight)
			this.transportation = new Flight((Flight) otherTrip.transportation);
		else if (otherTrip.transportation instanceof Train)
			this.transportation = new Train((Train) otherTrip.transportation);
		else if (otherTrip.transportation instanceof Bus)
			this.transportation = new Bus((Bus) otherTrip.transportation);
		else
			this.transportation = null;
		if      (otherTrip.accommodation instanceof Hotel)
			this.accommodation = new Hotel((Hotel) otherTrip.accommodation);
		else if (otherTrip.accommodation instanceof Hostel)
			this.accommodation = new Hostel((Hostel) otherTrip.accommodation);
		else
			this.accommodation = null;
	}


	// ================= GETTERS =================
	// Unchanged from A2
	public String         getTripId()          { return tripId; }
	public String         getDestination()     { return destination; }
	public int            getDurationInDays()  { return numOfDays; }
	public Client         getClient()          { return client; }
	public Transportation getTransportation()  { return transportation; }
	public Accommodation  getAccommodation()   { return accommodation; }

	// A3: new — satisfies the Identifiable interface
	@Override
	public String getId() { return tripId; }

	// A3: new — satisfies the Billable interface
	// getBasePrice() was already a field; this makes it part of the Billable contract
	@Override
	public double getBasePrice() { return basePrice; }

	// A3: new — satisfies the Billable interface
	// Delegates to the existing calculateTotalCost() so no logic is duplicated
	@Override
	public double getTotalCost() { return calculateTotalCost(); }


	// ================= SETTERS =================
	// Unchanged from A2
	public void setDestination(String destination) { this.destination = destination; }
	public void setTripId(String tripId)           { this.tripId = tripId; }

	public void setDurationInDays(int numOfDays) throws InvalidTripDataException {
		validateDuration(numOfDays);
		this.numOfDays = numOfDays;
	}

	public void setBasePrice(double basePrice) throws InvalidTripDataException {
		validateBasePrice(basePrice);
		this.basePrice = basePrice;
	}

	public void setClient(Client client) throws InvalidTripDataException {
		validateClient(client);
		this.client = client;
	}

	public void setTransportation(Transportation transportation) {
		this.transportation = transportation;
	}

	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}


	// ================= BUSINESS METHODS =================
	// Unchanged from A2

	public String getTripType() {
		if (transportation instanceof Flight) return "Flight Trip";
		if (transportation instanceof Train)  return "Train Trip";
		if (transportation instanceof Bus)    return "Bus Trip";
		return "No Transport";
	}

	public double calculateTotalCost() {
		double total = basePrice;
		if (transportation != null) total += transportation.calculateCost(numOfDays);
		if (accommodation  != null) total += accommodation.calculateCost(numOfDays);
		return total;
	}


	// ================= A3: CsvPersistable =================

	// A3: new — satisfies the CsvPersistable interface
	// Produces the same semicolon format the A2 TripFileManager wrote:
	// TripID;ClientID;AccommodationID;TransportationID;Destination;Days;BasePrice
	// Empty string is used for optional fields (accommodation, transportation) if null
	@Override
	public String toCsvRow() {
		String clientId = (client        != null) ? client.getClientId()                : "";
		String accId    = (accommodation  != null) ? accommodation.getAccommodationId() : "";
		String transId  = (transportation != null) ? transportation.getTransportId()    : "";
		return tripId + ";" + clientId + ";" + accId + ";" + transId + ";"
				+ destination + ";" + numOfDays + ";" + basePrice;
	}


	// ================= A3: Comparable<Trip> =================

	// A3: new — defines natural ordering for Trip
	// Business rule: highest revenue trips come first
	// Reversed comparison (other vs this) makes Collections.sort() produce descending order
	@Override
	public int compareTo(Trip other) {
		return Double.compare(other.calculateTotalCost(), this.calculateTotalCost());
	}


	// ================= TO STRING / EQUALS =================
	// Unchanged from A2

	@Override
	public String toString() {
		return "Trip ID: "       + tripId
				+ "\nDestination: " + destination
				+ "\nDays: "        + numOfDays
				+ "\nBase Price: $" + basePrice
				+ "\nTotal Cost: $" + String.format("%.2f", calculateTotalCost())
				+ "\nType: "        + getTripType()
				+ "\n  Client: "         + (client        != null ? client.toString()        : "None")
				+ "\n  Transportation: " + (transportation != null ? transportation.toString(): "None")
				+ "\n  Accommodation: "  + (accommodation  != null ? accommodation.toString() : "None");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Trip other = (Trip) obj;
		if (!destination.equals(other.destination)) return false;
		if (numOfDays != other.numOfDays) return false;
		if (Double.compare(basePrice, other.basePrice) != 0) return false;
		if (client == null && other.client != null) return false;
		if (client != null && !client.equals(other.client)) return false;
		if (transportation == null && other.transportation != null) return false;
		if (transportation != null && !transportation.equals(other.transportation)) return false;
		if (accommodation == null && other.accommodation != null) return false;
		if (accommodation != null && !accommodation.equals(other.accommodation)) return false;
		return true;
	}
}