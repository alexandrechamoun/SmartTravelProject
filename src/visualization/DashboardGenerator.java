package visualization;

/**
 * DashboardGenerator - Generates a professional HTML Dashboard for SmartTravel Pro (COMP249 A2)
 *
 * Features:
 * - Responsive tables for Clients and Trips
 * - JFreeChart integration (bar/pie/line charts)
 * - Bookman Old Style typography with Google Fonts fallback
 * - Auto-opens in default browser
 * - Professional CSS animations and hover effects
 *
 * Usage: DashboardGenerator.generateDashboard(service);
 *
 * Output: output/dashboard/dashboard.html + 3 chart PNGs
 *
 * @author [Your Name] & Kaustubha Mendhurwar
 * @version Winter 2026 - A2
 */

import service.SmartTravelService;
import client.Client;
import travel.Trip;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class DashboardGenerator {

    /**
     * Main entry point for dashboard generation.
     *
     * Workflow:
     *  1. Generates JFreeChart PNGs (cost bar, destination pie, duration line)
     *  2. Writes HTML with embedded CSS and tables
     *  3. Auto-opens output/dashboard/dashboard.html in browser
     *
     * @param service SmartTravelService with populated Client/Trip arrays
     * @throws IOException if file I/O fails (output dir/charts)
     */
    public static void generateDashboard(SmartTravelService service) throws IOException {
        // Ensure output directories exist
        new File("output/charts").mkdirs();
        new File("output/dashboard").mkdirs();
        new File("output/logs").mkdirs();

        // 1. Generate charts FIRST
        TripChartGenerator.generateCostBarChart(service);
        TripChartGenerator.generateDestinationPieChart(service);
        TripChartGenerator.generateDurationLineChart(service);

        // 2. Generate HTML dashboard
        generateHTMLDashboard(service);

        // 3. Auto-open in browser
        openInBrowser();

        System.out.println("Dashboard generated and opened!");
    }

    /**
     * Generates complete HTML dashboard with responsive design.
     */
    private static void generateHTMLDashboard(SmartTravelService service) throws IOException {
        PrintWriter out = new PrintWriter("output/dashboard/dashboard.html");
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("    <title>SmartTravel A2 Dashboard</title>");
        out.println("    <link rel='stylesheet' href='styles.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='container'>");
        writeSummary(service, out);
        writeClientsTable(service, out);
        writeTripsTable(service, out);
        writeChartsSection(out);
        writeStats(service, out);
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     * Writes dashboard header with dynamic stats.
     */
    private static void writeSummary(SmartTravelService service, PrintWriter out) {
        out.println("        <header>");
        out.println("            <h1>SmartTravel Dashboard</h1>");
        out.println("            <p>A2: File I/O + Exceptions | "
                + service.getClientCount() + " Clients | " + service.getTripCount() + " Trips</p>");
        out.println("        </header>");
    }

    /**
     * Generates responsive Clients table from service data.
     * Columns: ID (bold), Full Name, Email, Total Spent
     */
    private static void writeClientsTable(SmartTravelService service, PrintWriter out) {
        out.println("        <section class='data-section'>");
        out.println("            <h2> Clients (" + service.getClientCount() + ")</h2>");
        out.println("            <table>");
        out.println("                <thead>");
        out.println("                    <tr><th>ID</th><th>Name</th><th>Email</th><th>Total Spent ($)</th></tr>");
        out.println("                </thead>");
        out.println("                <tbody>");

        for (int i = 0; i < service.getClientCount(); i++) {
            Client client = service.getClient(i);
            double spent = client.getAmountSpent();
            out.println("                    <tr>");
            out.println("                        <td><strong>" + client.getClientId() + "</strong></td>");
            out.println("                        <td>" + client.getFirstName() + " " + client.getLastName() + "</td>");
            out.println("                        <td>" + client.getEmail() + "</td>");
            out.println("                        <td style='font-weight: bold; color: "
                    + (spent > 3000 ? "#d32f2f" : "#388e3c") + ";'>"
                    + String.format("%,.2f", spent) + "</td>");
            out.println("                    </tr>");
        }

        out.println("                </tbody>");
        out.println("            </table>");
        out.println("        </section>");
    }

    /**
     * Generates responsive Trips table.
     * Columns: ID, Client ID, Destination, Days, Total Price
     */
    private static void writeTripsTable(SmartTravelService service, PrintWriter out) {
        out.println("        <section class='data-section'>");
        out.println("            <h2> Trips (" + service.getTripCount() + ")</h2>");
        out.println("            <table>");
        out.println("                <thead>");
        out.println("                    <tr><th>ID</th><th>Client</th><th>Destination</th><th>Days</th><th>Price</th></tr>");
        out.println("                </thead>");
        out.println("                <tbody>");

        for (int i = 0; i < service.getTripCount(); i++) {
            Trip trip = service.getTrip(i);
            String clientId = (trip.getClient() != null) ? trip.getClient().getClientId() : "N/A";
            out.println("                    <tr>");
            out.println("                        <td><strong>" + trip.getTripId() + "</strong></td>");
            out.println("                        <td>" + clientId + "</td>");
            out.println("                        <td>" + trip.getDestination() + "</td>");
            out.println("                        <td>" + trip.getDurationInDays() + "</td>");
            out.println("                        <td>$" + String.format("%.2f", service.calculateTripTotal(i)) + "</td>");
            out.println("                    </tr>");
        }

        out.println("                </tbody>");
        out.println("            </table>");
        out.println("        </section>");
    }

    /**
     * Embeds 3 JFreeChart PNGs in responsive grid.
     */
    private static void writeChartsSection(PrintWriter out) {
        out.println("        <section class='charts-section'>");
        out.println("            <h2>Analytics (JFreeChart)</h2>");
        out.println("            <div class='chart-grid'>");
        out.println("                <figure class='chart-card'>");
        out.println("                    <img src='../charts/trip_cost_bar_chart.png' alt='Trip Costs' loading='lazy'>");
        out.println("                    <figcaption>Total Cost per Trip</figcaption>");
        out.println("                </figure>");
        out.println("                <figure class='chart-card'>");
        out.println("                    <img src='../charts/trips_per_destination_pie.png' alt='Destinations' loading='lazy'>");
        out.println("                    <figcaption>Trips per Destination</figcaption>");
        out.println("                </figure>");
        out.println("                <figure class='chart-card'>");
        out.println("                    <img src='../charts/trip_duration_line_chart.png' alt='Durations' loading='lazy'>");
        out.println("                    <figcaption>Trip Durations</figcaption>");
        out.println("                </figure>");
        out.println("            </div>");
        out.println("        </section>");
    }

    /**
     * Calculates and displays key business metrics.
     * Stats: Average Trip Cost, Average Duration, Total Revenue, Most Visited Destination.
     */
    private static void writeStats(SmartTravelService service, PrintWriter out) {

        int tripCount = service.getTripCount();
        if (tripCount == 0) {
            out.println("        <section class='stats-section'>");
            out.println("            <h2>No Trip Data</h2>");
            out.println("        </section>");
            return;
        }

        // 1. Total Revenue & Average Cost
        double totalRevenue = 0.0;
        for (int i = 0; i < tripCount; i++) {
            totalRevenue += service.calculateTripTotal(i);
        }
        double avgCost = totalRevenue / tripCount;

        // 2. Average Duration (days)
        double totalDays = 0.0;
        for (int i = 0; i < tripCount; i++) {
            totalDays += service.getTrip(i).getDurationInDays();
        }
        double avgDuration = totalDays / tripCount;

        // 3. Most visited destination
        String mostVisited = findMostVisitedDestination(service);
        int    visitCount  = countDestinationVisits(service, mostVisited);

        out.println("        <section class='stats-section'>");
        out.println("            <h2>Quick Stats (" + tripCount + " Trips)</h2>");
        out.println("            <div class='stat-grid'>");

        // Stat 1: Average Cost
        out.println("                <div class='stat-item'>");
        out.println("                    <span class='stat-label'>Avg Trip Cost</span>");
        out.println("                    <span class='stat-value'>$" + String.format("%,.0f", avgCost) + "</span>");
        out.println("                </div>");

        // Stat 2: Average Duration
        out.println("                <div class='stat-item'>");
        out.println("                    <span class='stat-label'>Avg Duration</span>");
        out.println("                    <span class='stat-value'>" + String.format("%.1f", avgDuration) + " days</span>");
        out.println("                </div>");

        // Stat 3: Total Revenue
        out.println("                <div class='stat-item'>");
        out.println("                    <span class='stat-label'>Total Revenue</span>");
        out.println("                    <span class='stat-value'>$" + String.format("%,.0f", totalRevenue) + "</span>");
        out.println("                </div>");

        // Stat 4: Most Visited
        out.println("                <div class='stat-item'>");
        out.println("                    <span class='stat-label'>Most Visited</span>");
        out.println("                    <span class='stat-value'>" + mostVisited
                + "<br><small>(" + visitCount + " trips)</small></span>");
        out.println("                </div>");

        out.println("            </div>");
        out.println("        </section>");
    }

    /**
     * Cross-platform browser launcher for dashboard.html.
     * Supports Windows (rundll32), macOS (open), Linux (xdg-open).
     */
    private static void openInBrowser() {
        try {
            String os  = System.getProperty("os.name").toLowerCase();
            String url = new File("output/dashboard/dashboard.html").getAbsolutePath();

            ProcessBuilder pb;
            if (os.contains("win")) {
                pb = new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", url);
            } else if (os.contains("mac")) {
                pb = new ProcessBuilder("open", url);
            } else {
                pb = new ProcessBuilder("xdg-open", url);
            }
            pb.start();
        } catch (IOException e) {
            System.out.println("Open output/dashboard/dashboard.html manually in your browser.");
        }
    }

    /* ==================== HELPER METHODS ==================== */

    /**
     * Finds the destination with the most trips.
     * Returns "N/A" if there are no trips.
     */
    private static String findMostVisitedDestination(SmartTravelService service) {
        int tripCount = service.getTripCount();
        if (tripCount == 0) return "N/A";

        String bestDest  = service.getTrip(0).getDestination();
        int    bestCount = 1;

        for (int i = 0; i < tripCount; i++) {
            String dest  = service.getTrip(i).getDestination();
            int    count = countDestinationVisits(service, dest);
            if (count > bestCount) {
                bestCount = count;
                bestDest  = dest;
            }
        }
        return bestDest;
    }

    /**
     * Counts trips to a specific destination (case-insensitive).
     */
    private static int countDestinationVisits(SmartTravelService service, String destination) {
        int count = 0;
        for (int i = 0; i < service.getTripCount(); i++) {
            if (service.getTrip(i).getDestination().equalsIgnoreCase(destination))
                count++;
        }
        return count;
    }
}
