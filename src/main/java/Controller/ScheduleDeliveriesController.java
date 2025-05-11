package Controller;

import Model.ScheduleDeliveriesDAO;
import Model.TrackShipmentProgress;
import java.util.List;

public class ScheduleDeliveriesController {

    private ScheduleDeliveriesDAO scheduleDeliveriesDAO;

    public ScheduleDeliveriesController() {
        this.scheduleDeliveriesDAO = new ScheduleDeliveriesDAO();
    }

    public List<TrackShipmentProgress> getTrackShipmentProgressForUser(int userId) {
        return scheduleDeliveriesDAO.getTrackShipmentProgressByUserId(userId);
    }
}
