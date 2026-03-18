// -------------------------------------------------------------
// Assignment 2
// Written by: Adam Kozman - 40341342
//              Alexander Chamoun - 40341371
// -------------------------------------------------------------
// Handles saving and loading Trip objects to/from CSV.
// CSV format (semicolon-separated, NO header):
//   TripID;ClientID;AccommodationID;TransportationID;Destination;DurationDays;BasePrice
//
// AccommodationID and TransportationID are optional (can be empty string).
// ClientID is mandatory and must exist in the loaded clients array.
// If any referenced ID does not exist, the trip is skipped and logged.
//
// Invalid lines are skipped and logged via ErrorLogger.

package persistence;

import client.Client;
import travel.*;
import exceptions.InvalidTripDataException;

import java.io.*;

public class TripFileManager {

    /**
     * Saves trips[0..tripCount] to the given CSV file path.
     */
    public static void saveTrips(Trip[] trips, int tripCount, String filePath) throws IOException {
        new File(filePath).getParentFile().mkdirs();
        PrintWriter writer = new PrintWriter(new FileWriter(filePath));

        for (int i = 0; i < tripCount; i++) {
            Trip t = trips[i];
            String clientId  = (t.getClient()        != null) ? t.getClient().getClientId()           : "";
            String accId     = (t.getAccommodation()  != null) ? t.getAccommodation().getAccommodationId() : "";
            String transId   = (t.getTransportation() != null) ? t.getTransportation().getTransportId()    : "";
            writer.println(t.getTripId() + ";" + clientId + ";" + accId + ";" + transId + ";"
                    + t.getDestination() + ";" + t.getDurationInDays() + ";" + t.getBasePrice());
        }
        writer.close();
    }

    /**
     * Loads trips from the CSV file, resolving references to already-loaded
     * clients, accommodations, and transportations.
     *
     * @param trips           array to populate
     * @param filePath        path to trips.csv
     * @param clients         loaded clients array
     * @param clientCount     number of valid clients
     * @param accommodations  loaded accommodations array
     * @param accCount        number of valid accommodations
     * @param transportations loaded transportations array
     * @param transCount      number of valid transportations
     * @return number of successfully loaded trips
     */
    public static int loadTrips(Trip[] trips, String filePath,
                                 Client[] clients, int clientCount,
                                 Accommodation[] accommodations, int accCount,
                                 Transportation[] transportations, int transCount) {
        int count = 0;
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";", -1); // -1 keeps trailing empty tokens
                if (parts.length < 7) {
                    ErrorLogger.log("trips.csv line " + lineNumber
                            + ": expected 7 fields, got " + parts.length + " -> [" + line + "]");
                    continue;
                }

                String tripId   = parts[0].trim();
                String clientId = parts[1].trim();
                String accId    = parts[2].trim();
                String transId  = parts[3].trim();
                String dest     = parts[4].trim();

                int days;
                try {
                    days = Integer.parseInt(parts[5].trim());
                } catch (NumberFormatException e) {
                    ErrorLogger.log("trips.csv line " + lineNumber
                            + ": invalid duration '" + parts[5].trim() + "' -> [" + line + "]");
                    continue;
                }

                double basePrice;
                try {
                    basePrice = Double.parseDouble(parts[6].trim());
                } catch (NumberFormatException e) {
                    ErrorLogger.log("trips.csv line " + lineNumber
                            + ": invalid base price '" + parts[6].trim() + "' -> [" + line + "]");
                    continue;
                }

                // Resolve client (mandatory)
                Client client = findClientById(clients, clientCount, clientId);
                if (client == null) {
                    ErrorLogger.log("trips.csv line " + lineNumber
                            + ": client '" + clientId + "' not found -> [" + line + "]");
                    continue;
                }

                // Build trip
                Trip trip;
                try {
                    trip = new Trip(dest, days, basePrice, client);
                } catch (InvalidTripDataException e) {
                    ErrorLogger.log("trips.csv line " + lineNumber
                            + ": validation error - " + e.getMessage() + " -> [" + line + "]");
                    continue;
                }

                // Restore the original trip ID from file
                trip.setTripId(tripId);

                // Resolve accommodation (optional)
                if (!accId.isEmpty()) {
                    Accommodation acc = findAccommodationById(accommodations, accCount, accId);
                    if (acc != null) {
                        trip.setAccommodation(acc);
                    } else {
                        ErrorLogger.log("trips.csv line " + lineNumber
                                + ": accommodation '" + accId + "' not found (trip loaded without it) -> [" + line + "]");
                    }
                }

                // Resolve transportation (optional)
                if (!transId.isEmpty()) {
                    Transportation trans = findTransportationById(transportations, transCount, transId);
                    if (trans != null) {
                        trip.setTransportation(trans);
                    } else {
                        ErrorLogger.log("trips.csv line " + lineNumber
                                + ": transportation '" + transId + "' not found (trip loaded without it) -> [" + line + "]");
                    }
                }

                trips[count++] = trip;
                if (count >= trips.length) break;
            }
        } catch (FileNotFoundException e) {
            ErrorLogger.log("trips.csv not found at: " + filePath);
        } catch (IOException e) {
            ErrorLogger.log("trips.csv read error: " + e.getMessage());
        }

        return count;
    }


    // ---- Helper: linear search by ID ----

    private static Client findClientById(Client[] clients, int count, String id) {
        for (int i = 0; i < count; i++)
            if (clients[i].getClientId().equalsIgnoreCase(id)) return clients[i];
        return null;
    }

    private static Accommodation findAccommodationById(Accommodation[] accs, int count, String id) {
        for (int i = 0; i < count; i++)
            if (accs[i].getAccommodationId().equalsIgnoreCase(id)) return accs[i];
        return null;
    }

    private static Transportation findTransportationById(Transportation[] trans, int count, String id) {
        for (int i = 0; i < count; i++)
            if (trans[i].getTransportId().equalsIgnoreCase(id)) return trans[i];
        return null;
    }
}
