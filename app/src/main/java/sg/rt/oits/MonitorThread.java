package sg.rt.oits;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import sg.rt.oits.serial.SerialPortManager;
import sg.rt.oits.serial.listener.OnSerialPortDataListener;


/**
 * 监控数据输出线程，负责采集电梯数据输出到232串口
 * Created by sg on 2018/4/18.
 */

public class MonitorThread extends Thread {

    private SerialPortManager mSerialPortManager;

    @Override
    public void run() {
        super.run();

        //1. 设置串口波特率
        int baudrate = 115200;
        //2. 注册串口监听
        mSerialPortManager = new SerialPortManager();

        EventBus.getDefault().register(this);

        mSerialPortManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onDataReceived(byte[] bytes) {
                String content = new String(bytes);
                Log.i("serial", "received" + content);

            }

            @Override
            public void onDataSent(byte[] bytes) {
                String content = new String(bytes);
                Log.i("serial", "send" + content);
            }
        });

        //4. 打开串口
        boolean openSerialPort = mSerialPortManager.openSerialPort(new File("/dev/ttySAC1")
                , baudrate);

        while(true){

            sendCurrentElevatorInfo(mSerialPortManager);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onAlertEvent(AlertEvent event){
        String alert_name = event.getAlert_name();
        switch (alert_name){
            case AlertEvent.ALERT_STOP_OUT:
                mSerialPortManager.sendBytes(clearSpace(alert_stop_out).getBytes());
                break;
            case AlertEvent.ALERT_RUSH_HEAD:
                mSerialPortManager.sendBytes(clearSpace(alert_rush_head).getBytes());
                break;
            case AlertEvent.ALERT_RUSH_BOTTOM:
                mSerialPortManager.sendBytes(clearSpace(alert_rush_bottom).getBytes());
                break;
            case AlertEvent.ALERT_RUNNING_DOOR_OPEN:
                mSerialPortManager.sendBytes(clearSpace(alert_running_door_open).getBytes());
                break;
            case AlertEvent.ALERT_OVERSPEED:
                mSerialPortManager.sendBytes(clearSpace(alert_overspeed).getBytes());
                break;
            case AlertEvent.ALERT_DOOR_RUNNING:
                mSerialPortManager.sendBytes(clearSpace(alert_door_running).getBytes());
                break;
        }
    }


    private String id = "AA AA 00 01 00 00 31 32 33 34 35 36 FF FF 08 00 00 00 3C 55 55";
    private String Online = "AA AA 00 02 00 00 01 FF FF FF FF FF FF 00 08 00 00 00 05 55 55";
    private String Offline = "AA AA 00 02 00 00 02 FF FF FF FF FF FF 00 08 00 00 00 06 55 55";
    private String Normal = "AA AA 00 03 00 00 01 FF FF FF FF FF FF 00 08 00 00 00 06 55 55";
    private String Fault = "AA AA 00 03 00 00 02 FF FF FF FF FF FF 00 08 00 00 00 07 55 55";
    private String Maintenance = "AA AA 00 03 00 00 03 FF FF FF FF FF FF 00 08 00 00 00 08 55 55";
    private String Opened = "AA AA 00 04 00 00 01 FF FF FF FF FF FF 00 08 00 00 00 07 55 55";
    private String Closed = "AA AA 00 04 00 00 02 FF FF FF FF FF FF 00 08 00 00 00 08 55 55";
    private String Opening = "AA AA 00 04 00 00 03 FF FF FF FF FF FF 00 08 00 00 00 09 55 55";
    private String Closing = "AA AA 00 04 00 00 04 FF FF FF FF FF FF 00 08 00 00 00 0A 55 55";
    private String Up = "AA AA 00 05 00 00 01 FF FF FF FF FF FF 00 08 00 00 00 08 55 55";
    private String Down = "AA AA 00 05 00 00 02 FF FF FF FF FF FF 00 08 00 00 00 09 55 55";
    private String Stop = "AA AA 00 05 00 00 03 FF FF FF FF FF FF 00 08 00 00 00 0A 55 55";
    private String power_on = "AA AA 00 06 00 00 01 FF FF FF FF FF FF 00 08 00 00 00 09 55 55";
    private String power_off = "AA AA 00 06 00 00 02 FF FF FF FF FF FF 00 08 00 00 00 0A 55 55";
    private String alert_stop_out = "AA AA 00 07 00 00 01 FF FF FF FF FF FF 00 08 00 00 00 0A 55 55";
    private String alert_rush_head = "AA AA 00 07 00 00 02 FF FF FF FF FF FF 00 08 00 00 00 0B 55 55";
    private String alert_rush_bottom = "AA AA 00 07 00 00 03 FF FF FF FF FF FF 00 08 00 00 00 0C 55 55";
    private String alert_running_door_open = "AA AA 00 07 00 00 04 FF FF FF FF FF FF 00 08 00 00 00 0D 55 55";
    private String alert_overspeed = "AA AA 00 07 00 00 05 FF FF FF FF FF FF 00 08 00 00 00 0E 55 55";
    private String alert_door_running = "AA AA 00 07 00 00 06 FF FF FF FF FF FF 00 08 00 00 00 0F 55 55";

    /**
     *采集当前电梯
     * @param mSerialPortManager
     */
    private void sendCurrentElevatorInfo(SerialPortManager mSerialPortManager) {

        Elevator elevator = Elevator.getInstance();
        //id
        mSerialPortManager.sendBytes(clearSpace(id).getBytes());
        //电梯在线状态
        if(elevator.isOnline_status()){
            mSerialPortManager.sendBytes(clearSpace(Online).getBytes());
        }else{
            mSerialPortManager.sendBytes(clearSpace(Offline).getBytes());
        }
        //电梯运行状态
        switch (elevator.getRunning_status()){
            case Elevator.NORMAL:
                mSerialPortManager.sendBytes(clearSpace(Normal).getBytes());
                break;
            case Elevator.MAINTENANCE:
                mSerialPortManager.sendBytes(clearSpace(Maintenance).getBytes());
                break;
            case Elevator.FAULT:
                mSerialPortManager.sendBytes(clearSpace(Fault).getBytes());
                break;
        }
        //电梯门状态
        switch (elevator.getDoor_open_status()){
            case Elevator.OPENED:
                mSerialPortManager.sendBytes(clearSpace(Opened).getBytes());
                break;
            case Elevator.CLOSED:
                mSerialPortManager.sendBytes(clearSpace(Closed).getBytes());
                break;
            case Elevator.CLOSING:
                mSerialPortManager.sendBytes(clearSpace(Closing).getBytes());
                break;
            case Elevator.OPENING:
                mSerialPortManager.sendBytes(clearSpace(Opening).getBytes());
                break;
        }
        //电梯方向
        switch (elevator.getDirection()){
            case Elevator.UP:
                mSerialPortManager.sendBytes(clearSpace(Up).getBytes());
                break;
            case Elevator.DOWN:
                mSerialPortManager.sendBytes(clearSpace(Down).getBytes());
                break;
            case Elevator.STOP:
                mSerialPortManager.sendBytes(clearSpace(Stop).getBytes());
                break;
        }
        //电源
        if(elevator.isPower_supply()){
            mSerialPortManager.sendBytes(clearSpace(power_on).getBytes());
        }else{
            mSerialPortManager.sendBytes(clearSpace(power_off).getBytes());
        }

    }


    private String clearSpace(String str){

        return  str.replaceAll(" +","");
    }


}
