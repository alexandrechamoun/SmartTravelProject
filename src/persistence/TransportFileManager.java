// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Handles saving and loading Transportation objects (Flight, Train, Bus) to/from CSV.
// CSV format (semicolon-separated, NO header):
//   FLIGHT;TransportID;companyName;departureCity;arrivalCity;baseFare;luggageAllowance
//   TRAIN;TransportID;companyName;departureCity;arrivalCity;baseFare;trainType   (seatClass missing -> stored as trainType;seatClass)
//   BUS;TransportID;companyName;departureCity;arrivalCity;baseFare;numOfStops
//
// Actual sample format from instructions:
//   FLIGHT;TR3001;Alitalia;JFK;FCO;850.00;23.0
//   TRAIN;TR3002;Shinkansen;Tokyo;Kyoto;250.00;HighSpeed
//   BUS;TR3003;Greyhound;NYC;Boston;75.00;3
//
// For TRAIN: field[6] is trainType; seatClass defaults to "Economy".
// Invalid/unrecognized lines are skipped and logged via ErrorLogger.

package persistence;

import travel.Transportation;
import travel.Flight;
import travel.Train;
import travel.Bus;
import exceptions.InvalidTransportDataException;

import java.io.*;

public class TransportFileManager {

    /**
     * Saves transportations[0..count] to the given CSV file path.
     */
    public static void saveTransportations(Transportation[] transportations, int count,
                                           String filePath) throws IOException {
        new File(filePath).getParentFile().mkdirs();
        PrintWriter writer = new PrintWriter(new FileWriter(filePath));

        for (int i = 0; i < count; i++) {
            Transportation t = transportations[i];
            if (t instanceof Flight) {
                Flight f = (Flight) t;
                writer.println("FLIGHT;" + f.getTransportId() + ";" + f.getCompanyName() + ";"
                        + f.getDepartureCity() + ";" + f.getArrivalCity() + ";"
                        + f.getBaseFare() + ";" + f.getLuggageAllowance());
            } else if (t instanceof Train) {
                Train tr = (Train) t;
                writer.println("TRAIN;" + tr.getTransportId() + ";" + tr.getCompanyName() + ";"
                        + tr.getDepartureCity() + ";" + tr.getArrivalCity() + ";"
                        + tr.getBaseFare() + ";" + tr.getTrainType() + ";" + tr.getSeatClass());
            } else if (t instanceof Bus) {
                Bus b = (Bus) t;
                writer.println("BUS;" + b.getTransportId() + ";" + b.getCompanyName() + ";"
                        + b.getDepartureCity() + ";" + b.getArrivalCity() + ";"
                        + b.getBaseFare() + ";" + b.getNumOfStops());
            }
        }
        writer.close();
    }

    /**
     * Loads transportations from the CSV file and populates the array.
     * Skips and logs invalid/unrecognized lines.
     *
     * @return number of successfully loaded transportations
     */
    public static int loadTransportations(Transportation[] transportations, String filePath) {
        int count = 0;
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length < 7) {
                    ErrorLogger.log("transports.csv line " + lineNumber
                            + ": expected at least 7 fields, got " + parts.length
                            + " -> [" + line + "]");
                    continue;
                }

                String type      = parts[0].trim().toUpperCase();
                String transId   = parts[1].trim();
                String company   = parts[2].trim();
                String departure = parts[3].trim();
                String arrival   = parts[4].trim();

                double fare;
                try {
                    fare = Double.parseDouble(parts[5].trim());
                } catch (NumberFormatException e) {
                    ErrorLogger.log("transports.csv line " + lineNumber
                            + ": invalid fare '" + parts[5].trim() + "' -> [" + line + "]");
                    continue;
                }

                try {
                    if (type.equals("FLIGHT")) {
                        double luggage;
                        try {
                            luggage = Double.parseDouble(parts[6].trim());
                        } catch (NumberFormatException e) {
                            ErrorLogger.log("transports.csv line " + lineNumber
                                    + ": invalid luggage '" + parts[6].trim()
                                    + "' -> [" + line + "]");
                            continue;
                        }
                        Flight f = new Flight(company, departure, arrival, company, luggage, fare);
                        f.setTransportId(transId);
                        transportations[count++] = f;

                    } else if (type.equals("TRAIN")) {
                        String trainType = parts[6].trim();
                        // seatClass is optional (field[7]) - default to "Economy"
                        String seatClass = (parts.length > 7) ? parts[7].trim() : "Economy";
                        Train t = new Train(company, departure, arrival, trainType, seatClass, fare);
                        t.setTransportId(transId);
                        transportations[count++] = t;

                    } else if (type.equals("BUS")) {
                        int stops;
                        try {
                            stops = Integer.parseInt(parts[6].trim());
                        } catch (NumberFormatException e) {
                            ErrorLogger.log("transports.csv line " + lineNumber
                                    + ": invalid stops '" + parts[6].trim()
                                    + "' -> [" + line + "]");
                            continue;
                        }
                        Bus b = new Bus(company, departure, arrival, company, stops, fare);
                        b.setTransportId(transId);
                        transportations[count++] = b;

                    } else {
                        ErrorLogger.log("transports.csv line " + lineNumber
                                + ": unknown type '" + type + "' -> [" + line + "]");
                        continue;
                    }
                } catch (InvalidTransportDataException e) {
                    ErrorLogger.log("transports.csv line " + lineNumber
                            + ": validation error - " + e.getMessage() + " -> [" + line + "]");
                }

                if (count >= transportations.length) break;
            }
        } catch (FileNotFoundException e) {
            ErrorLogger.log("transports.csv not found at: " + filePath);
        } catch (IOException e) {
            ErrorLogger.log("transports.csv read error: " + e.getMessage());
        }

        return count;
    }
}
