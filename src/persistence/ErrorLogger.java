// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Logs all CSV load errors to output/logs/errors.txt.
// Uses append mode so errors from different load sessions accumulate.

package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLogger {

    private static final String LOG_FILE = "output/logs/errors.txt";

    /**
     * Appends an error message to the log file with a timestamp.
     *
     * @param message the error description
     */
    public static void log(String message) {
        try {
            // Ensure the logs directory exists
            new File("output/logs").mkdirs();

            PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true));
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.println("[" + timestamp + "] " + message);
            writer.close();
        } catch (IOException e) {
            System.out.println("[ErrorLogger] Could not write to log file: " + e.getMessage());
        }
    }
}
