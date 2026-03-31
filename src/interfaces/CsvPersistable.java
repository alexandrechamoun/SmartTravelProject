// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Marks any SmartTravel entity that can serialize itself to a
// CSV row string matching the A2 file format.
// Implemented by: Client, Trip, Accommodation, Transportation.
// Each implementing class also provides a static fromCsvRow()
// factory method for deserialization (not enforced by interface
// since Java interfaces cannot declare static methods that
// subclasses must implement).

package interfaces;

public interface CsvPersistable {
    // Returns a semicolon-delimited CSV string matching A2 format.
    // e.g. "C1001;Sophia;Rossi;sophia@example.com"
    String toCsvRow();
}