package sg.rt.oits;

/**
 * Created by sg on 2018/4/16.
 */

public enum Operation {

    OUT_UP("out_up"),
    OUT_DOWN("out_down"),
    IN_CALL("in_call"),
    IN_FLOOR("floor"),
    IN_OPEN("in_open"),
    IN_CLOSE("in_close");

    private String content;

    private int floor;

    Operation(String content) {
        this.content = content;
    }

    public Operation setFloor(int floor) {
        this.floor = floor;
    }
}
