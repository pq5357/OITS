package sg.rt.oits;

/**
 * Created by sg on 2018/4/16.
 */

public class RefreshEvent {

    /**
     * 定义view操作
     */
    public static final int OPEN = 1;
    public static final int CLOSE = 2;
    public static final int REFRESH = 3;
    public static final int REFRESH_ARROW = 4;
    /**
     * 刷新操作类型
     */
    private int type;

    private boolean isLastFloor;

    /**
     * 需要操作的层数
     */
    private int floor;

    public RefreshEvent(int type) {
        this.type = type;
    }

    public RefreshEvent(int type, int floor) {
        this.type = type;
        this.floor = floor;
    }

    public RefreshEvent(int type, int floor, boolean isLastFloor) {
        this.type = type;
        this.isLastFloor = isLastFloor;
        this.floor = floor;
    }

    public boolean isLastFloor() {
        return isLastFloor;
    }

    public int getType(){
        return type;
    }


    public int getFloor() {
        return floor;
    }
}
