package sg.rt.oits;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by sg on 2018/4/12.
 */

public class ElevatorDoorView extends RelativeLayout {

    public static final int STATE_OPENED  = 0;
    public static final int STATE_OPENING = 2;
    public static final int STATE_CLOSING = 3;
    public static final int STATE_CLOSED = 1;

    protected int state;

    private View door_left;

    private View door_right;


    private static final int CLOSE = 1;
    private static final int OPEN = 2;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CLOSE:
                    close();
                    break;
                case OPEN:
                    open();
                    break;
            }
        }
    };

    public ElevatorDoorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.door_out, this, true);

        door_left = findViewById(R.id.door_left);
        door_right = findViewById(R.id.door_right);

        init();
    }

    private void init() {
        int door_open_status = Elevator.getInstance().getDoor_open_status();
        setState(door_open_status);
        if(door_open_status == Elevator.OPENED || door_open_status == Elevator.OPENING){
            door_left.setTranslationX(-300f);
            door_right.setTranslationX(300f);
        }
    }

    public int getState() {
        return state;
    }

    private int lastOpenFloor;

    public void setState(final int state) {
        this.state = state;
        Elevator.getInstance().setDoor_open_status(state);
        if(state == STATE_OPENED){
            lastOpenFloor = Elevator.getInstance().getCurrent_floor();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //判断是否10s之后打开的电梯还在同一层且依旧打开
                    if(lastOpenFloor == Elevator.getInstance().getCurrent_floor() && state == STATE_OPENED){
                        Message message = new Message();
                        message.what = CLOSE;
                        handler.sendMessage(message);
                    }
                }
            },10000l);
        }
    }

    public void open() {
        setState(STATE_OPENING);
        ObjectAnimator translationX = new ObjectAnimator().ofFloat(door_left, "translationX", 0,
                -300f);
        ObjectAnimator translationX1 = new ObjectAnimator().ofFloat(door_right, "translationX", 0,
                300f);
        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(translationX, translationX1); //设置动画
        animatorSet.setDuration(3000);  //设置动画时间
        animatorSet.start(); //启动
        setState(STATE_OPENED);
    }

    public void close() {
        setState(STATE_CLOSING);
        ObjectAnimator translationX = new ObjectAnimator().ofFloat(door_left, "translationX",
                -300f, 0);
        ObjectAnimator translationX1 = new ObjectAnimator().ofFloat(door_right, "translationX",
                300f, 0);
        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(translationX, translationX1); //设置动画
        animatorSet.setDuration(3000);  //设置动画时间
        animatorSet.start();
        setState(STATE_CLOSED);
    }

}
