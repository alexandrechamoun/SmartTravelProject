// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Central service class for the SmartTravel application.
// A3 changes: replaced all 4 fixed-size arrays and their associated
// count variables with dynamic ArrayLists. All existing public method
// signatures are preserved so the A2 driver compiles unchanged.

package service;

import client.Client;
import travel.*;
import exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class SmartTravelService {

    // ================= COLLECTIONS =================
    // ** Replaced all 8 declarations above with 4 dynamic lists:
    private List<Client>         clients         = new ArrayList<>();
    private List<Trip>           trips           = new ArrayList<>();
    private List<Accommodation>  accommodations  = new ArrayList<>();
    private List<Transportation> transportations = new ArrayList<>();


    // ================= CONSTRUCTOR =================
    public SmartTravelService() {
        // A2: clients         = new Client[100];
        // A2: trips           = new Trip[200];
        // A2: accommodations  = new Accommodation[50];
        // A2: transportations = new Transportation[50];
        // A2: clientCount = tripCount = accommodationCount = transportCount = 0;
        // A3: ArrayList initializes empty — no fixed sizes or count variables needed
    }


    // ================= CLIENT METHODS =================

    public void addClient(Client client) {
        // A2: if (clientCount >= clients.length) { warn and return; }
        // A3: removed — ArrayList grows automatically, no capacity check needed

        // A2: for (int i = 0; i < clientCount; i++) if (clients[i].getEmail()...
        // A3: enhanced for-loop over the list
        for (Client c : clients) {
            if (c.getEmail().equalsIgnoreCase(client.getEmail())) {
                throw new DuplicateEmailException(
                        "Email '" + client.getEmail() + "' is already registered to "
                                + c.getFirstName() + " " + c.getLastName() + ".");
            }
        }
        // A2: clients[clientCount++] = client;
        // A3:
        clients.add(client);
    }

    public boolean clientExists(String clientId) {
        // A2: for (int i = 0; i < clientCount; i++) if (clients[i].getClientId()...
        // A3: enhanced for-loop over the list
        for (Client c : clients) {
            if (c.getClientId().equalsIgnoreCase(clientId))
                return true;
        }
        return false;
    }

    public Client findClientById(String clientId) throws EntityNotFoundException {
        // A2: for (int i = 0; i < clientCount; i++) if (clients[i].getClientId()... return clients[i];
        // A3: enhanced for-loop over the list
        for (Client c : clients) {
            if (c.getClientId().equalsIgnoreCase(clientId))
                return c;
        }
        throw new EntityNotFoundException("Client with ID '" + clientId + "' not found.");
    }

    public void removeClientAt(int index) {
        // A2: if (index < 0 || index >= clientCount) return; for (int j = index; j < clientCount - 1; j++) clients[j] = clients[j + 1]; clients[--clientCount] = null;
        // A3: ArrayList handles shifting and size tracking internally
        if (index < 0 || index >= clients.size()) return;
        clients.remove(index);
    }


    // ================= TRIP METHODS =================

    public void createTrip(Trip trip) {
        // A2: if (tripCount >= trips.length) { warn and return; }
        // A3: removed — no capacity limit
        // A2: trips[tripCount++] = trip;
        // A3:
        trips.add(trip);
        if (trip.getClient() != null) {
            trip.getClient().addAmountSpent(trip.calculateTotalCost());
        }
    }

    public void removeTripAt(int index) {
        // A2: if (index < 0 || index >= tripCount) return; for (int j = index; j < tripCount - 1; j++) trips[j] = trips[j + 1]; trips[--tripCount] = null;
        // A3:
        if (index < 0 || index >= trips.size()) return;
        trips.remove(index);
    }

    public double calculateTripTotal(int index) {
        // A2: if (index < 0 || index >= tripCount) return 0; return trips[index].calculateTotalCost();
        // A3:
        if (index < 0 || index >= trips.size()) return 0;
        return trips.get(index).calculateTotalCost();
    }


    // ================= ACCOMMODATION METHODS =================

    public void addAccommodation(Accommodation accommodation) {
        // A2: if (accommodationCount >= accommodations.length) { warn and return; } accommodations[accommodationCount++] = accommodation;
        // A3:
        accommodations.add(accommodation);
    }

    public Accommodation findAccommodationById(String id) throws EntityNotFoundException {
        // A2: for (int i = 0; i < accommodationCount; i++) if (accommodations[i].getAccommodationId()...
        // A3:
        for (Accommodation a : accommodations) {
            if (a.getAccommodationId().equalsIgnoreCase(id))
                return a;
        }
        throw new EntityNotFoundException("Accommodation with ID '" + id + "' not found.");
    }

    public void removeAccommodationAt(int index) {
        // A2: if (index < 0 || index >= accommodationCount) return; for (int j = index; j < accommodationCount - 1; j++) accommodations[j] = accommodations[j + 1]; accommodations[--accommodationCount] = null;
        // A3:
        if (index < 0 || index >= accommodations.size()) return;
        accommodations.remove(index);
    }


    // ================= TRANSPORTATION METHODS =================

    public void addTransportation(Transportation transportation) {
        // A2: if (transportCount >= transportations.length) { warn and return; } transportations[transportCount++] = transportation;
        // A3:
        transportations.add(transportation);
    }

    public Transportation findTransportationById(String id) throws EntityNotFoundException {
        // A2: for (int i = 0; i < transportCount; i++) if (transportations[i].getTransportId()...
        // A3:
        for (Transportation t : transportations) {
            if (t.getTransportId().equalsIgnoreCase(id))
                return t;
        }
        throw new EntityNotFoundException("Transportation with ID '" + id + "' not found.");
    }

    public void removeTransportationAt(int index) {
        // A2: if (index < 0 || index >= transportCount) return;
        //     for (int j = index; j < transportCount - 1; j++)
        //         transportations[j] = transportations[j + 1];
        //     transportations[--transportCount] = null;
        // A3:
        if (index < 0 || index >= transportations.size()) return;
        transportations.remove(index);
    }


    // ================= LOAD / SAVE ALL DATA =================

    public void loadAllData(String dataDir) {
        // A2: clientCount = tripCount = accommodationCount = transportCount = 0;
        // A3: clear the lists instead
        clients.clear();
        trips.clear();
        accommodations.clear();
        transportations.clear();

        // A3: temporary arrays used to bridge to the unchanged A2 file managers,
        //     which still expect array + count parameters. Results are then
        //     transferred into the ArrayList collections.
        Client[]         clientArr = new Client[100];
        Accommodation[]  accArr    = new Accommodation[50];
        Transportation[] transArr  = new Transportation[50];
        Trip[]           tripArr   = new Trip[200];

        // A2: clientCount = persistence.ClientFileManager.loadClients(clients, ...);
        // A3: load into temp array, then copy into list
        int clientCount = persistence.ClientFileManager.loadClients(clientArr, dataDir + "clients.csv");
        System.out.println("Loaded " + clientCount + " clients.");
        for (int i = 0; i < clientCount; i++) clients.add(clientArr[i]);

        int accCount = persistence.AccommodationFileManager.loadAccommodations(
                accArr, dataDir + "accommodations.csv");
        System.out.println("Loaded " + accCount + " accommodations.");
        for (int i = 0; i < accCount; i++) accommodations.add(accArr[i]);

        int transCount = persistence.TransportFileManager.loadTransportations(
                transArr, dataDir + "transports.csv");
        System.out.println("Loaded " + transCount + " transportations.");
        for (int i = 0; i < transCount; i++) transportations.add(transArr[i]);

        int tripCount = persistence.TripFileManager.loadTrips(
                tripArr, dataDir + "trips.csv",
                clientArr, clientCount,
                accArr, accCount,
                transArr, transCount);
        System.out.println("Loaded " + tripCount + " trips.");
        for (int i = 0; i < tripCount; i++) trips.add(tripArr[i]);

        // A2: for (int i = 0; i < clientCount; i++) clients[i].setAmountSpent(0.0);
        // A2: for (int i = 0; i < tripCount; i++) ...
        // A3: enhanced for-loops
        for (Client c : clients) c.setAmountSpent(0.0);
        for (Trip t : trips) {
            if (t.getClient() != null)
                t.getClient().addAmountSpent(t.calculateTotalCost());
        }
    }

    public void saveAllData(String dataDir) {
        // A3: convert lists to arrays so unchanged A2 file managers can be called as-is
        // A2: persistence.ClientFileManager.saveClients(clients, clientCount, ...);
        // A3:
        Client[]         clientArr = clients.toArray(new Client[0]);
        Accommodation[]  accArr    = accommodations.toArray(new Accommodation[0]);
        Transportation[] transArr  = transportations.toArray(new Transportation[0]);
        Trip[]           tripArr   = trips.toArray(new Trip[0]);

        try {
            persistence.ClientFileManager.saveClients(clientArr, clientArr.length, dataDir + "clients.csv");
            System.out.println("Clients saved.");
        } catch (java.io.IOException e) {
            System.out.println("[ERROR] Could not save clients: " + e.getMessage());
        }
        try {
            persistence.AccommodationFileManager.saveAccommodations(
                    accArr, accArr.length, dataDir + "accommodations.csv");
            System.out.println("Accommodations saved.");
        } catch (java.io.IOException e) {
            System.out.println("[ERROR] Could not save accommodations: " + e.getMessage());
        }
        try {
            persistence.TransportFileManager.saveTransportations(
                    transArr, transArr.length, dataDir + "transports.csv");
            System.out.println("Transportations saved.");
        } catch (java.io.IOException e) {
            System.out.println("[ERROR] Could not save transportations: " + e.getMessage());
        }
        try {
            persistence.TripFileManager.saveTrips(tripArr, tripArr.length, dataDir + "trips.csv");
            System.out.println("Trips saved.");
        } catch (java.io.IOException e) {
            System.out.println("[ERROR] Could not save trips: " + e.getMessage());
        }
    }


    // ================= GETTERS =================
    // A2: returned the count variable directly, e.g. return clientCount;
    // A3: delegate to list's size() method — same return type, same signature
    public int getClientCount()        { return clients.size(); }
    public int getTripCount()          { return trips.size(); }
    public int getAccommodationCount() { return accommodations.size(); }
    public int getTransportCount()     { return transportations.size(); }

    // A2: return clients[i];
    // A3: return clients.get(i); — identical external behaviour
    public Client         getClient(int i)         { return clients.get(i); }
    public Trip           getTrip(int i)           { return trips.get(i); }
    public Accommodation  getAccommodation(int i)  { return accommodations.get(i); }
    public Transportation getTransportation(int i) { return transportations.get(i); }

    // A2: returned the raw array field directly, e.g. return trips;
    // A3: convert list to array on demand so callers (TripChartGenerator,
    //     DashboardGenerator) still receive Trip[] with no changes on their end
    public Trip[]           getAllTrips()            { return trips.toArray(new Trip[0]); }
    public Client[]         getAllClients()          { return clients.toArray(new Client[0]); }
    public Accommodation[]  getAllAccommodations()   { return accommodations.toArray(new Accommodation[0]); }
    public Transportation[] getAllTransportations()  { return transportations.toArray(new Transportation[0]); }

    // ================= A3: NEW LIST ACCESSORS =================
    // These did not exist in A2. Expose the live lists for Repository and analytics.
    public List<Client>         getClientList()         { return clients; }
    public List<Trip>           getTripList()           { return trips; }
    public List<Accommodation>  getAccommodationList()  { return accommodations; }
    public List<Transportation> getTransportationList() { return transportations; }
}
