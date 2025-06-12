package Model;

public class MonthlyReport {
    private int totalDeliveries;
    private int delayedDeliveries;
    private double averageRating;
    private int totalShipments;

    public int getTotalDeliveries() { return totalDeliveries; }
    public void setTotalDeliveries(int totalDeliveries) { this.totalDeliveries = totalDeliveries; }

    public int getDelayedDeliveries() { return delayedDeliveries; }
    public void setDelayedDeliveries(int delayedDeliveries) { this.delayedDeliveries = delayedDeliveries; }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public int getTotalShipments() { return totalShipments; }
    public void setTotalShipments(int totalShipments) { this.totalShipments = totalShipments; }
}