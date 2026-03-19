// -------------------------------------------------------------
// Assignment 2
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Represents a client of the SmartTravel agency.
// Each client is automatically assigned a unique ID starting at C1001.
// A2 additions: input validation on all fields (InvalidClientDataException),
// amountSpent attribute to track total spending across all trips.

package client;

import exceptions.InvalidClientDataException;

public class Client {

	// ================= ATTRIBUTES =================
	private String clientId;
	private String firstName;
	private String lastName;
	private String email;
	private double amountSpent; // A2: tracks total spending across all trips

	// Id counter shared by all Client objects.
	private static int nextId = 1001;


	// ================= VALIDATION HELPERS =================

	/**
	 * Validates a first or last name: non-empty and <= 50 chars.
	 */
	private static void validateName(String name, String fieldName) throws InvalidClientDataException {
		if (name == null || name.trim().isEmpty())
			throw new InvalidClientDataException(fieldName + " cannot be empty.");
		if (name.length() > 50)
			throw new InvalidClientDataException(fieldName + " cannot exceed 50 characters (got " + name.length() + ").");
	}

	/**
	 * Validates an email: contains '@' and '.', no spaces, <= 100 chars.
	 */
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

	// Default Constructor
	public Client() {
		this.clientId = "C" + nextId++;
		this.amountSpent = 0.0;
	}

	// Parameterized Constructor
	public Client(String firstName, String lastName, String email) throws InvalidClientDataException {
		validateName(firstName, "First name");
		validateName(lastName, "Last name");
		validateEmail(email);

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.clientId = "C" + nextId++;
		this.amountSpent = 0.0;
	}

	// Copy Constructor
	public Client(Client otherClient) {
		this.firstName = otherClient.firstName;
		this.lastName = otherClient.lastName;
		this.email = otherClient.email;
		this.clientId = "C" + nextId++;
		this.amountSpent = otherClient.amountSpent;
	}


	// ================= GETTERS =================
	public String getClientId()  { return clientId; }
	public String getFirstName() { return firstName; }
	public String getLastName()  { return lastName; }
	public String getEmail()     { return email; }
	public double getAmountSpent() { return amountSpent; }


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

	/**
	 * Adds the given amount to this client's total spending.
	 * Called by SmartTravelService whenever a trip is created for this client.
	 */
	public void addAmountSpent(double amount) {
		if (amount > 0) this.amountSpent += amount;
	}

	public void setAmountSpent(double amountSpent) {
		this.amountSpent = (amountSpent >= 0) ? amountSpent : 0;
	}


	// ================= TO STRING METHOD ==============
	@Override
	public String toString() {
		return "Client ID: " + clientId
				+ "\nFull Name: " + firstName + " " + lastName
				+ "\nEmail: " + email
				+ "\nTotal Spent: $" + String.format("%.2f", amountSpent);
	}


	// ================= EQUALS METHOD ==============
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