// -------------------------------------------------------------
// Assignment 1
// Part: Client Class
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------

/*
Represents a customer of the agency.
Stores their first name, last name, and email.
Each client gets an auto-generated ID starting at C1001.
Contains the three required constructors, getters/setters, toString() and equals()
 */

package client;

public class Client {
	
	// ================= ATTRIBUTES =================
	private String clientId;
	private String firstName;
	private String lastName;
	private String email;
	
	// Id counter shared by all Client objects.
	private static int nextId = 1001; 

	
	// ================= CONSTRUCTORS =================
	// Default Constructor
	public Client() {
		this.clientId = "C" + nextId++;
	};
	
	// Parameterized Constructor
	public Client(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.clientId = "C" + nextId++;
	}
	
	// Copy Constructor
	public Client(Client otherClient) {
		this.firstName = otherClient.firstName;
		this.lastName = otherClient.lastName;
		this.email = otherClient.email;
		this.clientId = "C" + nextId++;
	}
	
	// ================= GETTERS =================
	public String getClientId() {
		return clientId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	// ================= SETTERS =================
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	// ================= TO STRING METHOD ==============
	@Override
	public String toString() {
		return "Client ID: " + clientId + "\n" + "Full Name: " + firstName + " " + lastName + "\n" + "Email: " + email;
	}
		
	
	// ================= EQUALS METHOD ==============
	@Override
	public boolean equals(Object obj) {
		// If the object being compared is null, it cannot be equal to anything.
		if (obj == null) return false;

		// If the two objects are not the exact same class (e.g. comparing a Client to a Trip),
	    if (getClass() != obj.getClass()) return false;

		// At this point we know obj is not null and is a Client.
		// We cast obj from type Object to type Client so we can
		// access Client-specific fields like firstName, lastName, email.
	    Client other = (Client) obj;
	    
	    return firstName.equals(other.firstName) &&
	           lastName.equals(other.lastName) &&
	           email.equals(other.email);
	}
}
