// -------------------------------------------------------------
// Assignment 1
// Part: Driver Class
// Written By: Alexandre Chamoun
// Student ID: 40341371
// -------------------------------------------------------------
// Main driver class for the SmartTravel application.
// Provides a menu-driven interface and a predefined testing scenario.
// All methods are static — this class is never instantiated.

/*
The main class that runs the program.
When launched, it asks the user to choose between two modes.
All methods are static — the class is never instantiated. Contains:
	welcomeMessage() — displays the welcome message
	menu() — fully interactive menu with client, trip, transportation, accommodation management, additional operations, and visualization
	predefinedScenario() — hardcoded testing scenario that creates objects, tests equals(), demonstrates polymorphic cost calculation, finds the most expensive trip, and proves deep copy works
	copyTransportationArray() — required static method that deep copies a Transportation array using copy constructors
 */

package driver;

// Imports
import java.util.Scanner;

//Import the following because they are in different packages.
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
		
		// Scanner Initialization
		Scanner scanner = new Scanner(System.in);
		
		// Attributes
		int userChoice;
		
		welcomeMessage();
		System.out.println();
		System.out.println("Menu (1) or Predefined Testing Scenario (2): ");
		promptUserForChoice();
		userChoice = scanner.nextInt();
		
		switch(userChoice) {
		case 1: menu(); break;
		case 2: predefinedScenario(); break;
		}
		

		scanner.close();
		
	}

	
	// ============================ STATIC METHODS =============================
	// WELCOME MESSAGE STATIC METHOD:
	public static void welcomeMessage() {
		System.out.print("Welcome to my SmartTravel Agency! Made by Alexandre Chamoun.");
	}
	
	// PROMPT USER FOR CHOICE STATIC METHOD:
	public static void promptUserForChoice() {
		System.out.println("What do you want to do? ");
	}
	
	
	// PREDEFINED SCENARIO STATIC METHOD:
	public static void predefinedScenario() {
		System.out.println("========  PREDEFINED SCENARIO  ========");
		
		// Create Clients
		Client c1 = new Client("Alexandre","Chamoun", "alex@gmail.com");
		Client c2 = new Client("John","Doe", "doe@gmail.com");
		Client c3 = new Client("Marc","Smith", "marc@gmail.com");
		
		Client[] clients = new Client[3];
		clients[0] = c1;
		clients[1] = c2;
		clients[2] = c3;
		
		System.out.println("=== CLIENTS ===");
		for (int i = 0; i < clients.length ; i++) {
			System.out.println(clients[i]);
			System.out.println("----------");
		}
		
		System.out.println();  // skip a ligne for aesthetic.
		
		
		// Create Transportation Options
		Flight f1 = new Flight("Air Canada", "Montreal", "London", "Air Canada", 25, 500.00);
		Flight f2 = new Flight("Porter", "Montreal", "Fort Lauderdale", "Porter Airlines", 15, 275.00);
		Train t1 = new Train("Eurostar", "Paris", "Nice", "Regional", "Economy", 80.00);
		Train t2 = new Train("Alta Velocidad Española", "Madrid", "Barcelona", "High-Speed", "First Class", 110.00);
		Bus b1 = new Bus("FlixBus", "Berlin", "Amsterdam", "FlixBus", 4, 24.99);
		Bus b2 = new Bus("MegaBus", "Chicago", "Indianapolis", "MegaBus", 3, 14.99);
		
		Transportation[] transportations = new Transportation[6];
		transportations[0] = f1;
		transportations[1] = f2;
		transportations[2] = t1;
		transportations[3] = t2;
		transportations[4] = b1;
		transportations[5] = b2;
		
		System.out.println("=== TRANSPORTATIONS ===");
		for (int i = 0; i < transportations.length; i++) {
	        System.out.println(transportations[i]);
	        System.out.println("----------");
		}
		
		System.out.println(); // skip a ligne for aesthetic.
		
		
		// Create Accommodation Options
		Hotel h1 = new Hotel("Marriott", "Paris", 200.00, 5);
		Hotel h2 = new Hotel("Holiday Inn", "London", 120.00, 3);
		Hostel hs1 = new Hostel("HI Hostel", "Amsterdam", 35.00, 6);
		Hostel hs2 = new Hostel("Generator", "Nice", 40.00, 8);
		
		Accommodation[] accommodations = new Accommodation[4];
		accommodations[0] = h1;
		accommodations[1] = h2;
		accommodations[2] = hs1;
		accommodations[3] = hs2;
		
		System.out.println("=== ACCOMMODATIONS ===");
		for (int i = 0 ; i < accommodations.length ; i++) {
			System.out.println(accommodations[i]);
			System.out.println("----------");
		}
		
		System.out.println(); // skip a ligne for aesthetic.

		
		// Create Trip Options
		Trip trip1 = new Trip("London", 5, 100.00, c1);
		trip1.setTransportation(f1);
		trip1.setAccommodation(h2);
		
		Trip trip2 = new Trip("Amsterdam", 3, 45.00, c2);
		trip2.setTransportation(b1);
		trip2.setAccommodation(hs1);
		
		Trip trip3 = new Trip("Nice", 5, 60.00, c3);
		trip3.setTransportation(t1);
		trip3.setAccommodation(hs2);
		
		Trip[] trips = new Trip[3];
		trips[0] = trip1;
		trips[1] = trip2;
		trips[2] = trip3;
		
		System.out.println("=== TRIPS ===");
		for (int i = 0 ; i < trips.length ; i++) {
			System.out.println(trips[i]);
			System.out.println("----------");
		}
		
		System.out.println(); // skip a ligne for aesthetic.

		
		// ================== Testing Equals() method =====================
		System.out.println("=== Testing the Equals method ===");
		// CASE 1: Compare objects.
		System.out.print("Comparing Client vs Trip (different classes): ");
		System.out.println(c1.equals(trip1));
		
		// CASE 2: Compare same class with diff attributes:
		System.out.print("Comparing client1 vs client2 (same class, different attributes): ");
	    System.out.println(c1.equals(c2));  // expected: false
	    
	    // CASE 3: Compare same class with identical attributes
	    Client clientCopy = new Client("Alexandre", "Chamoun", "alex@gmail.com");
	    System.out.print("Comparing client1 vs clientCopy (same class, identical attributes): ");
	    System.out.println(c1.equals(clientCopy)); // expected: true
	    
	    // Also test on transportation
	    System.out.print("Comparing flight1 vs flight2 (same class, different attributes): ");
	    System.out.println(f1.equals(f2));  // expected: false

	    Flight flightCopy = new Flight("Air Canada", "Montreal", "London", "Air Canada", 25, 500.00);
	    System.out.print("Comparing flight1 vs flightCopy (same class, identical attributes): ");
	    System.out.println(f1.equals(flightCopy));  // expected: true
	    
	    System.out.println(); // skip a ligne for aesthetic.
	    
	    
	    // ================= CALCULATE TOTAL COST =================
	    System.out.println("=== Polymorphic Cost Calculation ===");
	    for (int i = 0; i < trips.length; i++) {
	        System.out.println(trips[i].getTripId() + " to " + trips[i].getDestination() 
	        					+ " - Type: " + trips[i].getTripType() 
	        					+ " - Total Cost: $" + trips[i].calculateTotalCost());
	    }
	    
	    System.out.println(); // skip a ligne for aesthetic.
	    
	    // ================= MOST EXPENSIVE TRIP =================
	    System.out.println("=== Most Expensive Trip ===");
	    Trip mostExpensive = trips[0];
	    for (int i = 1; i < trips.length; i++) {
	        if (trips[i].calculateTotalCost() > mostExpensive.calculateTotalCost())
	            mostExpensive = trips[i];
	    }
	    System.out.println("Most expensive trip: " + mostExpensive);
	    
	    System.out.println(); // skip a ligne for aesthetic.
	    
	    
	    // ================= DEEP COPY OF TRANSPORTATION ARRAY =================
	    System.out.println("=== Deep Copy of Transportation Array ===");
	    Transportation[] copiedArray = copyTransportationArray(transportations);

	    // Modify the copy
	    copiedArray[0].setCompanyName("MODIFIED COMPANY");

	    // Display both to prove original is unchanged
	    System.out.println("Original [0]: " + transportations[0]);
	    System.out.println("Copy    [0]: " + copiedArray[0]);

	    System.out.println("\n========== END OF PREDEFINED SCENARIO ==========\n");
	}
		

	
	// COPY TRANSPORTATION ARRAY STATIC METHOD:
	public static Transportation[] copyTransportationArray(Transportation[] original) {
		// Create a new array of the same size as the original.
		// This is a new independent array in memory — not a copy of the reference.
		Transportation[] copy = new Transportation[original.length];

		// Transportation is abstract so we cannot do new Transportation().
		// We use instanceof to identify the concrete type of each element
		// at runtime, then cast it and call the appropriate copy constructor.
		for (int i = 0 ; i < original.length ; i++) {
			// In this first case... if the current element is a Flight, cast it to Flight
			// and create a brand new Flight using the Flight copy constructor.
			if (original[i] instanceof Flight) {
				copy[i] = new Flight((Flight) original[i]);
			}
			else if (original[i] instanceof Train) {
				copy[i] = new Train((Train) original[i]);
			}
			else if (original[i] instanceof Bus) {
				copy[i] = new Bus((Bus) original[i]);
			}
			else {
				copy[i] = null;
			}		
		}
		return copy;
		
	}
	
	
	// MENU STATIC METHOD:
	public static void menu() {

		// Scanner initializing
	    Scanner scanner = new Scanner(System.in);

	    // Arrays to store objects (max 100 each)
		// so we must use counters below to track how many are actually stored.
		Client[] clients = new Client[100];
	    Trip[] trips = new Trip[100];
	    Transportation[] transportations = new Transportation[100];
	    Accommodation[] accommodations = new Accommodation[100];

	    // Counters to track how many objects are in each array
	    int clientCount = 0;
	    int tripCount = 0;
	    int transportCount = 0;
	    int accommodationCount = 0;

	    // User choice from 1-7 in the main menu
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

			// Consume the leftover newline character after nextInt().
			// Without this, the next scanner.nextLine() call would read an empty string instead of waiting for user input.
	        scanner.nextLine();


	        switch (choice) {
	            // 1. CLIENT MANAGEMENT
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
							// Create a new Client and store it at the current count index.
							// Then increment the counter so the next client goes in the next slot.
	                        clients[clientCount++] = new Client(firstName, lastName, email);
	                        System.out.println("Client added successfully.");
	                        break;

	                    case "b":
	                        if (clientCount == 0) {
	                            System.out.println("No clients to edit.");
	                            break;
	                        }
	                        System.out.print("Enter client ID to edit: ");
	                        String editId = scanner.nextLine();
	                        boolean foundClient = false;
							// Loop through the array to find the client with the matching ID.
							for (int i = 0; i < clientCount; i++) {
	                            if (clients[i].getClientId().equals(editId)) {
	                                foundClient = true;
	                                System.out.print("New first name: ");
	                                clients[i].setFirstName(scanner.nextLine());
	                                System.out.print("New last name: ");
	                                clients[i].setLastName(scanner.nextLine());
	                                System.out.print("New email: ");
	                                clients[i].setEmail(scanner.nextLine());
	                                System.out.println("Client updated.");
	                                break;
	                            }
	                        }
	                        if (!foundClient) System.out.println("Client not found.");
	                        break;

	                    case "c":
	                        if (clientCount == 0) {
	                            System.out.println("No clients to delete.");
	                            break;
	                        }
	                        System.out.print("Enter client ID to delete: ");
	                        String deleteId = scanner.nextLine();
	                        boolean deletedClient = false;
	                        for (int i = 0; i < clientCount; i++) {
	                            if (clients[i].getClientId().equals(deleteId)) {
	                                // Shift elements left
	                                for (int j = i; j < clientCount - 1; j++)
	                                    clients[j] = clients[j + 1];
									// Set the last element to null since it was shifted left.
									// Decrement the counter since we now have one fewer client.
	                                clients[--clientCount] = null;
	                                deletedClient = true;
	                                System.out.println("Client deleted.");
	                                break;
	                            }
	                        }
	                        if (!deletedClient) System.out.println("Client not found.");
	                        break;

	                    case "d":
	                        if (clientCount == 0) {
	                            System.out.println("No clients found.");
	                            break;
	                        }
							// Loop only up to clientCount, not clients.length,
							// because everything beyond clientCount is null.
	                        for (int i = 0; i < clientCount; i++)
	                            System.out.println(clients[i] + "\n");
	                        break;

	                    default:
	                        System.out.println("Invalid choice.");
	                }
	                break;

	            // 2. TRIP MANAGEMENT
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
	                    if (tripCount >= trips.length) {
	                        System.out.println("Trip list is full.");
	                        break;
	                    }
	                    if (clientCount == 0) {
	                        System.out.println("No clients available. Please add a client first.");
	                        break;
	                    }
	                    System.out.print("Enter destination: ");
	                    String dest = scanner.nextLine();
	                    System.out.print("Enter number of days: ");
	                    int days = scanner.nextInt();
	                    System.out.print("Enter base price: ");
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
	                    if (foundC == null) {
	                        System.out.println("Client not found.");
	                        break;
	                    }

	                    Trip newTrip = new Trip(dest, days, price, foundC);

	                    // Link transportation
	                    System.out.print("Add transportation? (yes/no): ");
	                    String addTrans = scanner.nextLine();
	                    if (addTrans.equalsIgnoreCase("yes")) {
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
	                    String addAcc = scanner.nextLine();
	                    if (addAcc.equalsIgnoreCase("yes")) {
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
	                        if (tripCount == 0) {
	                            System.out.println("No trips to edit.");
	                            break;
	                        }
	                        System.out.print("Enter trip ID to edit: ");
	                        String editTripId = scanner.nextLine();
	                        boolean foundTrip = false;
	                        for (int i = 0; i < tripCount; i++) {
	                            if (trips[i].getTripId().equals(editTripId)) {
	                                foundTrip = true;
	                                System.out.print("New destination: ");
	                                trips[i].setDestination(scanner.nextLine());
	                                System.out.print("New number of days: ");
	                                trips[i].setDurationInDays(scanner.nextInt());
	                                System.out.print("New base price: ");
	                                trips[i].setBasePrice(scanner.nextDouble());
	                                scanner.nextLine();
	                                System.out.println("Trip updated.");
	                                break;
	                            }
	                        }
	                        if (!foundTrip) System.out.println("Trip not found.");
	                        break;

	                    case "c":
	                        if (tripCount == 0) {
	                            System.out.println("No trips to cancel.");
	                            break;
	                        }
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
	                        if (tripCount == 0) {
	                            System.out.println("No trips found.");
	                            break;
	                        }
	                        for (int i = 0; i < tripCount; i++)
	                            System.out.println(trips[i] + "\n");
	                        break;

	                    case "e":
	                        if (tripCount == 0) {
	                            System.out.println("No trips found.");
	                            break;
	                        }
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

	            // 3. TRANSPORTATION MANAGEMENT
	            case 3:
	                System.out.println("\n--- Transportation Management ---");
	                System.out.println("a) Add a transportation option");
	                System.out.println("b) Remove a transportation option");
	                System.out.println("c) List transportation options by type");
	                System.out.print("Enter your choice: ");
	                String transChoice = scanner.nextLine();

	                switch (transChoice) {
	                    case "a":
	                        if (transportCount >= transportations.length) {
	                            System.out.println("Transportation list is full.");
	                            break;
	                        }
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
	                            System.out.print("Number of stops: ");
	                            int stops = scanner.nextInt();
	                            scanner.nextLine();
	                            transportations[transportCount++] = new Bus(company, dep, arr, busCompany, stops, fare);
	                        } else {
	                            System.out.println("Invalid type.");
	                        }
	                        System.out.println("Transportation added.");
	                        break;

	                    case "b":
	                        if (transportCount == 0) {
	                            System.out.println("No transportation options to remove.");
	                            break;
	                        }
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
	                            if (transportations[i] instanceof Flight) {
	                                System.out.println(transportations[i] + "\n");
	                                anyFlight = true;
	                            }
	                        }
	                        if (!anyFlight) System.out.println("No flights.");

	                        System.out.println("\n-- Trains --");
	                        boolean anyTrain = false;
	                        for (int i = 0; i < transportCount; i++) {
	                            if (transportations[i] instanceof Train) {
	                                System.out.println(transportations[i] + "\n");
	                                anyTrain = true;
	                            }
	                        }
	                        if (!anyTrain) System.out.println("No trains.");

	                        System.out.println("\n-- Buses --");
	                        boolean anyBus = false;
	                        for (int i = 0; i < transportCount; i++) {
	                            if (transportations[i] instanceof Bus) {
	                                System.out.println(transportations[i] + "\n");
	                                anyBus = true;
	                            }
	                        }
	                        if (!anyBus) System.out.println("No buses.");
	                        break;

	                    default:
	                        System.out.println("Invalid choice.");
	                }
	                break;

	            // 4. ACCOMMODATION MANAGEMENT
	            case 4:
	                System.out.println("\n--- Accommodation Management ---");
	                System.out.println("a) Add an accommodation");
	                System.out.println("b) Remove an accommodation");
	                System.out.println("c) List accommodations by type");
	                System.out.print("Enter your choice: ");
	                String accChoice = scanner.nextLine();

	                switch (accChoice) {
	                    case "a":
	                        if (accommodationCount >= accommodations.length) {
	                            System.out.println("Accommodation list is full.");
	                            break;
	                        }
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

	                        if (accType == 1) {
	                            System.out.print("Star rating: ");
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
	                        }
	                        System.out.println("Accommodation added.");
	                        break;

	                    case "b":
	                        if (accommodationCount == 0) {
	                            System.out.println("No accommodations to remove.");
	                            break;
	                        }
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
	                            if (accommodations[i] instanceof Hotel) {
	                                System.out.println(accommodations[i] + "\n");
	                                anyHotel = true;
	                            }
	                        }
	                        if (!anyHotel) System.out.println("No hotels.");

	                        System.out.println("\n-- Hostels --");
	                        boolean anyHostel = false;
	                        for (int i = 0; i < accommodationCount; i++) {
	                            if (accommodations[i] instanceof Hostel) {
	                                System.out.println(accommodations[i] + "\n");
	                                anyHostel = true;
	                            }
	                        }
	                        if (!anyHostel) System.out.println("No hostels.");
	                        break;

	                    default:
	                        System.out.println("Invalid choice.");
	                }
	                break;

	            // 5. ADDITIONAL OPERATIONS
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
	                        if (tripCount == 0) {
	                            System.out.println("No trips available.");
	                            break;
	                        }
	                        Trip mostExpensive = trips[0];
	                        for (int i = 1; i < tripCount; i++) {
	                            if (trips[i].calculateTotalCost() > mostExpensive.calculateTotalCost())
	                                mostExpensive = trips[i];
	                        }
	                        System.out.println("Most expensive trip:\n" + mostExpensive);
	                        break;

	                    case "b":
	                        if (tripCount == 0) {
	                            System.out.println("No trips available.");
	                            break;
	                        }
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
	                        if (transportCount == 0) {
	                            System.out.println("No transportation options available.");
	                            break;
	                        }
	                        Transportation[] transCopy = copyTransportationArray(transportations);
	                        System.out.println("Deep copy of transportation array created:");
	                        for (int i = 0; i < transportCount; i++)
	                            System.out.println(transCopy[i] + "\n");
	                        break;

	                    case "d":
	                        if (accommodationCount == 0) {
	                            System.out.println("No accommodations available.");
	                            break;
	                        }
	                        Accommodation[] accCopy = new Accommodation[accommodations.length];
	                        for (int i = 0; i < accommodationCount; i++) {
	                            if (accommodations[i] instanceof Hotel)
	                                accCopy[i] = new Hotel((Hotel) accommodations[i]);
	                            else if (accommodations[i] instanceof Hostel)
	                                accCopy[i] = new Hostel((Hostel) accommodations[i]);
	                        }
	                        System.out.println("Deep copy of accommodation array created:");
	                        for (int i = 0; i < accommodationCount; i++)
	                            System.out.println(accCopy[i] + "\n");
	                        break;

	                    default:
	                        System.out.println("Invalid choice.");
	                }
	                break;

	            // 6. GENERATE VISUALIZATION
	            case 6:
	                System.out.println("\n--- Generate Visualization ---");
	                System.out.println("a) Bar chart (Trip cost)");
	                System.out.println("b) Pie chart (Trips per destination)");
	                System.out.println("c) Line chart (Duration over time)");
	                System.out.print("Enter your choice: ");
	                String visChoice = scanner.nextLine();

	                if (tripCount == 0) {
	                    System.out.println("No trips available to generate charts.");
	                    break;
	                }

	                try {
	                    switch (visChoice) {
	                        case "a":
	                            visualization.TripChartGenerator.generateCostBarChart(trips, tripCount);
	                            System.out.println("Bar chart saved to output/trip_cost_bar_chart.png");
	                            break;
	                        case "b":
	                            visualization.TripChartGenerator.generateDestinationPieChart(trips, tripCount);
	                            System.out.println("Pie chart saved to output/trips_per_destination_pie.png");
	                            break;
	                        case "c":
	                            visualization.TripChartGenerator.generateDurationLineChart(trips, tripCount);
	                            System.out.println("Line chart saved to output/trip_duration_line_chart.png");
	                            break;
	                        default:
	                            System.out.println("Invalid choice.");
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error generating chart: " + e.getMessage());
	                }
	                break;

	            // 7. EXIT
	            case 7:
	                System.out.println("Goodbye! Thank you for using SmartTravel.");
	                break;

	            default:
	                System.out.println("Invalid choice. Please enter a number between 1 and 7.");
	        }
	    }
	}

}
