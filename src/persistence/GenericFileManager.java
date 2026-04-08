// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Part 4: Generic File Manager
// One class that replaces all four A2 file managers.
// Uses toCsvRow() to save and getSimpleName() to figure out
// which fromCsvRow() to call when loading.
// Trip loading is still handled by TripFileManager because trips
// need client/accommodation/transportation to already be loaded first.
// The old A2 file managers are kept so menus 1-6 and 8-11 still work.

package persistence;

import client.Client;
import exceptions.*;
import interfaces.CsvPersistable;
import travel.Accommodation;
import travel.Transportation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenericFileManager {

    // ================= SAVE =================

    // saves any list of CsvPersistable objects to a file
    // just calls toCsvRow() on each item — no type-specific code needed
    public static <T extends CsvPersistable> void save(List<T> items, String filePath)
            throws IOException {
        new File(filePath).getParentFile().mkdirs();
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (T item : items) {
                writer.println(item.toCsvRow());
            }
        }
    }


    // ================= LOAD =================

    // loads a CSV file and rebuilds objects using the right fromCsvRow()
    // we check the class name to know which factory method to call
    // bad lines are skipped and logged instead of crashing
    public static <T extends CsvPersistable> List<T> load(String filePath, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    T item = fromCsvRow(line, clazz);
                    if (item != null) result.add(item);
                } catch (Exception e) {
                    ErrorLogger.log(filePath + " line " + lineNumber
                            + ": " + e.getMessage() + " -> [" + line + "]");
                }
            }
        } catch (FileNotFoundException e) {
            ErrorLogger.log("GenericFileManager: file not found at " + filePath);
        } catch (IOException e) {
            ErrorLogger.log("GenericFileManager: read error for " + filePath
                    + " - " + e.getMessage());
        }

        return result;
    }


    // ================= PRIVATE DISPATCH =================

    // picks the right fromCsvRow() based on the class name
    // Client, Accommodation, and Transportation are supported
    // Trip is not — TripFileManager handles that
    @SuppressWarnings("unchecked")
    private static <T extends CsvPersistable> T fromCsvRow(String csvLine, Class<T> clazz)
            throws Exception {
        switch (clazz.getSimpleName()) {
            case "Client":
                return (T) Client.fromCsvRow(csvLine);

            case "Accommodation":
                // Accommodation.fromCsvRow() checks the type prefix (HOTEL or HOSTEL)
                return (T) Accommodation.fromCsvRow(csvLine);

            case "Transportation":
                // Transportation.fromCsvRow() checks the type prefix (FLIGHT, TRAIN, BUS)
                return (T) Transportation.fromCsvRow(csvLine);

            default:
                ErrorLogger.log("GenericFileManager: unsupported type '"
                        + clazz.getSimpleName() + "' — use the specific FileManager.");
                return null;
        }
    }
}
