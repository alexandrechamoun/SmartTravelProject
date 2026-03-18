// -------------------------------------------------------------
// Assignment 2
// Written by: Adam Kozman - 40341342
//              Alexander Chamoun - 40341371
// -------------------------------------------------------------
// Handles saving and loading Accommodation objects (Hotel, Hostel) to/from CSV.
// CSV format (semicolon-separated, NO header):
//   HOTEL;AccommodationID;name;location;pricePerNight;starRating
//   HOSTEL;AccommodationID;name;location;pricePerNight;numOfSharedBedsPerRoom
//
// Invalid or unrecognized lines are skipped and logged via ErrorLogger.

package persistence;

import travel.Accommodation;
import travel.Hotel;
import travel.Hostel;
import exceptions.InvalidAccommodationDataException;

import java.io.*;

public class AccommodationFileManager {

    /**
     * Saves accommodations[0..count] to the given CSV file path.
     */
    public static void saveAccommodations(Accommodation[] accommodations, int count,
                                          String filePath) throws IOException {
        new File(filePath).getParentFile().mkdirs();
        PrintWriter writer = new PrintWriter(new FileWriter(filePath));

        for (int i = 0; i < count; i++) {
            Accommodation a = accommodations[i];
            if (a instanceof Hotel) {
                Hotel h = (Hotel) a;
                writer.println("HOTEL;" + h.getAccommodationId() + ";" + h.getName() + ";"
                        + h.getLocation() + ";" + h.getPricePerNight() + ";" + h.getStarRating());
            } else if (a instanceof Hostel) {
                Hostel hs = (Hostel) a;
                writer.println("HOSTEL;" + hs.getAccommodationId() + ";" + hs.getName() + ";"
                        + hs.getLocation() + ";" + hs.getPricePerNight() + ";"
                        + hs.getNumOfSharedBedsPerRoom());
            }
        }
        writer.close();
    }

    /**
     * Loads accommodations from the CSV file and populates the array.
     * Skips and logs invalid/unrecognized lines.
     *
     * @return number of successfully loaded accommodations
     */
    public static int loadAccommodations(Accommodation[] accommodations, String filePath) {
        int count = 0;
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length < 6) {
                    ErrorLogger.log("accommodations.csv line " + lineNumber
                            + ": expected at least 6 fields, got " + parts.length
                            + " -> [" + line + "]");
                    continue;
                }

                String type  = parts[0].trim().toUpperCase();
                String accId = parts[1].trim();
                String name  = parts[2].trim();
                String loc   = parts[3].trim();

                double price;
                try {
                    price = Double.parseDouble(parts[4].trim());
                } catch (NumberFormatException e) {
                    ErrorLogger.log("accommodations.csv line " + lineNumber
                            + ": invalid price '" + parts[4].trim() + "' -> [" + line + "]");
                    continue;
                }

                try {
                    if (type.equals("HOTEL")) {
                        int stars;
                        try {
                            stars = Integer.parseInt(parts[5].trim());
                        } catch (NumberFormatException e) {
                            ErrorLogger.log("accommodations.csv line " + lineNumber
                                    + ": invalid star rating '" + parts[5].trim()
                                    + "' -> [" + line + "]");
                            continue;
                        }
                        Hotel h = new Hotel(name, loc, price, stars);
                        h.setAccommodationId(accId);
                        accommodations[count++] = h;

                    } else if (type.equals("HOSTEL")) {
                        int beds;
                        try {
                            beds = Integer.parseInt(parts[5].trim());
                        } catch (NumberFormatException e) {
                            ErrorLogger.log("accommodations.csv line " + lineNumber
                                    + ": invalid beds '" + parts[5].trim()
                                    + "' -> [" + line + "]");
                            continue;
                        }
                        Hostel hs = new Hostel(name, loc, price, beds);
                        hs.setAccommodationId(accId);
                        accommodations[count++] = hs;

                    } else {
                        ErrorLogger.log("accommodations.csv line " + lineNumber
                                + ": unknown type '" + type + "' -> [" + line + "]");
                        continue;
                    }
                } catch (InvalidAccommodationDataException e) {
                    ErrorLogger.log("accommodations.csv line " + lineNumber
                            + ": validation error - " + e.getMessage() + " -> [" + line + "]");
                }

                if (count >= accommodations.length) break;
            }
        } catch (FileNotFoundException e) {
            ErrorLogger.log("accommodations.csv not found at: " + filePath);
        } catch (IOException e) {
            ErrorLogger.log("accommodations.csv read error: " + e.getMessage());
        }

        return count;
    }
}
