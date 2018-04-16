package sg.rt.oits;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by sg on 2018/4/13.
 */

public class Elevator{

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
    public static final int CLOSED = 1;
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

    /**
     * 乘坐人当前楼层
     */
    public int people_floor;

    /**
     * 信息采集间隔
     */
    public static long collect_info_interval = 10000l;

    private static volatile Elevator instance = null;

    public static Elevator getInstance(){
        if (instance == null) {
            synchronized (Elevator.class) {
                if (instance == null) {
                    instance = new Elevator();
                    EventBus.getDefault().register(instance);
                    instance.init();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化电梯参数，电梯位于楼层5，人员位于楼层1，stop
     */
    private void init() {
        elevator_id = "123456";
        online_status = true;
        running_status = NORMAL;
        current_floor = 5;
        door_open_status = CLOSED;
        direction = STOP;
        power_supply = true;
        people_floor = 1;
        EventBus.getDefault().post(new RefreshEvent(RefreshEvent.REFRESH));
    }

    private Elevator() {

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onOperationEvent(OperationEvent operationEvent){
        Operation operation = operationEvent.getOperation();
        EventBus.getDefault().post(new RefreshEvent(RefreshEvent.REFRESH));
        switch (operation){
            case OUT_UP:
                if(current_floor>people_floor){
                    setDirection(DOWN);
                    setDoor_open_status(CLOSED);
                    while(current_floor != people_floor){
                        try {
                            Thread.sleep(2000l);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        current_floor--;
                        EventBus.getDefault().post(new RefreshEvent(RefreshEvent.REFRESH));
                    }
                    EventBus.getDefault().post(new RefreshEvent(RefreshEvent.OPEN));
                    setDirection(STOP);
                }else if(current_floor<people_floor){

                }else{

                }
                break;
            case OUT_DOWN:
                break;
            case IN_FLOOR:
                 break;
            case IN_CLOSE:
                break;
            case IN_OPEN:
                break;
            case IN_CALL:
                break;
        }
        EventBus.getDefault().post(new RefreshEvent(RefreshEvent.REFRESH));

    }

    public String getElevator_id() {
        return elevator_id;
    }

    public boolean isOnline_status() {
        return online_status;
    }

    public int getRunning_status() {
        return running_status;
    }

    public int getCurrent_floor() {
        return current_floor;
    }

    public int getDoor_open_status() {
        return door_open_status;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isPower_supply() {
        return power_supply;
    }

    public int getPeople_floor() {
        return people_floor;
    }


    public void setElevator_id(String elevator_id) {
        this.elevator_id = elevator_id;
    }

    public void setOnline_status(boolean online_status) {
        this.online_status = online_status;
    }

    public void setRunning_status(int running_status) {
        this.running_status = running_status;
    }

    public void setCurrent_floor(int current_floor) {
        this.current_floor = current_floor;
    }

    public void setDoor_open_status(int door_open_status) {
        this.door_open_status = door_open_status;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setPower_supply(boolean power_supply) {
        this.power_supply = power_supply;
    }

    public void setPeople_floor(int people_floor) {
        this.people_floor = people_floor;
    }

    public static void setCollect_info_interval(long collect_info_interval) {
        Elevator.collect_info_interval = collect_info_interval;
    }
}
