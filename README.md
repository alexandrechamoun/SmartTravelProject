SmartTravel A3 — Collections, Interfaces & Generics

Course: COMP 249 — Object-Oriented Programming II, Winter 2026
Assignment: 3

Authors: Alexandre Chamoun (40341371) & Adam Kozman (40341342)


Project Overview:
SmartTravel is a console-based travel agency management system.
This assignment extends A2 by adding:

  - Collections       : All 4 fixed-size arrays replaced with dynamic ArrayList<T>
  - Interfaces        : Identifiable, Billable, CsvPersistable, Comparable<T>
                        implemented across all model classes
  - Generic Utilities : Repository<T>, GenericFileManager<T>, RecentList<T>
  - Predicate Filtering: Reusable Predicate<T>-based search in Repository
  - Business Sorting  : Natural ordering via Comparable<T> on every entity
  - Advanced Analytics: New Menu 7 (7.1–7.6) showcasing all A3 features
  - All A2 functionality (menus 1-6, 8-11, file I/O, dashboard) preserved unchanged


A3 Changes by Part:

Part 1 — Collections Foundation
  - SmartTravelService: replaced Client[], Trip[], Accommodation[], Transportation[]
    and their count variables with ArrayList<T>
  - All service methods updated to use list operations (add, get, size, remove)
  - getAllTrips(), getAllClients(), etc. return arrays on demand for backward compatibility

Part 2 — Interfaces
  - interfaces/Identifiable.java      : getId()
  - interfaces/Billable.java          : getBasePrice(), getTotalCost()  [Trip only]
  - interfaces/CsvPersistable.java    : toCsvRow()
  - All model classes implement appropriate interfaces + Comparable<T>
  - Natural sort order: Client (descending amountSpent), Trip (descending totalCost),
    Accommodation (descending pricePerNight), Transportation (descending baseFare)

Part 3 — Generic Repository
  - service/Repository.java: add(), findById(), filter(Predicate<T>), getSorted()
  - Works with any T that is Identifiable & Comparable

Part 4 — Generic File Manager
  - persistence/GenericFileManager.java: replaces 4 specific file managers
  - load(filePath, Class<T>) and save(List<T>, filePath) handle Client,
    Accommodation, and Transportation generically
  - Trip loading still uses TripFileManager because it requires cross-references
    to already-loaded clients/accommodations/transportations
  - SmartTravelService.loadAllData() uses GenericFileManager for the first 3 types

Part 5 — Advanced Analytics (Menu 7)
  7.1  Trips by Destination  — Predicate filter
  7.2  Trips by Cost Range   — Predicate range filter
  7.3  Top Clients by Spending — Repository.getSorted() + Client.compareTo()
  7.4  Recent Trips          — RecentList<Trip> demo (LinkedList-backed)
  7.5  Smart Sort Collections — all 4 entity types sorted by business order
  7.6  Back to main menu


Why LinkedList for RecentList<T>?

RecentList always inserts at the front (addFirst) and removes from the back
(removeLast) when over capacity. Both operations are O(1) on a LinkedList
because it maintains direct head/tail node references — no element shifting.
An ArrayList would need to shift every element on every addFirst(), making it
O(n). Since RecentList never needs random index access, LinkedList is the
natural and more efficient choice for this use case.


How to Run:

1. Open the project in IntelliJ (or any Java IDE)
2. Navigate to src/driver/SmartTravelDriver.java
3. Right-click → Run 'SmartTravelDriver.main()'
4. The console will display a welcome message and the main menu

Menu options:
  1-6   — Client, Trip, Transport, Accommodation management + charts (A1/A2, unchanged)
  7     — Advanced Analytics (NEW in A3)
  8     — Load all data from data/*.csv
  9     — Save all data to data/*.csv
  10    — Run predefined scenario (demonstrates all 6 exceptions)
  11    — Generate HTML dashboard + charts
  0     — Exit

CSV data files are located in: data/
Log file for load errors:       output/logs/errors.txt
