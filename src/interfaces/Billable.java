// -------------------------------------------------------------
// Assignment 3
// Written by: Adam Kozman - 40341342
//             Alexandre Chamoun - 40341371
// -------------------------------------------------------------
// Marks any SmartTravel entity that has a price and a total cost.
// Implemented by: Trip only.

package interfaces;

public interface Billable {
    double getBasePrice();
    double getTotalCost(); // Includes accommodation + transportation costs
}