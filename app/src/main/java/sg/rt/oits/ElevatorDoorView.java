package sg.rt.oits;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by sg on 2018/4/12.
 */

public class ElevatorDoorView extends DoorView {

    private View door_left;

    private View door_right;

    public ElevatorDoorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.door_out, this, true);

        door_left = findViewById(R.id.door_left);
        door_right = findViewById(R.id.door_right);

        init();
    }

    private void init() {
        setState(STATE_CLOSED);
    }

    @Override
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

    @Override
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
