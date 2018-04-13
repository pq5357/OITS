package sg.rt.oits;

/**
 * Created by sg on 2018/4/13.
 */

public abstract class Elevator {

    /**
     * 电梯识别码,固定6个字符，唯一
     */
    protected String elevator_id;
    /**
     * 是否在线
     */
    protected boolean online_status;

    public static final int NORMAL = 1;
    public static final int FAULT = 2;
    public static final int MAINTENANCE = 3;
    /**
     * 运行状态
     */
    protected int running_status;
    /**
     * 当前楼层
     */
    protected int current_floor;

    public static final int OPENED = 0;
    public static final int CLOSEF = 1;
    public static final int OPENING = 2;
    public static final int CLOSING = 3;
    /**
     * 电梯门开关状态
     */
    protected int door_open_status;


    public static final int UP = 1;
    public static final int DOWN = -1;
    public static final int STOP = 0;
    /**
     * 电梯运行方向
     */
    protected int direction;
    /**
     * 电梯是否供电
     */
    protected  boolean power_supply;


    public static long collect_info_interval = 1000l;

}
