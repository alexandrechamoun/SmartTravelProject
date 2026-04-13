// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Main driver for SmartTravel A3.
// A2 menus 1-6 and 8-11 are unchanged.
// A3 change: Menu 7 is now the Advanced Analytics menu (7.1-7.6).

package driver;

import java.util.List;
import java.util.Scanner;

import client.Client;
import travel.*;
import exceptions.*;
import service.Repository;
import service.RecentList;
import service.SmartTravelService;
import visualization.DashboardGenerator;
import visualization.TripChartGenerator;

public class SmartTravelDriver {

    // ===================== MAIN =====================
    public static void main(String[] args) {
        SmartTravelService service = new SmartTravelService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("  Welcome to SmartTravel Agency - A3");
        System.out.println("  Made by Adam Kozman & Alexandre Chamoun");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(scanner);

            switch (choice) {
                // ---- A1 Menus (1-6) wired to service ----
                case 1: menuClientManagement(scanner, service);      break;
                case 2: menuTripManagement(scanner, service);        break;
                case 3: menuTransportManagement(scanner, service);   break;
                case 4: menuAccommodationManagement(scanner, service); break;
                case 5: menuAdditionalOperations(scanner, service);  break;
                case 6: menuGenerateVisualization(scanner, service); break;

                // Menu 7 replaced with Advanced Analytics ----
                case 7:  menuAdvancedAnalytics(scanner, service);   break;

               
                case 8:  loadAllData(service);            break;
                case 9:  saveAllData(service);            break;
                case 10: runPredefinedScenario(service);  break;
                case 11: generateDashboard(service);      break;
                case 0:
                    System.out.println("Goodbye! Thank you for using SmartTravel.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 0-11.");
            }
        }
        scanner.close();
    }


    // ===================== PRINT MENU =====================
    private static void printMenu() {
        System.out.println("\n=== SmartTravel A3 Console ===");
        System.out.println("1.  Client Management");
        System.out.println("2.  Trip Management");
        System.out.println("3.  Transportation Management");
        System.out.println("4.  Accommodation Management");
        System.out.println("5.  Additional Operations");
        System.out.println("6.  Generate Visualization (A1 Charts)");
        System.out.println("7.  Advanced Analytics");       // A3: replaces old "List All Data Summary"
        System.out.println("8.  Load All Data (output/data/*.csv)");
        System.out.println("9.  Save All Data (output/data/*.csv)");
        System.out.println("10. Run Predefined Scenario");
        System.out.println("11. Generate Dashboard (HTML + charts)");
        System.out.println("0.  Exit");
        System.out.print("Enter your choice: ");
    }


    // ===================== MENU 1: CLIENT MANAGEMENT =====================
    private static void menuClientManagement(Scanner scanner, SmartTravelService service) {
        System.out.println("\n--- Client Management ---");
        System.out.println("a) Add a client");
        System.out.println("b) Edit a client");
        System.out.println("c) Delete a client");
        System.out.println("d) List all clients");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "a":
                if (service.getClientCount() >= 100) { System.out.println("Client list is full."); break; }
                System.out.print("First name: "); String fn = scanner.nextLine();
                System.out.print("Last name: ");  String ln = scanner.nextLine();
                System.out.print("Email: ");      String em = scanner.nextLine();
                try {
                    Client c = new Client(fn, ln, em);
                    service.addClient(c);
                    System.out.println("Client added: " + c.getClientId());
                } catch (InvalidClientDataException e) {
                    System.out.println("Invalid data: " + e.getMessage());
                } catch (DuplicateEmailException e) {
                    System.out.println("Duplicate email: " + e.getMessage());
                }
                break;

            case "b":
                if (service.getClientCount() == 0) { System.out.println("No clients."); break; }
                System.out.print("Enter client ID to edit: ");
                String editId = scanner.nextLine().trim();
                try {
                    Client ec = service.findClientById(editId);
                    System.out.print("New first name: ");
                    ec.setFirstName(scanner.nextLine());
                    System.out.print("New last name: ");
                    ec.setLastName(scanner.nextLine());
                    System.out.print("New email: ");
                    ec.setEmail(scanner.nextLine());
                    System.out.println("Client updated.");
                } catch (EntityNotFoundException e) {
                    System.out.println("Not found: " + e.getMessage());
                } catch (InvalidClientDataException e) {
                    System.out.println("Invalid data: " + e.getMessage());
                }
                break;

            case "c":
                if (service.getClientCount() == 0) { System.out.println("No clients."); break; }
                System.out.print("Enter client ID to delete: ");
                String delId = scanner.nextLine().trim();
                boolean deleted = false;
                for (int i = 0; i < service.getClientCount(); i++) {
                    if (service.getClient(i).getClientId().equals(delId)) {
                        service.removeClientAt(i);
                        deleted = true;
                        System.out.println("Client deleted.");
                        break;
                    }
                }
                if (!deleted) System.out.println("Client not found.");
                break;

            case "d":
                if (service.getClientCount() == 0) { System.out.println("No clients."); break; }
                for (int i = 0; i < service.getClientCount(); i++)
                    System.out.println(service.getClient(i) + "\n");
                break;

            default: System.out.println("Invalid choice.");
        }
    }


    // ===================== MENU 2: TRIP MANAGEMENT =====================
    private static void menuTripManagement(Scanner scanner, SmartTravelService service) {
        System.out.println("\n--- Trip Management ---");
        System.out.println("a) Create a trip");
        System.out.println("b) Edit trip information");
        System.out.println("c) Cancel a trip");
        System.out.println("d) List all trips");
        System.out.println("e) List trips for a specific client");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "a":
                if (service.getTripCount() >= 200) { System.out.println("Trip list is full."); break; }
                if (service.getClientCount() == 0) { System.out.println("No clients. Add a client first."); break; }
                System.out.print("Destination: "); String dest = scanner.nextLine();
                System.out.print("Days (1-20): "); int days = readInt(scanner);
                System.out.print("Base price (min $100): $"); double price = readDouble(scanner);
                System.out.print("Client ID: "); String cid = scanner.nextLine().trim();

                Client foundC = null;
                try { foundC = service.findClientById(cid); }
                catch (EntityNotFoundException e) { System.out.println("Client not found."); break; }

                Trip newTrip = null;
                try { newTrip = new Trip(dest, days, price, foundC); }
                catch (InvalidTripDataException e) { System.out.println("Invalid trip: " + e.getMessage()); break; }

                // Optional transportation
                System.out.print("Add transportation? (yes/no): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                    if (service.getTransportCount() == 0) {
                        System.out.println("No transportations available.");
                    } else {
                        System.out.print("Transport ID: ");
                        String tid = scanner.nextLine().trim();
                        try {
                            newTrip.setTransportation(service.findTransportationById(tid));
                            System.out.println("Transportation linked.");
                        } catch (EntityNotFoundException e) { System.out.println("Transport not found."); }
                    }
                }

                // Optional accommodation
                System.out.print("Add accommodation? (yes/no): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                    if (service.getAccommodationCount() == 0) {
                        System.out.println("No accommodations available.");
                    } else {
                        System.out.print("Accommodation ID: ");
                        String aid = scanner.nextLine().trim();
                        try {
                            newTrip.setAccommodation(service.findAccommodationById(aid));
                            System.out.println("Accommodation linked.");
                        } catch (EntityNotFoundException e) { System.out.println("Accommodation not found."); }
                    }
                }

                service.createTrip(newTrip);
                System.out.println("Trip created: " + newTrip.getTripId()
                        + " | Total: $" + String.format("%.2f", newTrip.calculateTotalCost()));
                break;

            case "b":
                if (service.getTripCount() == 0) { System.out.println("No trips."); break; }
                System.out.print("Trip ID to edit: ");
                String editTid = scanner.nextLine().trim();
                boolean foundTrip = false;
                for (int i = 0; i < service.getTripCount(); i++) {
                    Trip t = service.getTrip(i);
                    if (t.getTripId().equals(editTid)) {
                        foundTrip = true;
                        try {
                            System.out.print("New destination: "); t.setDestination(scanner.nextLine());
                            System.out.print("New days (1-20): "); t.setDurationInDays(readInt(scanner));
                            System.out.print("New base price ($100+): $"); t.setBasePrice(readDouble(scanner));
                            System.out.println("Trip updated.");
                        } catch (InvalidTripDataException e) {
                            System.out.println("Invalid data: " + e.getMessage());
                        }
                        break;
                    }
                }
                if (!foundTrip) System.out.println("Trip not found.");
                break;

            case "c":
                if (service.getTripCount() == 0) { System.out.println("No trips."); break; }
                System.out.print("Trip ID to cancel: ");
                String cancelId = scanner.nextLine().trim();
                boolean cancelled = false;
                for (int i = 0; i < service.getTripCount(); i++) {
                    if (service.getTrip(i).getTripId().equals(cancelId)) {
                        service.removeTripAt(i);
                        cancelled = true;
                        System.out.println("Trip cancelled.");
                        break;
                    }
                }
                if (!cancelled) System.out.println("Trip not found.");
                break;

            case "d":
                if (service.getTripCount() == 0) { System.out.println("No trips."); break; }
                for (int i = 0; i < service.getTripCount(); i++)
                    System.out.println(service.getTrip(i) + "\n");
                break;

            case "e":
                if (service.getTripCount() == 0) { System.out.println("No trips."); break; }
                System.out.print("Client ID: ");
                String searchCid = scanner.nextLine().trim();
                boolean any = false;
                for (int i = 0; i < service.getTripCount(); i++) {
                    Trip t = service.getTrip(i);
                    if (t.getClient() != null && t.getClient().getClientId().equals(searchCid)) {
                        System.out.println(t + "\n");
                        any = true;
                    }
                }
                if (!any) System.out.println("No trips found for client " + searchCid + ".");
                break;

            default: System.out.println("Invalid choice.");
        }
    }


    // ===================== MENU 3: TRANSPORTATION MANAGEMENT =====================
    private static void menuTransportManagement(Scanner scanner, SmartTravelService service) {
        System.out.println("\n--- Transportation Management ---");
        System.out.println("a) Add a transportation option");
        System.out.println("b) Remove a transportation option");
        System.out.println("c) List transportation options by type");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "a":
                if (service.getTransportCount() >= 50) { System.out.println("Transportation list is full."); break; }
                System.out.println("Type: 1) Flight  2) Train  3) Bus");
                System.out.print("Enter type: ");
                int ttype = readInt(scanner);
                System.out.print("Company name: ");   String company = scanner.nextLine();
                System.out.print("Departure city: "); String dep = scanner.nextLine();
                System.out.print("Arrival city: ");   String arr = scanner.nextLine();
                System.out.print("Base fare: $");      double fare = readDouble(scanner);
                try {
                    if (ttype == 1) {
                        System.out.print("Airline name: ");       String airline = scanner.nextLine();
                        System.out.print("Luggage allowance (kg): "); double luggage = readDouble(scanner);
                        service.addTransportation(new Flight(company, dep, arr, airline, luggage, fare));
                    } else if (ttype == 2) {
                        System.out.print("Train type: ");  String trainType = scanner.nextLine();
                        System.out.print("Seat class: ");  String seatClass = scanner.nextLine();
                        service.addTransportation(new Train(company, dep, arr, trainType, seatClass, fare));
                    } else if (ttype == 3) {
                        System.out.print("Bus company: ");          String busComp = scanner.nextLine();
                        System.out.print("Number of stops (>=1): "); int stops = readInt(scanner);
                        service.addTransportation(new Bus(company, dep, arr, busComp, stops, fare));
                    } else {
                        System.out.println("Invalid type."); break;
                    }
                    System.out.println("Transportation added.");
                } catch (InvalidTransportDataException e) {
                    System.out.println("Invalid transport data: " + e.getMessage());
                }
                break;

            case "b":
                if (service.getTransportCount() == 0) { System.out.println("No transportation options."); break; }
                System.out.print("Transport ID to remove: ");
                String removeId = scanner.nextLine().trim();
                boolean removed = false;
                for (int i = 0; i < service.getTransportCount(); i++) {
                    if (service.getTransportation(i).getTransportId().equals(removeId)) {
                        service.removeTransportationAt(i);
                        removed = true;
                        System.out.println("Transportation removed.");
                        break;
                    }
                }
                if (!removed) System.out.println("Transportation not found.");
                break;

            case "c":
                System.out.println("\n-- Flights --");
                boolean anyFlight = false;
                for (int i = 0; i < service.getTransportCount(); i++) {
                    if (service.getTransportation(i) instanceof Flight) {
                        System.out.println(service.getTransportation(i) + "\n"); anyFlight = true;
                    }
                }
                if (!anyFlight) System.out.println("No flights.");

                System.out.println("\n-- Trains --");
                boolean anyTrain = false;
                for (int i = 0; i < service.getTransportCount(); i++) {
                    if (service.getTransportation(i) instanceof Train) {
                        System.out.println(service.getTransportation(i) + "\n"); anyTrain = true;
                    }
                }
                if (!anyTrain) System.out.println("No trains.");

                System.out.println("\n-- Buses --");
                boolean anyBus = false;
                for (int i = 0; i < service.getTransportCount(); i++) {
                    if (service.getTransportation(i) instanceof Bus) {
                        System.out.println(service.getTransportation(i) + "\n"); anyBus = true;
                    }
                }
                if (!anyBus) System.out.println("No buses.");
                break;

            default: System.out.println("Invalid choice.");
        }
    }


    // ===================== MENU 4: ACCOMMODATION MANAGEMENT =====================
    private static void menuAccommodationManagement(Scanner scanner, SmartTravelService service) {
        System.out.println("\n--- Accommodation Management ---");
        System.out.println("a) Add an accommodation");
        System.out.println("b) Remove an accommodation");
        System.out.println("c) List accommodations by type");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "a":
                if (service.getAccommodationCount() >= 50) { System.out.println("Accommodation list is full."); break; }
                System.out.println("Type: 1) Hotel  2) Hostel");
                System.out.print("Enter type: "); int atype = readInt(scanner);
                System.out.print("Name: ");          String aName = scanner.nextLine();
                System.out.print("Location: ");      String aLoc = scanner.nextLine();
                System.out.print("Price/night: $");  double ppn = readDouble(scanner);
                try {
                    if (atype == 1) {
                        System.out.print("Star rating (1-5): "); int stars = readInt(scanner);
                        service.addAccommodation(new Hotel(aName, aLoc, ppn, stars));
                    } else if (atype == 2) {
                        System.out.print("Shared beds per room (>=1): "); int beds = readInt(scanner);
                        service.addAccommodation(new Hostel(aName, aLoc, ppn, beds));
                    } else {
                        System.out.println("Invalid type."); break;
                    }
                    System.out.println("Accommodation added.");
                } catch (InvalidAccommodationDataException e) {
                    System.out.println("Invalid accommodation data: " + e.getMessage());
                }
                break;

            case "b":
                if (service.getAccommodationCount() == 0) { System.out.println("No accommodations."); break; }
                System.out.print("Accommodation ID to remove: ");
                String removeAccId = scanner.nextLine().trim();
                boolean removedAcc = false;
                for (int i = 0; i < service.getAccommodationCount(); i++) {
                    if (service.getAccommodation(i).getAccommodationId().equals(removeAccId)) {
                        service.removeAccommodationAt(i);
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
                for (int i = 0; i < service.getAccommodationCount(); i++) {
                    if (service.getAccommodation(i) instanceof Hotel) {
                        System.out.println(service.getAccommodation(i) + "\n"); anyHotel = true;
                    }
                }
                if (!anyHotel) System.out.println("No hotels.");

                System.out.println("\n-- Hostels --");
                boolean anyHostel = false;
                for (int i = 0; i < service.getAccommodationCount(); i++) {
                    if (service.getAccommodation(i) instanceof Hostel) {
                        System.out.println(service.getAccommodation(i) + "\n"); anyHostel = true;
                    }
                }
                if (!anyHostel) System.out.println("No hostels.");
                break;

            default: System.out.println("Invalid choice.");
        }
    }


    // ===================== MENU 5: ADDITIONAL OPERATIONS =====================
    private static void menuAdditionalOperations(Scanner scanner, SmartTravelService service) {
        System.out.println("\n--- Additional Operations ---");
        System.out.println("a) Display the most expensive trip");
        System.out.println("b) Calculate total cost of a trip");
        System.out.println("c) Deep copy of transportation array");
        System.out.println("d) Deep copy of accommodation array");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "a":
                if (service.getTripCount() == 0) { System.out.println("No trips."); break; }
                Trip best = service.getTrip(0);
                for (int i = 1; i < service.getTripCount(); i++)
                    if (service.getTrip(i).calculateTotalCost() > best.calculateTotalCost())
                        best = service.getTrip(i);
                System.out.println("Most expensive trip:\n" + best);
                break;

            case "b":
                if (service.getTripCount() == 0) { System.out.println("No trips."); break; }
                System.out.print("Enter trip ID: ");
                String tripId = scanner.nextLine().trim();
                boolean found = false;
                for (int i = 0; i < service.getTripCount(); i++) {
                    if (service.getTrip(i).getTripId().equals(tripId)) {
                        System.out.println("Total cost: $"
                                + String.format("%.2f", service.calculateTripTotal(i)));
                        found = true; break;
                    }
                }
                if (!found) System.out.println("Trip not found.");
                break;

            case "c":
                if (service.getTransportCount() == 0) { System.out.println("No transportations."); break; }
                System.out.println("Deep copy of transportation array:");
                for (int i = 0; i < service.getTransportCount(); i++) {
                    Transportation orig = service.getTransportation(i);
                    Transportation copy;
                    if      (orig instanceof Flight) copy = new Flight((Flight) orig);
                    else if (orig instanceof Train)  copy = new Train((Train) orig);
                    else                             copy = new Bus((Bus) orig);
                    System.out.println(copy + "\n");
                }
                break;

            case "d":
                if (service.getAccommodationCount() == 0) { System.out.println("No accommodations."); break; }
                System.out.println("Deep copy of accommodation array:");
                for (int i = 0; i < service.getAccommodationCount(); i++) {
                    Accommodation orig = service.getAccommodation(i);
                    Accommodation copy;
                    if (orig instanceof Hotel) copy = new Hotel((Hotel) orig);
                    else                       copy = new Hostel((Hostel) orig);
                    System.out.println(copy + "\n");
                }
                break;

            default: System.out.println("Invalid choice.");
        }
    }


    // ===================== MENU 6: GENERATE VISUALIZATION (A1) =====================
    private static void menuGenerateVisualization(Scanner scanner, SmartTravelService service) {
        System.out.println("\n--- Generate Visualization ---");
        System.out.println("a) Bar chart (Trip cost)");
        System.out.println("b) Pie chart (Trips per destination)");
        System.out.println("c) Line chart (Duration over time)");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        if (service.getTripCount() == 0) { System.out.println("No trips to chart."); return; }

        try {
            switch (choice) {
                case "a":
                    TripChartGenerator.generateCostBarChart(service.getAllTrips(), service.getTripCount());
                    System.out.println("Bar chart saved to output/charts/trip_cost_bar_chart.png");
                    break;
                case "b":
                    TripChartGenerator.generateDestinationPieChart(service.getAllTrips(), service.getTripCount());
                    System.out.println("Pie chart saved to output/charts/trips_per_destination_pie.png");
                    break;
                case "c":
                    TripChartGenerator.generateDurationLineChart(service.getAllTrips(), service.getTripCount());
                    System.out.println("Line chart saved to output/charts/trip_duration_line_chart.png");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Error generating chart: " + e.getMessage());
        }
    }


    // ===================== MENU 7: ADVANCED ANALYTICS (A3) =====================
    // Menu 7 used to be "List All Data Summary" in A2, now replaced with analytics.
    // Shows off Repository<T>, Predicate<T> filtering, RecentList<T>, and sorting.
    // Repos are rebuilt each time from the current service data.
    private static void menuAdvancedAnalytics(Scanner scanner, SmartTravelService service) {

        // Build repo mirrors from the live service lists
        Repository<Trip>   tripRepo   = new Repository<>();
        Repository<Client> clientRepo = new Repository<>();

        for (int i = 0; i < service.getTripCount();   i++) tripRepo.add(service.getTrip(i));
        for (int i = 0; i < service.getClientCount(); i++) clientRepo.add(service.getClient(i));

        // RecentList tracks whichever trips the user views during this session
        RecentList<Trip> recentTrips = new RecentList<>();

        boolean inAnalytics = true;
        while (inAnalytics) {
            System.out.println("\n--- Advanced Analytics ---");
            System.out.println("7.1  Trips by Destination (Predicate filter)");
            System.out.println("7.2  Trips by Cost Range  (Predicate range)");
            System.out.println("7.3  Top Clients by Spending (natural sort)");
            System.out.println("7.4  Recent Trips (RecentList demo)");
            System.out.println("7.5  Smart Sort Collections (business natural order)");
            System.out.println("7.6  Back to main menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {

                // ----- 7.1: Trips by Destination -----
                // predicate filters trips where destination matches the input
                case "7.1": {
                    if (service.getTripCount() == 0) { System.out.println("No trips loaded."); break; }
                    System.out.print("Enter destination to search: ");
                    String dest = scanner.nextLine().trim();
                    List<Trip> matches = tripRepo.filter(
                            t -> t.getDestination().equalsIgnoreCase(dest));
                    System.out.println("\nTrips to \"" + dest + "\": " + matches.size() + " found.");
                    for (Trip t : matches) {
                        System.out.println("  " + t.getTripId()
                                + " | " + t.getDurationInDays() + " days"
                                + " | $" + String.format("%.2f", t.calculateTotalCost()));
                        recentTrips.addRecent(t); // record as recently viewed
                    }
                    break;
                }

                // ----- 7.2: Trips by Cost Range -----
                // predicate filters trips where total cost is between min and max
                case "7.2": {
                    if (service.getTripCount() == 0) { System.out.println("No trips loaded."); break; }
                    System.out.print("Minimum cost: $");
                    double min = readDouble(scanner);
                    System.out.print("Maximum cost: $");
                    double max = readDouble(scanner);
                    List<Trip> inRange = tripRepo.filter(
                            t -> t.calculateTotalCost() >= min && t.calculateTotalCost() <= max);
                    System.out.println("\nTrips between $" + String.format("%.2f", min)
                            + " and $" + String.format("%.2f", max)
                            + ": " + inRange.size() + " found.");
                    for (Trip t : inRange) {
                        System.out.println("  " + t.getTripId()
                                + " | " + t.getDestination()
                                + " | $" + String.format("%.2f", t.calculateTotalCost()));
                        recentTrips.addRecent(t);
                    }
                    break;
                }

                // ----- 7.3: Top Clients by Spending -----
                // getSorted() uses Client.compareTo() which sorts descending by amountSpent
                case "7.3": {
                    if (service.getClientCount() == 0) { System.out.println("No clients loaded."); break; }
                    System.out.print("How many top clients to show? ");
                    int n = readInt(scanner);
                    List<Client> sorted = clientRepo.getSorted();
                    int limit = Math.min(n, sorted.size());
                    System.out.println("\nTop " + limit + " clients by total spending:");
                    for (int i = 0; i < limit; i++) {
                        Client c = sorted.get(i);
                        System.out.printf("  #%-2d %-8s %-25s $%.2f%n",
                                i + 1,
                                c.getClientId(),
                                c.getFirstName() + " " + c.getLastName(),
                                c.getAmountSpent());
                    }
                    break;
                }

                // ----- 7.4: Recent Trips -----
                // shows trips viewed in 7.1 or 7.2 this session, most recent first
                // RecentList auto-caps at 10 items, no size variable needed
                case "7.4": {
                    System.out.println("\nRecently viewed trips (most recent first):");
                    if (recentTrips.isEmpty()) {
                        System.out.println("  (none yet — use 7.1 or 7.2 to view some trips first)");
                    } else {
                        recentTrips.printRecent(5); // show up to 5
                    }
                    break;
                }

                // ----- 7.5: Smart Sort Collections -----
                // sorts all four entity types using their natural business order
                case "7.5": {
                    System.out.println("\n=== Business Natural Order Sort ===");

                    // Trips — descending by total cost (highest revenue first)
                    System.out.println("\n--- Trips (descending by total cost) ---");
                    if (service.getTripCount() == 0) {
                        System.out.println("  (none)");
                    } else {
                        List<Trip> sortedTrips = tripRepo.getSorted();
                        for (Trip t : sortedTrips) {
                            System.out.printf("  %-8s %-15s $%.2f%n",
                                    t.getTripId(), t.getDestination(), t.calculateTotalCost());
                        }
                    }

                    // Clients — descending by amountSpent (most valuable first)
                    System.out.println("\n--- Clients (descending by total spent) ---");
                    if (service.getClientCount() == 0) {
                        System.out.println("  (none)");
                    } else {
                        List<Client> sortedClients = clientRepo.getSorted();
                        for (Client c : sortedClients) {
                            System.out.printf("  %-8s %-25s $%.2f%n",
                                    c.getClientId(),
                                    c.getFirstName() + " " + c.getLastName(),
                                    c.getAmountSpent());
                        }
                    }

                    // Accommodations — descending by pricePerNight (luxury first)
                    System.out.println("\n--- Accommodations (descending by price/night) ---");
                    if (service.getAccommodationCount() == 0) {
                        System.out.println("  (none)");
                    } else {
                        Repository<Accommodation> accRepo = new Repository<>();
                        for (int i = 0; i < service.getAccommodationCount(); i++)
                            accRepo.add(service.getAccommodation(i));
                        List<Accommodation> sortedAcc = accRepo.getSorted();
                        for (Accommodation a : sortedAcc) {
                            System.out.printf("  %-8s %-25s $%.2f/night%n",
                                    a.getAccommodationId(), a.getName(), a.getPricePerNight());
                        }
                    }

                    // Transportations — descending by base fare (premium first)
                    System.out.println("\n--- Transportations (descending by base fare) ---");
                    if (service.getTransportCount() == 0) {
                        System.out.println("  (none)");
                    } else {
                        Repository<Transportation> transRepo = new Repository<>();
                        for (int i = 0; i < service.getTransportCount(); i++)
                            transRepo.add(service.getTransportation(i));
                        List<Transportation> sortedTrans = transRepo.getSorted();
                        for (Transportation t : sortedTrans) {
                            System.out.printf("  %-8s [%-6s] %s -> %s  $%.2f%n",
                                    t.getTransportId(),
                                    t instanceof Flight ? "Flight" : t instanceof Train ? "Train" : "Bus",
                                    t.getDepartureCity(), t.getArrivalCity(),
                                    t.getBaseFare());
                        }
                    }
                    break;
                }

                case "7.6":
                    inAnalytics = false;
                    break;

                default:
                    System.out.println("Invalid choice. Enter 7.1 through 7.6.");
            }
        }
    }


    // ===================== MENU 8: LOAD ALL DATA =====================
    private static void loadAllData(SmartTravelService service) {
        System.out.println("\nLoading all data from output/data/...");
        service.loadAllData("data/");
        System.out.println("Load complete. Errors (if any) logged to output/logs/errors.txt.");
    }


    // ===================== MENU 9: SAVE ALL DATA =====================
    private static void saveAllData(SmartTravelService service) {
        System.out.println("\nSaving all data to output/data/...");
        service.saveAllData("data/");
        System.out.println("Save complete.");
    }


    // ===================== MENU 10: PREDEFINED SCENARIO =====================
    private static void runPredefinedScenario(SmartTravelService service) {
        System.out.println("\n===============================================");
        System.out.println("    SMART TRAVEL A2 - PREDEFINED SCENARIO");
        System.out.println("===============================================");
        System.out.println("Demonstrates: loading, adding, saving,");
        System.out.println("all 6 exceptions, polymorphism, and persistence.");
        System.out.println();

        // ---- 1. Load existing data ----
        System.out.println("--- Step 1: Load data from CSV ---");
        service.loadAllData("data/");
        System.out.println("After load: "
                + service.getClientCount() + " clients, "
                + service.getTripCount() + " trips.");

        // ---- 2. Add some hard-coded objects ----
        System.out.println("\n--- Step 2: Add hard-coded objects ---");

        Client c1 = null, c2 = null;
        try {
            c1 = new Client("Scenario", "Alpha", "scenario.alpha@test.com");
            service.addClient(c1);
            c2 = new Client("Scenario", "Beta",  "scenario.beta@test.com");
            service.addClient(c2);
            System.out.println("Added 2 scenario clients.");
        } catch (InvalidClientDataException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (DuplicateEmailException e) {
            System.out.println("[WARN] " + e.getMessage() + " (using existing client)");
            // find existing
            for (int i = 0; i < service.getClientCount(); i++) {
                if (service.getClient(i).getEmail().equalsIgnoreCase("scenario.alpha@test.com")) c1 = service.getClient(i);
                if (service.getClient(i).getEmail().equalsIgnoreCase("scenario.beta@test.com"))  c2 = service.getClient(i);
            }
        }

        Flight flight = null;
        Hotel  hotel  = null;
        try {
            flight = new Flight("ScenarioAir", "Montreal", "Rome", "ScenarioAir", 25, 600.00);
            service.addTransportation(flight);
            hotel  = new Hotel("Scenario Grand", "Rome", 180.00, 4);
            service.addAccommodation(hotel);
            System.out.println("Added flight and hotel.");
        } catch (InvalidTransportDataException | InvalidAccommodationDataException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        if (c1 != null && flight != null && hotel != null) {
            try {
                Trip t = new Trip("Rome", 7, 150.00, c1);
                t.setTransportation(flight);
                t.setAccommodation(hotel);
                service.createTrip(t);
                System.out.println("Created scenario trip to Rome. Total: $"
                        + String.format("%.2f", t.calculateTotalCost()));
            } catch (InvalidTripDataException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        // ---- 3. Demonstrate all 6 exceptions ----
        System.out.println("\n--- Step 3: Exception Demonstrations ---");

        System.out.print("[1] InvalidClientDataException (empty name): ");
        try { new Client("", "Test", "t@t.com"); }
        catch (InvalidClientDataException e) { System.out.println("CAUGHT: " + e.getMessage()); }

        System.out.print("[2] InvalidTripDataException (price < $100): ");
        try { new Trip("Paris", 3, 50.00, c1); }
        catch (InvalidTripDataException e) { System.out.println("CAUGHT: " + e.getMessage()); }

        System.out.print("[3] InvalidTransportDataException (bus 0 stops): ");
        try { new Bus("X", "A", "B", "X", 0, 30.00); }
        catch (InvalidTransportDataException e) { System.out.println("CAUGHT: " + e.getMessage()); }

        System.out.print("[4] InvalidAccommodationDataException (hostel $200/night): ");
        try { new Hostel("H", "X", 200.00, 4); }
        catch (InvalidAccommodationDataException e) { System.out.println("CAUGHT: " + e.getMessage()); }

        System.out.print("[5] EntityNotFoundException (client C9999): ");
        try { service.findClientById("C9999"); }
        catch (EntityNotFoundException e) { System.out.println("CAUGHT: " + e.getMessage()); }

        System.out.print("[6] DuplicateEmailException: ");
        try {
            if (c1 != null) {
                Client dup = new Client("Dup", "User", c1.getEmail());
                service.addClient(dup);
            }
        } catch (InvalidClientDataException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (DuplicateEmailException e) {
            System.out.println("CAUGHT: " + e.getMessage());
        }

        // ---- 4. Save updated data ----
        System.out.println("\n--- Step 4: Save data back to CSV ---");
        service.saveAllData("data/");

        System.out.println("\n--- Step 5: Summary after scenario ---");
        System.out.println(service.getClientCount() + " clients, "
                + service.getTripCount() + " trips, "
                + service.getAccommodationCount() + " accommodations, "
                + service.getTransportCount() + " transportations.");

        System.out.println("\n===============================================");
        System.out.println("         END OF PREDEFINED SCENARIO");
        System.out.println("===============================================");
    }


    // ===================== MENU 11: GENERATE DASHBOARD =====================
    private static void generateDashboard(SmartTravelService service) {
        System.out.println("\nGenerating dashboard...");
        if (service.getTripCount() == 0) {
            System.out.println("No trip data. Load data first (Menu 8) or run predefined scenario (Menu 10).");
            return;
        }
        try {
            DashboardGenerator.generateDashboard(service);
        } catch (Exception e) {
            System.out.println("Error generating dashboard: " + e.getMessage());
        }
    }


    // ===================== INPUT HELPERS =====================
    /** Reads an int and consumes the remaining newline. */
    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a whole number: ");
            }
        }
    }

    /** Reads a double and consumes the remaining newline. */
    private static double readDouble(Scanner scanner) {
        while (true) {
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }
}
