package Controller;

import Model.MonthlyReport;
import Model.MonthlyReport;
import View.adminView;

public class MonthlyReportController {
    private model.MonthlyReportDAO dao;
    private adminView view;

    public MonthlyReportController(adminView view) {
        this.dao = new model.MonthlyReportDAO();
        this.view = view;
        initialize();
    }

    private void initialize() {
        // Populate year/month dropdowns
        view.getComboBoxYear().addItem(2025);
        view.getComboBoxMonth().addItem("January");

        // Add generate button listener
        view.getBtnGenerate().addActionListener(e -> generateReport());
    }

    private void generateReport() {
        int year = (int) view.getComboBoxYear().getSelectedItem();
        String month = (String) view.getComboBoxMonth().getSelectedItem();

        MonthlyReport report = dao.generateReport(year, month);

        // Update GUI labels
        view.getLblTotalDeliveriesNumber().setText(String.valueOf(report.getTotalDeliveries()));
        view.getLblDelayedDeliveriesNumber().setText(String.valueOf(report.getDelayedDeliveries()));
        view.getLblAverageRatingNumber().setText(String.format("%.1f", report.getAverageRating()));
        view.getLblTotalShipmentsNumber().setText(String.valueOf(report.getTotalShipments()));
    }
}