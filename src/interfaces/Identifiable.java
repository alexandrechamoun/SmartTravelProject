// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Marks any SmartTravel entity that has a unique string ID.
// Implemented by: Client, Trip, Accommodation, Transportation.

package interfaces;

public interface Identifiable {
    String getId(); // Returns "C1001", "T2001", "A4001", "TR3001", etc.
}