// -------------------------------------------------------------
// Assignment 2
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Central service class for the SmartTravel application.
// Maintains 4 in-memory arrays (clients, trips, accommodations, transportations)
// and provides all business logic methods used by the driver and dashboard.

package service;

import client.Client;
import travel.*;
import exceptions.*;

public class SmartTravelService {

    // ================= ARRAYS & COUNTS =================
    private Client[]         clients;
    private Trip[]           trips;
    private Accommodation[]  accommodations;
    private Transportation[] transportations;

    private int clientCount;
    private int tripCount;
    private int accommodationCount;
    private int transportCount;

    // ================= CONSTRUCTOR =================
    public SmartTravelService() {
        clients         = new Client[100];
        trips           = new Trip[200];
        accommodations  = new Accommodation[50];
        transportations = new Transportation[50];

        clientCount        = 0;
        tripCount          = 0;
        accommodationCount = 0;
        transportCount     = 0;
    }


    // ================= CLIENT METHODS =================

    /**
     * Adds a client to the clients array.
     * Throws DuplicateEmailException (unchecked) if email already exists.
     */
    public void addClient(Client client) {
        if (clientCount >= clients.length) {
            System.out.println("[WARNING] Client array is full. Cannot add more clients.");
            return;
        }
        // Check for duplicate email
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getEmail().equalsIgnoreCase(client.getEmail())) {
                throw new DuplicateEmailException(
                        "Email '" + client.getEmail() + "' is already registered to "
                        + clients[i].getFirstName() + " " + clients[i].getLastName() + ".");
            }
        }
        clients[clientCount++] = client;
    }

    /**
     * Returns true if a client with the given ID already exists in the array.
     */
    public boolean clientExists(String clientId) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getClientId().equalsIgnoreCase(clientId))
                return true;
        }
        return false;
    }

    /**
     * Returns the Client with the given ID.
     * Throws EntityNotFoundException if not found.
     */
    public Client findClientById(String clientId) throws EntityNotFoundException {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getClientId().equalsIgnoreCase(clientId))
                return clients[i];
        }
        throw new EntityNotFoundException("Client with ID '" + clientId + "' not found.");
    }

    /**
     * Removes the client at the given index (shift-left).
     */
    public void removeClientAt(int index) {
        if (index < 0 || index >= clientCount) return;
        for (int j = index; j < clientCount - 1; j++)
            clients[j] = clients[j + 1];
        clients[--clientCount] = null;
    }


    // ================= TRIP METHODS =================

    /**
     * Creates a trip and adds it to the trips array.
     * Also increments the client's amountSpent.
     */
    public void createTrip(Trip trip) {
        if (tripCount >= trips.length) {
            System.out.println("[WARNING] Trip array is full. Cannot add more trips.");
            return;
        }
        trips[tripCount++] = trip;
        // Update client's amountSpent
        if (trip.getClient() != null) {
            trip.getClient().addAmountSpent(trip.calculateTotalCost());
        }
    }

    /**
     * Removes the trip at the given index (shift-left).
     */
    public void removeTripAt(int index) {
        if (index < 0 || index >= tripCount) return;
        for (int j = index; j < tripCount - 1; j++)
            trips[j] = trips[j + 1];
        trips[--tripCount] = null;
    }

    /**
     * Resolves client, accommodation, and transportation references for a trip,
     * then returns the trip's total cost.
     */
    public double calculateTripTotal(int index) {
        if (index < 0 || index >= tripCount) return 0;
        return trips[index].calculateTotalCost();
    }


    // ================= ACCOMMODATION METHODS =================

    public void addAccommodation(Accommodation accommodation) {
        if (accommodationCount >= accommodations.length) {
            System.out.println("[WARNING] Accommodation array is full.");
            return;
        }
        accommodations[accommodationCount++] = accommodation;
    }

    public Accommodation findAccommodationById(String id) throws EntityNotFoundException {
        for (int i = 0; i < accommodationCount; i++) {
            if (accommodations[i].getAccommodationId().equalsIgnoreCase(id))
                return accommodations[i];
        }
        throw new EntityNotFoundException("Accommodation with ID '" + id + "' not found.");
    }

    public void removeAccommodationAt(int index) {
        if (index < 0 || index >= accommodationCount) return;
        for (int j = index; j < accommodationCount - 1; j++)
            accommodations[j] = accommodations[j + 1];
        accommodations[--accommodationCount] = null;
    }


    // ================= TRANSPORTATION METHODS =================

    public void addTransportation(Transportation transportation) {
        if (transportCount >= transportations.length) {
            System.out.println("[WARNING] Transportation array is full.");
            return;
        }
        transportations[transportCount++] = transportation;
    }

    public Transportation findTransportationById(String id) throws EntityNotFoundException {
        for (int i = 0; i < transportCount; i++) {
            if (transportations[i].getTransportId().equalsIgnoreCase(id))
                return transportations[i];
        }
        throw new EntityNotFoundException("Transportation with ID '" + id + "' not found.");
    }

    public void removeTransportationAt(int index) {
        if (index < 0 || index >= transportCount) return;
        for (int j = index; j < transportCount - 1; j++)
            transportations[j] = transportations[j + 1];
        transportations[--transportCount] = null;
    }


    // ================= LOAD / SAVE ALL DATA =================

    /**
     * Loads all data from CSV files in the given directory.
     * Order matters: clients first, then accommodations & transportations, then trips.
     */
    public void loadAllData(String dataDir) {
        // Reset counts before loading
        clientCount        = 0;
        tripCount          = 0;
        accommodationCount = 0;
        transportCount     = 0;

        // 1. Clients first
        clientCount = persistence.ClientFileManager.loadClients(clients, dataDir + "clients.csv");
        System.out.println("Loaded " + clientCount + " clients.");

        // 2. Accommodations
        accommodationCount = persistence.AccommodationFileManager.loadAccommodations(
                accommodations, dataDir + "accommodations.csv");
        System.out.println("Loaded " + accommodationCount + " accommodations.");

        // 3. Transportations
        transportCount = persistence.TransportFileManager.loadTransportations(
                transportations, dataDir + "transports.csv");
        System.out.println("Loaded " + transportCount + " transportations.");

        // 4. Trips (must resolve client/acc/trans by ID)
        tripCount = persistence.TripFileManager.loadTrips(
                trips, dataDir + "trips.csv",
                clients, clientCount,
                accommodations, accommodationCount,
                transportations, transportCount);
        System.out.println("Loaded " + tripCount + " trips.");

        // Recalculate amountSpent for all clients from loaded trips
        for (int i = 0; i < clientCount; i++)
            clients[i].setAmountSpent(0.0);
        for (int i = 0; i < tripCount; i++) {
            if (trips[i].getClient() != null)
                trips[i].getClient().addAmountSpent(trips[i].calculateTotalCost());
        }
    }

    /**
     * Saves all data to CSV files in the given directory.
     */
    public void saveAllData(String dataDir) {
        try {
            persistence.ClientFileManager.saveClients(clients, clientCount, dataDir + "clients.csv");
            System.out.println("Clients saved.");
        } catch (java.io.IOException e) {
            System.out.println("[ERROR] Could not save clients: " + e.getMessage());
        }
        try {
            persistence.AccommodationFileManager.saveAccommodations(
                    accommodations, accommodationCount, dataDir + "accommodations.csv");
            System.out.println("Accommodations saved.");
        } catch (java.io.IOException e) {
            System.out.println("[ERROR] Could not save accommodations: " + e.getMessage());
        }
        try {
            persistence.TransportFileManager.saveTransportations(
                    transportations, transportCount, dataDir + "transports.csv");
            System.out.println("Transportations saved.");
        } catch (java.io.IOException e) {
            System.out.println("[ERROR] Could not save transportations: " + e.getMessage());
        }
        try {
            persistence.TripFileManager.saveTrips(trips, tripCount, dataDir + "trips.csv");
            System.out.println("Trips saved.");
        } catch (java.io.IOException e) {
            System.out.println("[ERROR] Could not save trips: " + e.getMessage());
        }
    }


    // ================= GETTERS (for dashboard & driver) =================

    public int getClientCount()        { return clientCount; }
    public int getTripCount()          { return tripCount; }
    public int getAccommodationCount() { return accommodationCount; }
    public int getTransportCount()     { return transportCount; }

    public Client        getClient(int i)        { return clients[i]; }
    public Trip          getTrip(int i)          { return trips[i]; }
    public Accommodation getAccommodation(int i) { return accommodations[i]; }
    public Transportation getTransportation(int i){ return transportations[i]; }

    /** Returns the full clients array (used by file managers). */
    public Client[] getAllClients()            { return clients; }

    /** Returns the full trips array (used by charts). */
    public Trip[] getAllTrips()                { return trips; }

    /** Returns the full accommodations array. */
    public Accommodation[] getAllAccommodations() { return accommodations; }

    /** Returns the full transportations array. */
    public Transportation[] getAllTransportations() { return transportations; }
}
