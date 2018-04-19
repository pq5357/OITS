package sg.rt.oits;

/**
 * Created by sg on 2018/4/19.
 */

public class AlertEvent {


    public static final String ALERT_STOP_OUT =  "alert_stop_out";
    public static final String ALERT_RUSH_HEAD =  "alert_rush_head";
    public static final String ALERT_RUSH_BOTTOM =  "alert_rush_bottom";
    public static final String ALERT_RUNNING_DOOR_OPEN =  "alert_running_door_open";
    public static final String ALERT_OVERSPEED =  "alert_overspeed";
    public static final String ALERT_DOOR_RUNNING =  "alert_door_running";

    String alert_name;

    public AlertEvent(String alert_name) {
        this.alert_name = alert_name;
    }


    public String getAlert_name() {
        return alert_name;
    }

}
