// -------------------------------------------------------------
// Assignment 2
// Written by: Adam Kozman - 40341342
//              Alexander Chamoun - 40341371
// -------------------------------------------------------------
// Handles saving and loading Client objects to/from CSV.
// CSV format (semicolon-separated, NO header):
//   ClientID;firstName;lastName;email
// Example: C1001;Sophia;Rossi;sophia@example.com
//
// Invalid lines are skipped and logged via ErrorLogger.

package persistence;

import client.Client;
import exceptions.InvalidClientDataException;

import java.io.*;

public class ClientFileManager {

    /**
     * Saves clients[0..clientCount] to the given CSV file path.
     *
     * @param clients     the array of clients
     * @param clientCount number of valid clients
     * @param filePath    path to write the CSV
     * @throws IOException if the file cannot be written
     */
    public static void saveClients(Client[] clients, int clientCount, String filePath)
            throws IOException {
        // Ensure parent directories exist
        new File(filePath).getParentFile().mkdirs();

        PrintWriter writer = new PrintWriter(new FileWriter(filePath));
        for (int i = 0; i < clientCount; i++) {
            Client c = clients[i];
            writer.println(c.getClientId() + ";" + c.getFirstName() + ";"
                    + c.getLastName() + ";" + c.getEmail());
        }
        writer.close();
    }

    /**
     * Loads clients from the CSV file and populates the array.
     * Skips and logs any invalid lines.
     *
     * @param clients  the array to populate
     * @param filePath path to the CSV file
     * @return number of successfully loaded clients
     */
    public static int loadClients(Client[] clients, String filePath) {
        int count = 0;
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length != 4) {
                    ErrorLogger.log("clients.csv line " + lineNumber
                            + ": expected 4 fields, got " + parts.length + " -> [" + line + "]");
                    continue;
                }

                String clientId  = parts[0].trim();
                String firstName = parts[1].trim();
                String lastName  = parts[2].trim();
                String email     = parts[3].trim();

                try {
                    Client c = new Client(firstName, lastName, email);
                    c.setClientId(clientId); // restore original ID from file
                    clients[count++] = c;
                } catch (InvalidClientDataException e) {
                    ErrorLogger.log("clients.csv line " + lineNumber
                            + ": invalid data - " + e.getMessage() + " -> [" + line + "]");
                }

                if (count >= clients.length) break;
            }
        } catch (FileNotFoundException e) {
            ErrorLogger.log("clients.csv not found at: " + filePath);
        } catch (IOException e) {
            ErrorLogger.log("clients.csv read error: " + e.getMessage());
        }

        return count;
    }
}
