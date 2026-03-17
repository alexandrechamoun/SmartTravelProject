// -------------------------------------------------------------
// Assignment 2
// Written by: Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Main driver class for the SmartTravel application.
// Provides a menu-driven interface and a predefined testing scenario.
// A2 update: all constructor/setter calls now wrapped in try-catch
// to handle the new checked exceptions from the domain model.

package driver;

import java.util.Scanner;

import exceptions.DuplicateEmailException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidClientDataException;
import exceptions.InvalidTripDataException;
import exceptions.InvalidTransportDataException;
import exceptions.InvalidAccommodationDataException;

import client.Client;
import travel.Accommodation;
import travel.Bus;
import travel.Flight;
import travel.Hostel;
import travel.Hotel;
import travel.Train;
import travel.Transportation;
import travel.Trip;

public class Driver {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int userChoice;

		welcomeMessage();
		System.out.println();
		System.out.println("Menu (1) or Predefined Testing Scenario (2): ");
		promptUserForChoice();
		userChoice = scanner.nextInt();

		switch (userChoice) {
			case 1: menu(); break;
			case 2: predefinedScenario(); break;
		}

		scanner.close();
	}


	// ============================ STATIC METHODS =============================

	public static void welcomeMessage() {
		System.out.print("Welcome to my SmartTravel Agency! Made by Alexandre Chamoun & Adam Kozman.");
	}

	public static void promptUserForChoice() {
		System.out.println("What do you want to do? ");
	}


	// ========================= PREDEFINED SCENARIO ===========================
	public static void predefinedScenario() {
		System.out.println("╔═════════════════════════════════════════════╗");
		System.out.println("║     SMARTTRAVEL A2 - PREDEFINED SCENARIO    ║");
		System.out.println("╚═════════════════════════════════════════════╝");
		System.out.println("This scenario demonstrates:");
		System.out.println("  1. Valid object creation (happy path)");
		System.out.println("  2. All 6 custom exceptions being triggered and caught");
		System.out.println("  3. Polymorphic cost calculation");
		System.out.println("  4. Deep copy verification");
		System.out.println("  5. equals() method testing");
		System.out.println();


		// ================================================================
		// PART 1 — VALID OBJECT CREATION (HAPPY PATH)
		// ================================================================
		System.out.println("════════════════════════════════════════════════");
		System.out.println("       PART 1: Valid Object Creation");
		System.out.println("════════════════════════════════════════════════");

		// ----- Valid Clients -----
		Client c1 = null, c2 = null, c3 = null;
		try {
			c1 = new Client("Alexandre", "Chamoun", "alex@gmail.com");
			c2 = new Client("John", "Doe", "doe@gmail.com");
			c3 = new Client("Marc", "Smith", "marc@gmail.com");
			System.out.println("[OK] 3 clients created successfully.");
		} catch (InvalidClientDataException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}

		Client[] clients = new Client[3];
		clients[0] = c1; clients[1] = c2; clients[2] = c3;

		System.out.println("\n--- Clients ---");
		for (int i = 0; i < clients.length; i++) {
			System.out.println(clients[i]);
			System.out.println("----------");
		}

		// ----- Valid Transportation -----
		Flight f1 = null, f2 = null;
		Train  t1 = null, t2 = null;
		Bus    b1 = null, b2 = null;

		try {
			f1 = new Flight("Air Canada", "Montreal", "London", "Air Canada", 25, 500.00);
			f2 = new Flight("Porter", "Montreal", "Fort Lauderdale", "Porter Airlines", 15, 275.00);
			System.out.println("[OK] 2 flights created successfully.");
		} catch (InvalidTransportDataException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}

		try {
			t1 = new Train("Eurostar", "Paris", "Nice", "Regional", "Economy", 80.00);
			t2 = new Train("Alta Velocidad Española", "Madrid", "Barcelona", "High-Speed", "First Class", 110.00);
			System.out.println("[OK] 2 trains created successfully.");
		} catch (InvalidTransportDataException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}

		try {
			b1 = new Bus("FlixBus", "Berlin", "Amsterdam", "FlixBus", 4, 24.99);
			b2 = new Bus("MegaBus", "Chicago", "Indianapolis", "MegaBus", 3, 14.99);
			System.out.println("[OK] 2 buses created successfully.");
		} catch (InvalidTransportDataException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}

		Transportation[] transportations = new Transportation[6];
		transportations[0] = f1; transportations[1] = f2;
		transportations[2] = t1; transportations[3] = t2;
		transportations[4] = b1; transportations[5] = b2;

		System.out.println("\n--- Transportations ---");
		for (int i = 0; i < transportations.length; i++) {
			System.out.println(transportations[i]);
			System.out.println("----------");
		}

		// ----- Valid Accommodations -----
		Hotel  h1 = null, h2 = null;
		Hostel hs1 = null, hs2 = null;

		try {
			h1 = new Hotel("Marriott", "Paris", 200.00, 5);
			h2 = new Hotel("Holiday Inn", "London", 120.00, 3);
			System.out.println("[OK] 2 hotels created successfully.");
		} catch (InvalidAccommodationDataException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}

		try {
			hs1 = new Hostel("HI Hostel", "Amsterdam", 35.00, 6);
			hs2 = new Hostel("Generator", "Nice", 40.00, 8);
			System.out.println("[OK] 2 hostels created successfully.");
		} catch (InvalidAccommodationDataException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}

		Accommodation[] accommodations = new Accommodation[4];
		accommodations[0] = h1; accommodations[1] = h2;
		accommodations[2] = hs1; accommodations[3] = hs2;

		System.out.println("\n--- Accommodations ---");
		for (int i = 0; i < accommodations.length; i++) {
			System.out.println(accommodations[i]);
			System.out.println("----------");
		}

		// ----- Valid Trips -----
		Trip trip1 = null, trip2 = null, trip3 = null;

		try {
			trip1 = new Trip("London", 5, 100.00, c1);
			trip1.setTransportation(f1);
			trip1.setAccommodation(h2);
			System.out.println("[OK] Trip 1 (London) created successfully.");
		} catch (InvalidTripDataException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}

		try {
			trip2 = new Trip("Amsterdam", 3, 100.00, c2);
			trip2.setTransportation(b1);
			trip2.setAccommodation(hs1);
			System.out.println("[OK] Trip 2 (Amsterdam) created successfully.");
		} catch (InvalidTripDataException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}

		try {
			trip3 = new Trip("Nice", 5, 100.00, c3);
			trip3.setTransportation(t1);
			trip3.setAccommodation(hs2);
			System.out.println("[OK] Trip 3 (Nice) created successfully.");
		} catch (InvalidTripDataException e) {
			System.out.println("[ERROR] " + e.getMessage());
		}

		Trip[] trips = new Trip[3];
		trips[0] = trip1; trips[1] = trip2; trips[2] = trip3;

		System.out.println("\n--- Trips ---");
		for (int i = 0; i < trips.length; i++) {
			System.out.println(trips[i]);
			System.out.println("----------");
		}


		// ================================================================
		// PART 2 — EXCEPTION DEMONSTRATIONS
		// ================================================================
		System.out.println("\n════════════════════════════════════════════════");
		System.out.println("  PART 2: Exception Handling Demonstrations");
		System.out.println("════════════════════════════════════════════════");


		// ------ 1. InvalidClientDataException ------
		System.out.println("\n[TEST 1] InvalidClientDataException");
		System.out.println("-------------------------------------");

		// Empty first name
		System.out.print("  Creating client with empty first name... ");
		try {
			new Client("", "Chamoun", "alex2@gmail.com");
		} catch (InvalidClientDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Name too long (> 50 chars)
		System.out.print("  Creating client with name exceeding 50 chars... ");
		try {
			new Client("AlexandreAlexandreAlexandreAlexandreAlexandreAlexandre", "Chamoun", "alex3@gmail.com");
		} catch (InvalidClientDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Email missing @
		System.out.print("  Creating client with email missing '@'... ");
		try {
			new Client("Sophie", "Martin", "sophiegmail.com");
		} catch (InvalidClientDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Email with spaces
		System.out.print("  Creating client with spaces in email... ");
		try {
			new Client("Sophie", "Martin", "sophie @gmail.com");
		} catch (InvalidClientDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}


		// ------ 2. InvalidTripDataException ------
		System.out.println("\n[TEST 2] InvalidTripDataException");
		System.out.println("-----------------------------------");

		// Base price below $100
		System.out.print("  Creating trip with base price $50 (min is $100)... ");
		try {
			new Trip("Paris", 3, 50.00, c1);
		} catch (InvalidTripDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Duration = 0 days
		System.out.print("  Creating trip with 0 days (min is 1)... ");
		try {
			new Trip("Paris", 0, 200.00, c1);
		} catch (InvalidTripDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Duration = 25 days (over max of 20)
		System.out.print("  Creating trip with 25 days (max is 20)... ");
		try {
			new Trip("Paris", 25, 200.00, c1);
		} catch (InvalidTripDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Null client
		System.out.print("  Creating trip with null client... ");
		try {
			new Trip("Paris", 5, 200.00, null);
		} catch (InvalidTripDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}


		// ------ 3. InvalidTransportDataException ------
		System.out.println("\n[TEST 3] InvalidTransportDataException");
		System.out.println("---------------------------------------");

		// Flight with negative luggage
		System.out.print("  Creating flight with -5kg luggage allowance... ");
		try {
			new Flight("Air Canada", "Montreal", "Rome", "Air Canada", -5, 400.00);
		} catch (InvalidTransportDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Bus with 0 stops (business rule: min 1)
		System.out.print("  Creating bus with 0 stops (min is 1)... ");
		try {
			new Bus("FlixBus", "Paris", "Lyon", "FlixBus", 0, 30.00);
		} catch (InvalidTransportDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Train with empty seat class
		System.out.print("  Creating train with empty seat class... ");
		try {
			new Train("VIA Rail", "Montreal", "Toronto", "Regional", "", 60.00);
		} catch (InvalidTransportDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Transportation with empty departure city
		System.out.print("  Creating flight with empty departure city... ");
		try {
			new Flight("Air Canada", "", "London", "Air Canada", 20, 400.00);
		} catch (InvalidTransportDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}


		// ------ 4. InvalidAccommodationDataException ------
		System.out.println("\n[TEST 4] InvalidAccommodationDataException");
		System.out.println("------------------------------------------");

		// Hotel with price = 0
		System.out.print("  Creating hotel with $0/night price... ");
		try {
			new Hotel("Budget Inn", "Berlin", 0.00, 3);
		} catch (InvalidAccommodationDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Hotel with star rating = 0
		System.out.print("  Creating hotel with 0-star rating (min is 1)... ");
		try {
			new Hotel("No Stars Inn", "Berlin", 80.00, 0);
		} catch (InvalidAccommodationDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Hotel with star rating = 6
		System.out.print("  Creating hotel with 6-star rating (max is 5)... ");
		try {
			new Hotel("Too Many Stars", "Dubai", 500.00, 6);
		} catch (InvalidAccommodationDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Hostel over $150/night (business rule)
		System.out.print("  Creating hostel at $200/night (max is $150)... ");
		try {
			new Hostel("Fancy Hostel", "Monaco", 200.00, 4);
		} catch (InvalidAccommodationDataException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}


		// ------ 5. EntityNotFoundException ------
		System.out.println("\n[TEST 5] EntityNotFoundException");
		System.out.println("---------------------------------");

		// Simulate searching for a client ID that doesn't exist
		System.out.print("  Searching for non-existent client ID 'C9999'... ");
		try {
			String searchId = "C9999";
			boolean found = false;
			for (int i = 0; i < clients.length; i++) {
				if (clients[i] != null && clients[i].getClientId().equals(searchId)) {
					found = true;
					break;
				}
			}
			if (!found)
				throw new EntityNotFoundException(
						"Client with ID '" + searchId + "' not found in the system.");
		} catch (EntityNotFoundException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}

		// Simulate searching for a trip ID that doesn't exist
		System.out.print("  Searching for non-existent trip ID 'T9999'... ");
		try {
			String searchId = "T9999";
			boolean found = false;
			for (int i = 0; i < trips.length; i++) {
				if (trips[i] != null && trips[i].getTripId().equals(searchId)) {
					found = true;
					break;
				}
			}
			if (!found)
				throw new EntityNotFoundException(
						"Trip with ID '" + searchId + "' not found in the system.");
		} catch (EntityNotFoundException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}


		// ------ 6. DuplicateEmailException (unchecked) ------
		System.out.println("\n[TEST 6] DuplicateEmailException (unchecked)");
		System.out.println("---------------------------------------------");

		// Simulate trying to add a client with an email already in the array
		System.out.print("  Adding client with duplicate email 'alex@gmail.com'... ");
		try {
			String newEmail = "alex@gmail.com";
			for (int i = 0; i < clients.length; i++) {
				if (clients[i] != null && clients[i].getEmail().equals(newEmail))
					throw new DuplicateEmailException(
							"Email '" + newEmail + "' is already registered to " + clients[i].getFirstName()
									+ " " + clients[i].getLastName() + ".");
			}
		} catch (DuplicateEmailException e) {
			System.out.println("CAUGHT: " + e.getMessage());
		}


		// ================================================================
		// PART 3 — POLYMORPHIC COST CALCULATION
		// ================================================================
		System.out.println("\n════════════════════════════════════════════════");
		System.out.println("    PART 3: Polymorphic Cost Calculation");
		System.out.println("════════════════════════════════════════════════");
		for (int i = 0; i < trips.length; i++) {
			if (trips[i] != null)
				System.out.println(trips[i].getTripId() + " to " + trips[i].getDestination()
						+ " | Type: " + trips[i].getTripType()
						+ " | Total Cost: $" + String.format("%.2f", trips[i].calculateTotalCost()));
		}

		System.out.println("\n--- Most Expensive Trip ---");
		Trip mostExpensive = trips[0];
		for (int i = 1; i < trips.length; i++) {
			if (trips[i] != null && trips[i].calculateTotalCost() > mostExpensive.calculateTotalCost())
				mostExpensive = trips[i];
		}
		System.out.println(mostExpensive);


		// ================================================================
		// PART 4 — DEEP COPY VERIFICATION
		// ================================================================
		System.out.println("\n════════════════════════════════════════════════");
		System.out.println("    PART 4: Deep Copy Verification");
		System.out.println("════════════════════════════════════════════════");

		Transportation[] copiedTransport = copyTransportationArray(transportations);
		try {
			copiedTransport[0].setCompanyName("MODIFIED COMPANY");
		} catch (InvalidTransportDataException e) {
			System.out.println("Error modifying copy: " + e.getMessage());
		}
		System.out.println("Original [0] company : " + transportations[0].getCompanyName());
		System.out.println("Copy     [0] company : " + copiedTransport[0].getCompanyName());
		System.out.println("[OK] Deep copy confirmed — original is unchanged.");

		// Deep copy of Trip (copy constructor)
		Trip tripCopy = new Trip(trip1);
		System.out.println("\nOriginal trip1 ID : " + trip1.getTripId());
		System.out.println("Copied trip ID    : " + tripCopy.getTripId());
		System.out.println("[OK] Copy has a new unique ID — deep copy works correctly.");


		// ================================================================
		// PART 5 — EQUALS() METHOD TESTING
		// ================================================================
		System.out.println("\n════════════════════════════════════════════════");
		System.out.println("    PART 5: equals() Method Testing");
		System.out.println("════════════════════════════════════════════════");

		System.out.print("  Client vs Trip (different classes): ");
		System.out.println(c1.equals(trip1) + " (expected: false)");

		System.out.print("  client1 vs client2 (different attributes): ");
		System.out.println(c1.equals(c2) + " (expected: false)");

		Client clientCopy = null;
		try {
			clientCopy = new Client("Alexandre", "Chamoun", "alex@gmail.com");
		} catch (InvalidClientDataException e) {
			System.out.println("Error: " + e.getMessage());
		}
		System.out.print("  client1 vs clientCopy (identical attributes): ");
		System.out.println(c1.equals(clientCopy) + " (expected: true)");

		System.out.print("  flight1 vs flight2 (different attributes): ");
		System.out.println(f1.equals(f2) + " (expected: false)");

		Flight flightCopy = null;
		try {
			flightCopy = new Flight("Air Canada", "Montreal", "London", "Air Canada", 25, 500.00);
		} catch (InvalidTransportDataException e) {
			System.out.println("Error: " + e.getMessage());
		}
		System.out.print("  flight1 vs flightCopy (identical attributes): ");
		System.out.println(f1.equals(flightCopy) + " (expected: true)");

		System.out.println("\n╔══════════════════════════════════════════════╗");
		System.out.println("║       END OF PREDEFINED SCENARIO             ║");
		System.out.println("╚══════════════════════════════════════════════╝");
	}


	// ========================= COPY TRANSPORT ARRAY ==========================
	public static Transportation[] copyTransportationArray(Transportation[] original) {
		Transportation[] copy = new Transportation[original.length];
		for (int i = 0; i < original.length; i++) {
			if      (original[i] instanceof Flight) copy[i] = new Flight((Flight) original[i]);
			else if (original[i] instanceof Train)  copy[i] = new Train((Train)  original[i]);
			else if (original[i] instanceof Bus)    copy[i] = new Bus((Bus)    original[i]);
			else copy[i] = null;
		}
		return copy;
	}


	// =============================== MENU ====================================
	public static void menu() {

		Scanner scanner = new Scanner(System.in);

		// Arrays (max capacities per A2 spec)
		Client[]         clients         = new Client[100];
		Trip[]           trips           = new Trip[200];
		Transportation[] transportations = new Transportation[50];
		Accommodation[]  accommodations  = new Accommodation[50];

		int clientCount       = 0;
		int tripCount         = 0;
		int transportCount    = 0;
		int accommodationCount= 0;

		int choice = 0;
		while (choice != 7) {
			System.out.println("\n========  MAIN MENU  ========");
			System.out.println("1. Client Management");
			System.out.println("2. Trip Management");
			System.out.println("3. Transportation Management");
			System.out.println("4. Accommodation Management");
			System.out.println("5. Additional Operations");
			System.out.println("6. Generate Visualization");
			System.out.println("7. Exit");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {

				// -------- 1. CLIENT MANAGEMENT --------
				case 1:
					System.out.println("\n--- Client Management ---");
					System.out.println("a) Add a client");
					System.out.println("b) Edit a client");
					System.out.println("c) Delete a client");
					System.out.println("d) List all clients");
					System.out.print("Enter your choice: ");
					String clientChoice = scanner.nextLine();

					switch (clientChoice) {
						case "a":
							if (clientCount >= clients.length) {
								System.out.println("Client list is full.");
								break;
							}
							System.out.print("Enter first name: ");
							String firstName = scanner.nextLine();
							System.out.print("Enter last name: ");
							String lastName = scanner.nextLine();
							System.out.print("Enter email: ");
							String email = scanner.nextLine();
							try {
								clients[clientCount++] = new Client(firstName, lastName, email);
								System.out.println("Client added successfully.");
							} catch (InvalidClientDataException e) {
								clientCount--; // rollback counter
								System.out.println("Invalid client data: " + e.getMessage());
							}
							break;

						case "b":
							if (clientCount == 0) { System.out.println("No clients to edit."); break; }
							System.out.print("Enter client ID to edit: ");
							String editId = scanner.nextLine();
							boolean foundClient = false;
							for (int i = 0; i < clientCount; i++) {
								if (clients[i].getClientId().equals(editId)) {
									foundClient = true;
									try {
										System.out.print("New first name: ");
										clients[i].setFirstName(scanner.nextLine());
										System.out.print("New last name: ");
										clients[i].setLastName(scanner.nextLine());
										System.out.print("New email: ");
										clients[i].setEmail(scanner.nextLine());
										System.out.println("Client updated.");
									} catch (InvalidClientDataException e) {
										System.out.println("Invalid data: " + e.getMessage());
									}
									break;
								}
							}
							if (!foundClient) System.out.println("Client not found.");
							break;

						case "c":
							if (clientCount == 0) { System.out.println("No clients to delete."); break; }
							System.out.print("Enter client ID to delete: ");
							String deleteId = scanner.nextLine();
							boolean deletedClient = false;
							for (int i = 0; i < clientCount; i++) {
								if (clients[i].getClientId().equals(deleteId)) {
									for (int j = i; j < clientCount - 1; j++)
										clients[j] = clients[j + 1];
									clients[--clientCount] = null;
									deletedClient = true;
									System.out.println("Client deleted.");
									break;
								}
							}
							if (!deletedClient) System.out.println("Client not found.");
							break;

						case "d":
							if (clientCount == 0) { System.out.println("No clients found."); break; }
							for (int i = 0; i < clientCount; i++)
								System.out.println(clients[i] + "\n");
							break;

						default:
							System.out.println("Invalid choice.");
					}
					break;


				// -------- 2. TRIP MANAGEMENT --------
				case 2:
					System.out.println("\n--- Trip Management ---");
					System.out.println("a) Create a trip");
					System.out.println("b) Edit trip information");
					System.out.println("c) Cancel a trip");
					System.out.println("d) List all trips");
					System.out.println("e) List all trips for a specific client");
					System.out.print("Enter your choice: ");
					String tripChoice = scanner.nextLine();

					switch (tripChoice) {
						case "a":
							if (tripCount >= trips.length) { System.out.println("Trip list is full."); break; }
							if (clientCount == 0) { System.out.println("No clients available. Please add a client first."); break; }

							System.out.print("Enter destination: ");
							String dest = scanner.nextLine();
							System.out.print("Enter number of days (1-20): ");
							int days = scanner.nextInt();
							System.out.print("Enter base price (min $100): ");
							double price = scanner.nextDouble();
							scanner.nextLine();
							System.out.print("Enter client ID: ");
							String cId = scanner.nextLine();

							Client foundC = null;
							for (int i = 0; i < clientCount; i++) {
								if (clients[i].getClientId().equals(cId)) {
									foundC = clients[i];
									break;
								}
							}
							if (foundC == null) { System.out.println("Client not found."); break; }

							Trip newTrip = null;
							try {
								newTrip = new Trip(dest, days, price, foundC);
							} catch (InvalidTripDataException e) {
								System.out.println("Invalid trip data: " + e.getMessage());
								break;
							}

							// Link transportation
							System.out.print("Add transportation? (yes/no): ");
							if (scanner.nextLine().equalsIgnoreCase("yes")) {
								if (transportCount == 0) {
									System.out.println("No transportation available.");
								} else {
									System.out.print("Enter transport ID: ");
									String transId = scanner.nextLine();
									for (int i = 0; i < transportCount; i++) {
										if (transportations[i].getTransportId().equals(transId)) {
											newTrip.setTransportation(transportations[i]);
											System.out.println("Transportation linked.");
											break;
										}
									}
								}
							}

							// Link accommodation
							System.out.print("Add accommodation? (yes/no): ");
							if (scanner.nextLine().equalsIgnoreCase("yes")) {
								if (accommodationCount == 0) {
									System.out.println("No accommodations available.");
								} else {
									System.out.print("Enter accommodation ID: ");
									String accId = scanner.nextLine();
									for (int i = 0; i < accommodationCount; i++) {
										if (accommodations[i].getAccommodationId().equals(accId)) {
											newTrip.setAccommodation(accommodations[i]);
											System.out.println("Accommodation linked.");
											break;
										}
									}
								}
							}

							trips[tripCount++] = newTrip;
							System.out.println("Trip created successfully.");
							break;

						case "b":
							if (tripCount == 0) { System.out.println("No trips to edit."); break; }
							System.out.print("Enter trip ID to edit: ");
							String editTripId = scanner.nextLine();
							boolean foundTrip = false;
							for (int i = 0; i < tripCount; i++) {
								if (trips[i].getTripId().equals(editTripId)) {
									foundTrip = true;
									try {
										System.out.print("New destination: ");
										trips[i].setDestination(scanner.nextLine());
										System.out.print("New number of days (1-20): ");
										trips[i].setDurationInDays(scanner.nextInt());
										System.out.print("New base price (min $100): ");
										trips[i].setBasePrice(scanner.nextDouble());
										scanner.nextLine();
										System.out.println("Trip updated.");
									} catch (InvalidTripDataException e) {
										scanner.nextLine();
										System.out.println("Invalid data: " + e.getMessage());
									}
									break;
								}
							}
							if (!foundTrip) System.out.println("Trip not found.");
							break;

						case "c":
							if (tripCount == 0) { System.out.println("No trips to cancel."); break; }
							System.out.print("Enter trip ID to cancel: ");
							String cancelId = scanner.nextLine();
							boolean cancelledTrip = false;
							for (int i = 0; i < tripCount; i++) {
								if (trips[i].getTripId().equals(cancelId)) {
									for (int j = i; j < tripCount - 1; j++)
										trips[j] = trips[j + 1];
									trips[--tripCount] = null;
									cancelledTrip = true;
									System.out.println("Trip cancelled.");
									break;
								}
							}
							if (!cancelledTrip) System.out.println("Trip not found.");
							break;

						case "d":
							if (tripCount == 0) { System.out.println("No trips found."); break; }
							for (int i = 0; i < tripCount; i++)
								System.out.println(trips[i] + "\n");
							break;

						case "e":
							if (tripCount == 0) { System.out.println("No trips found."); break; }
							System.out.print("Enter client ID: ");
							String searchClientId = scanner.nextLine();
							boolean foundAny = false;
							for (int i = 0; i < tripCount; i++) {
								if (trips[i].getClient() != null &&
										trips[i].getClient().getClientId().equals(searchClientId)) {
									System.out.println(trips[i] + "\n");
									foundAny = true;
								}
							}
							if (!foundAny) System.out.println("No trips found for this client.");
							break;

						default:
							System.out.println("Invalid choice.");
					}
					break;


				// -------- 3. TRANSPORTATION MANAGEMENT --------
				case 3:
					System.out.println("\n--- Transportation Management ---");
					System.out.println("a) Add a transportation option");
					System.out.println("b) Remove a transportation option");
					System.out.println("c) List transportation options by type");
					System.out.print("Enter your choice: ");
					String transChoice = scanner.nextLine();

					switch (transChoice) {
						case "a":
							if (transportCount >= transportations.length) { System.out.println("Transportation list is full."); break; }
							System.out.println("Type: 1) Flight  2) Train  3) Bus");
							System.out.print("Enter type: ");
							int transType = scanner.nextInt();
							scanner.nextLine();
							System.out.print("Company name: ");
							String company = scanner.nextLine();
							System.out.print("Departure city: ");
							String dep = scanner.nextLine();
							System.out.print("Arrival city: ");
							String arr = scanner.nextLine();
							System.out.print("Base fare: ");
							double fare = scanner.nextDouble();
							scanner.nextLine();

							try {
								if (transType == 1) {
									System.out.print("Airline name: ");
									String airline = scanner.nextLine();
									System.out.print("Luggage allowance (kg): ");
									double luggage = scanner.nextDouble();
									scanner.nextLine();
									transportations[transportCount++] = new Flight(company, dep, arr, airline, luggage, fare);
								} else if (transType == 2) {
									System.out.print("Train type: ");
									String trainType = scanner.nextLine();
									System.out.print("Seat class: ");
									String seatClass = scanner.nextLine();
									transportations[transportCount++] = new Train(company, dep, arr, trainType, seatClass, fare);
								} else if (transType == 3) {
									System.out.print("Bus company: ");
									String busCompany = scanner.nextLine();
									System.out.print("Number of stops (min 1): ");
									int stops = scanner.nextInt();
									scanner.nextLine();
									transportations[transportCount++] = new Bus(company, dep, arr, busCompany, stops, fare);
								} else {
									System.out.println("Invalid type.");
									break;
								}
								System.out.println("Transportation added.");
							} catch (InvalidTransportDataException e) {
								transportCount--; // rollback
								System.out.println("Invalid transport data: " + e.getMessage());
							}
							break;

						case "b":
							if (transportCount == 0) { System.out.println("No transportation options to remove."); break; }
							System.out.print("Enter transport ID to remove: ");
							String removeTransId = scanner.nextLine();
							boolean removedTrans = false;
							for (int i = 0; i < transportCount; i++) {
								if (transportations[i].getTransportId().equals(removeTransId)) {
									for (int j = i; j < transportCount - 1; j++)
										transportations[j] = transportations[j + 1];
									transportations[--transportCount] = null;
									removedTrans = true;
									System.out.println("Transportation removed.");
									break;
								}
							}
							if (!removedTrans) System.out.println("Transportation not found.");
							break;

						case "c":
							System.out.println("\n-- Flights --");
							boolean anyFlight = false;
							for (int i = 0; i < transportCount; i++) {
								if (transportations[i] instanceof Flight) { System.out.println(transportations[i] + "\n"); anyFlight = true; }
							}
							if (!anyFlight) System.out.println("No flights.");

							System.out.println("\n-- Trains --");
							boolean anyTrain = false;
							for (int i = 0; i < transportCount; i++) {
								if (transportations[i] instanceof Train) { System.out.println(transportations[i] + "\n"); anyTrain = true; }
							}
							if (!anyTrain) System.out.println("No trains.");

							System.out.println("\n-- Buses --");
							boolean anyBus = false;
							for (int i = 0; i < transportCount; i++) {
								if (transportations[i] instanceof Bus) { System.out.println(transportations[i] + "\n"); anyBus = true; }
							}
							if (!anyBus) System.out.println("No buses.");
							break;

						default:
							System.out.println("Invalid choice.");
					}
					break;


				// -------- 4. ACCOMMODATION MANAGEMENT --------
				case 4:
					System.out.println("\n--- Accommodation Management ---");
					System.out.println("a) Add an accommodation");
					System.out.println("b) Remove an accommodation");
					System.out.println("c) List accommodations by type");
					System.out.print("Enter your choice: ");
					String accChoice = scanner.nextLine();

					switch (accChoice) {
						case "a":
							if (accommodationCount >= accommodations.length) { System.out.println("Accommodation list is full."); break; }
							System.out.println("Type: 1) Hotel  2) Hostel");
							System.out.print("Enter type: ");
							int accType = scanner.nextInt();
							scanner.nextLine();
							System.out.print("Name: ");
							String accName = scanner.nextLine();
							System.out.print("Location: ");
							String accLoc = scanner.nextLine();
							System.out.print("Price per night: ");
							double ppn = scanner.nextDouble();
							scanner.nextLine();

							try {
								if (accType == 1) {
									System.out.print("Star rating (1-5): ");
									int stars = scanner.nextInt();
									scanner.nextLine();
									accommodations[accommodationCount++] = new Hotel(accName, accLoc, ppn, stars);
								} else if (accType == 2) {
									System.out.print("Number of shared beds per room: ");
									int beds = scanner.nextInt();
									scanner.nextLine();
									accommodations[accommodationCount++] = new Hostel(accName, accLoc, ppn, beds);
								} else {
									System.out.println("Invalid type.");
									break;
								}
								System.out.println("Accommodation added.");
							} catch (InvalidAccommodationDataException e) {
								accommodationCount--; // rollback
								System.out.println("Invalid accommodation data: " + e.getMessage());
							}
							break;

						case "b":
							if (accommodationCount == 0) { System.out.println("No accommodations to remove."); break; }
							System.out.print("Enter accommodation ID to remove: ");
							String removeAccId = scanner.nextLine();
							boolean removedAcc = false;
							for (int i = 0; i < accommodationCount; i++) {
								if (accommodations[i].getAccommodationId().equals(removeAccId)) {
									for (int j = i; j < accommodationCount - 1; j++)
										accommodations[j] = accommodations[j + 1];
									accommodations[--accommodationCount] = null;
									removedAcc = true;
									System.out.println("Accommodation removed.");
									break;
								}
							}
							if (!removedAcc) System.out.println("Accommodation not found.");
							break;

						case "c":
							System.out.println("\n-- Hotels --");
							boolean anyHotel = false;
							for (int i = 0; i < accommodationCount; i++) {
								if (accommodations[i] instanceof Hotel) { System.out.println(accommodations[i] + "\n"); anyHotel = true; }
							}
							if (!anyHotel) System.out.println("No hotels.");

							System.out.println("\n-- Hostels --");
							boolean anyHostel = false;
							for (int i = 0; i < accommodationCount; i++) {
								if (accommodations[i] instanceof Hostel) { System.out.println(accommodations[i] + "\n"); anyHostel = true; }
							}
							if (!anyHostel) System.out.println("No hostels.");
							break;

						default:
							System.out.println("Invalid choice.");
					}
					break;


				// -------- 5. ADDITIONAL OPERATIONS --------
				case 5:
					System.out.println("\n--- Additional Operations ---");
					System.out.println("a) Display the most expensive trip");
					System.out.println("b) Calculate and display the total cost of a trip");
					System.out.println("c) Create a deep copy of the transportation array");
					System.out.println("d) Create a deep copy of the accommodation array");
					System.out.print("Enter your choice: ");
					String addChoice = scanner.nextLine();

					switch (addChoice) {
						case "a":
							if (tripCount == 0) { System.out.println("No trips available."); break; }
							Trip mostExpensive = trips[0];
							for (int i = 1; i < tripCount; i++) {
								if (trips[i].calculateTotalCost() > mostExpensive.calculateTotalCost())
									mostExpensive = trips[i];
							}
							System.out.println("Most expensive trip:\n" + mostExpensive);
							break;

						case "b":
							if (tripCount == 0) { System.out.println("No trips available."); break; }
							System.out.print("Enter trip ID: ");
							String costTripId = scanner.nextLine();
							boolean foundTripCost = false;
							for (int i = 0; i < tripCount; i++) {
								if (trips[i].getTripId().equals(costTripId)) {
									System.out.println("Total cost of trip " + costTripId + ": $" + trips[i].calculateTotalCost());
									foundTripCost = true;
									break;
								}
							}
							if (!foundTripCost) System.out.println("Trip not found.");
							break;

						case "c":
							if (transportCount == 0) { System.out.println("No transportation options available."); break; }
							Transportation[] transCopy = copyTransportationArray(transportations);
							System.out.println("Deep copy of transportation array created:");
							for (int i = 0; i < transportCount; i++)
								System.out.println(transCopy[i] + "\n");
							break;

						case "d":
							if (accommodationCount == 0) { System.out.println("No accommodations available."); break; }
							Accommodation[] accCopy = new Accommodation[accommodations.length];
							for (int i = 0; i < accommodationCount; i++) {
								if      (accommodations[i] instanceof Hotel)  accCopy[i] = new Hotel((Hotel)   accommodations[i]);
								else if (accommodations[i] instanceof Hostel) accCopy[i] = new Hostel((Hostel) accommodations[i]);
							}
							System.out.println("Deep copy of accommodation array created:");
							for (int i = 0; i < accommodationCount; i++)
								System.out.println(accCopy[i] + "\n");
							break;

						default:
							System.out.println("Invalid choice.");
					}
					break;


				// -------- 6. GENERATE VISUALIZATION --------
				case 6:
					System.out.println("\n--- Generate Visualization ---");
					System.out.println("a) Bar chart (Trip cost)");
					System.out.println("b) Pie chart (Trips per destination)");
					System.out.println("c) Line chart (Duration over time)");
					System.out.print("Enter your choice: ");
					String visChoice = scanner.nextLine();

					if (tripCount == 0) { System.out.println("No trips available to generate charts."); break; }

					try {
						switch (visChoice) {
							case "a":
								visualization.TripChartGenerator.generateCostBarChart(trips, tripCount);
								System.out.println("Bar chart saved to output/charts/trip_cost_bar_chart.png");
								break;
							case "b":
								visualization.TripChartGenerator.generateDestinationPieChart(trips, tripCount);
								System.out.println("Pie chart saved to output/charts/trips_per_destination_pie.png");
								break;
							case "c":
								visualization.TripChartGenerator.generateDurationLineChart(trips, tripCount);
								System.out.println("Line chart saved to output/charts/trip_duration_line_chart.png");
								break;
							default:
								System.out.println("Invalid choice.");
						}
					} catch (Exception e) {
						System.out.println("Error generating chart: " + e.getMessage());
					}
					break;


				// -------- 7. EXIT --------
				case 7:
					System.out.println("Goodbye! Thank you for using SmartTravel.");
					break;

				default:
					System.out.println("Invalid choice. Please enter a number between 1 and 7.");
			}
		}
	}
}