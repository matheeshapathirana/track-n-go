package Controller;

import Model.MonthlyReport;
import View.adminView;

public class MonthlyReportController {
    private Model.MonthlyReportDAO dao;
    private adminView view;

    public MonthlyReportController(adminView view) {
        this.dao = new Model.MonthlyReportDAO();
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.getBtnGenerate().addActionListener(e -> generateReport());
    }

    private void generateReport() {
        int year = Integer.parseInt((String) view.getComboBoxYear().getSelectedItem());
        String month = (String) view.getComboBoxMonth().getSelectedItem();

        MonthlyReport report = dao.generateReport(year, month);

        view.getLblTotalDeliveriesNumber().setText(String.valueOf(report.getTotalDeliveries()));
        view.getLblDelayedDeliveriesNumber().setText(String.valueOf(report.getDelayedDeliveries()));
        view.getLblTotalShipmentsNumber().setText(String.valueOf(report.getTotalShipments()));
    }
}