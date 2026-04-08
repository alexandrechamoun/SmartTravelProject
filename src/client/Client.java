// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a client of the SmartTravel agency.
// A3 changes:
//   - implements Identifiable:    getId() added
//   - implements CsvPersistable:  toCsvRow() and static fromCsvRow() added
//   - implements Comparable<Client>: compareTo() added (descending by amountSpent)

package client;

import exceptions.InvalidClientDataException;
import interfaces.Identifiable;    
import interfaces.CsvPersistable;


public class Client implements Identifiable, CsvPersistable, Comparable<Client> {

	// ================= ATTRIBUTES =================
	// Unchanged from A2
	private String clientId;
	private String firstName;
	private String lastName;
	private String email;
	private double amountSpent;

	private static int nextId = 1001;


	// ================= VALIDATION HELPERS =================

	private static void validateName(String name, String fieldName) throws InvalidClientDataException {
		if (name == null || name.trim().isEmpty())
			throw new InvalidClientDataException(fieldName + " cannot be empty.");
		if (name.length() > 50)
			throw new InvalidClientDataException(fieldName + " cannot exceed 50 characters (got " + name.length() + ").");
	}

	private static void validateEmail(String email) throws InvalidClientDataException {
		if (email == null || email.trim().isEmpty())
			throw new InvalidClientDataException("Email cannot be empty.");
		if (email.length() > 100)
			throw new InvalidClientDataException("Email cannot exceed 100 characters (got " + email.length() + ").");
		if (!email.contains("@"))
			throw new InvalidClientDataException("Email must contain '@': " + email);
		if (!email.contains("."))
			throw new InvalidClientDataException("Email must contain '.': " + email);
		if (email.contains(" "))
			throw new InvalidClientDataException("Email must not contain spaces: " + email);
	}


	// ================= CONSTRUCTORS =================

	public Client() {
		this.clientId    = "C" + nextId++;
		this.amountSpent = 0.0;
	}

	public Client(String firstName, String lastName, String email) throws InvalidClientDataException {
		validateName(firstName, "First name");
		validateName(lastName,  "Last name");
		validateEmail(email);
		this.firstName   = firstName;
		this.lastName    = lastName;
		this.email       = email;
		this.clientId    = "C" + nextId++;
		this.amountSpent = 0.0;
	}

	public Client(Client otherClient) {
		this.firstName   = otherClient.firstName;
		this.lastName    = otherClient.lastName;
		this.email       = otherClient.email;
		this.clientId    = "C" + nextId++;
		this.amountSpent = otherClient.amountSpent;
	}


	// ================= GETTERS =================
	public String getClientId()    { return clientId; }
	public String getFirstName()   { return firstName; }
	public String getLastName()    { return lastName; }
	public String getEmail()       { return email; }
	public double getAmountSpent() { return amountSpent; }

	// Repository and GenericFileManager use getId() to look up any entity by ID
	@Override
	public String getId() { return clientId; }


	// ================= SETTERS =================
	public void setClientId(String clientId) { this.clientId = clientId; }

	public void setFirstName(String firstName) throws InvalidClientDataException {
		validateName(firstName, "First name");
		this.firstName = firstName;
	}

	public void setLastName(String lastName) throws InvalidClientDataException {
		validateName(lastName, "Last name");
		this.lastName = lastName;
	}

	public void setEmail(String email) throws InvalidClientDataException {
		validateEmail(email);
		this.email = email;
	}

	public void addAmountSpent(double amount) {
		if (amount > 0) this.amountSpent += amount;
	}

	public void setAmountSpent(double amountSpent) {
		this.amountSpent = (amountSpent >= 0) ? amountSpent : 0;
	}


	// ================= CsvPersistable =================

	// satisfies the CsvPersistable interface
	// Produces the same semicolon format the A2 ClientFileManager wrote:
	// ClientID;firstName;lastName;email
	@Override
	public String toCsvRow() {
		return clientId + ";" + firstName + ";" + lastName + ";" + email;
	}

	// static factory that reconstructs a Client from one CSV line
	// Mirror of toCsvRow(): splits on ";" and validates each field
	// Used by GenericFileManager.load() in Part 4
	public static Client fromCsvRow(String csvLine) throws InvalidClientDataException {
		String[] parts = csvLine.split(";");
		if (parts.length != 4)
			throw new InvalidClientDataException("Invalid client CSV row: " + csvLine);
		String id        = parts[0].trim();
		String firstName = parts[1].trim();
		String lastName  = parts[2].trim();
		String email     = parts[3].trim();
		Client c = new Client(firstName, lastName, email);
		c.setClientId(id); // restore original ID from file, overriding auto-generated one
		return c;
	}


	// ================= A3: Comparable<Client> =================

	// defines natural ordering for Client
	// Business rule: most valuable clients (highest amountSpent) come first
	// Reversed comparison (other vs this) makes Collections.sort() produce descending order
	@Override
	public int compareTo(Client other) {
		return Double.compare(other.amountSpent, this.amountSpent);
	}


	// ================= TO STRING / EQUALS =================

	@Override
	public String toString() {
		return "Client ID: "      + clientId
				+ "\nFull Name: "    + firstName + " " + lastName
				+ "\nEmail: "        + email
				+ "\nTotal Spent: $" + String.format("%.2f", amountSpent);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Client other = (Client) obj;
		return firstName.equals(other.firstName)
				&& lastName.equals(other.lastName)
				&& email.equals(other.email);
	}
}
