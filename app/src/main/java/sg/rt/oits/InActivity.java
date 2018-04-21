package sg.rt.oits;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

public class InActivity extends AppCompatActivity {

    private ElevatorDoorView doorInView;
    private InPanelView panelView;
    private AppCompatRadioButton btn_one;
    private AppCompatRadioButton btn_two;
    private AppCompatRadioButton btn_three;
    private AppCompatRadioButton btn_four;
    private AppCompatRadioButton btn_five;
    private AppCompatRadioButton btn_six;
    private AppCompatRadioButton btn_seven;
    private AppCompatRadioButton btn_eight;
    private AppCompatRadioButton btn_nine;
    private AppCompatRadioButton btn_ten;
    private AppCompatRadioButton btn_close;
    private AppCompatRadioButton btn_open;

    private AppCompatButton btn_left;
    private AppCompatButton btn_center;
    private AppCompatButton btn_right;

    private Context mContext;
    private AlertPopupWindow alertPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);
        getWindow().setEnterTransition(new Fade().setDuration(2000));
        getWindow().setExitTransition(new Fade().setDuration(2000));
        setContentView(R.layout.activity_in);

        doorInView = (ElevatorDoorView) findViewById(R.id.door_in);
        panelView = (InPanelView) findViewById(R.id.panel_in);

        btn_left = (AppCompatButton)findViewById(R.id.btn_left);
        btn_center = (AppCompatButton)findViewById(R.id.btn_center);
        btn_right = (AppCompatButton)findViewById(R.id.btn_right);


        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertPopupWindow = new AlertPopupWindow(mContext);
            }
        });

        btn_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InActivity.this, OutActivity.class), ActivityOptions
                        .makeSceneTransitionAnimation(InActivity.this).toBundle());
            }
        });


        addClickListener();

        showStatus();

    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(alertPopupWindow != null){
            alertPopupWindow.dismiss();
        }
        EventBus.getDefault().unregister(this);

    }

    private void showStatus() {
        Elevator elevator = Elevator.getInstance();
        switch (elevator.getDirection()) {
            case Elevator.STOP:
                panelView.findViewById(R.id.iv_arrow).setVisibility(View.INVISIBLE);
                break;
            case Elevator.UP:
                panelView.findViewById(R.id.iv_arrow).setVisibility(View.VISIBLE);
                ((ImageView) panelView.findViewById(R.id.iv_arrow)).setImageResource(R.drawable
                        .arrow_up_orange);
                break;
            case Elevator.DOWN:
                panelView.findViewById(R.id.iv_arrow).setVisibility(View.VISIBLE);
                ((ImageView) panelView.findViewById(R.id.iv_arrow)).setImageResource(R.drawable
                        .arrow_down_orange);
                break;
        }

        ((TextView) panelView.findViewById(R.id.tv_floor)).setText(elevator.getCurrent_floor() + "");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent event) {
        int type = event.getType();
        switch (type) {
            case RefreshEvent.REFRESH:
                showStatus();
                if(event.isLastFloor()){
                    panelView.findViewById(R.id.iv_arrow).setVisibility(View.INVISIBLE);
                    resetAllButton();
                }
                if(event.getFloor() != 0){
                    resetFloorBtn(event.getFloor());
                }
                break;
            case RefreshEvent.OPEN:
                showStatus();
                doorInView.open();
                if(event.isLastFloor()){
                    panelView.findViewById(R.id.iv_arrow).setVisibility(View.INVISIBLE);
                }
                if(event.getFloor() != 0){
                    resetFloorBtn(event.getFloor());
                }
                break;
            case RefreshEvent.CLOSE:
                showStatus();
                if(event.isLastFloor()){
                    panelView.findViewById(R.id.iv_arrow).setVisibility(View.INVISIBLE);
                }
                doorInView.close();
                if(event.getFloor() != 0){
                    resetFloorBtn(event.getFloor());
                }
                break;

        }
    }

    private void resetAllButton() {
        btn_one.setChecked(false);
        btn_two.setChecked(false);
        btn_three.setChecked(false);
        btn_four.setChecked(false);
        btn_five.setChecked(false);
        btn_six.setChecked(false);
        btn_seven.setChecked(false);
        btn_eight.setChecked(false);
        btn_nine.setChecked(false);
        btn_ten.setChecked(false);
    }

    /**
     * 重置楼层按钮状态
     */
    private void resetFloorBtn(int floor) {

        switch (floor){
            case -2:
                btn_open.setChecked(false);
                break;
            case -1:
                btn_close.setChecked(false);
                break;
            case 1:
                btn_one.setChecked(false);
                break;
            case 2:
                btn_two.setChecked(false);
                break;
            case 3:
                btn_three.setChecked(false);
                break;
            case 4:
                btn_four.setChecked(false);
                break;
            case 5:
                btn_five.setChecked(false);
                break;
            case 6:
                btn_six.setChecked(false);
                break;
            case 7:
                btn_seven.setChecked(false);
                break;
            case 8:
                btn_eight.setChecked(false);
                break;
            case 9:
                btn_nine.setChecked(false);
                break;
            case 10:
                btn_ten.setChecked(false);
                break;
        }
    }

    /**
     * 给控制面板上按钮添加点击监听
     */
    private void addClickListener() {

        btn_one = (AppCompatRadioButton) panelView.findViewById(R.id.btn_one);
        btn_one.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR.setFloor(1)));
                        }
                    }
                });
        btn_two = (AppCompatRadioButton) panelView.findViewById(R.id.btn_two);
        btn_two.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(2)));
                        }
                    }
                });
        btn_three = (AppCompatRadioButton) panelView.findViewById(R.id.btn_three);
        btn_three.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(3)));
                        }
                    }
                });
        btn_four = (AppCompatRadioButton) panelView.findViewById(R.id
                .btn_four);
        btn_four.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(4)));
                        }
                    }
                });
        btn_five = (AppCompatRadioButton) panelView.findViewById(R.id
                .btn_five);
        btn_five.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(5)));
                        }
                    }
                });
        btn_six = (AppCompatRadioButton) panelView.findViewById(R.id.btn_six);
        btn_six.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(6)));
                        }
                    }
                });
        btn_seven = (AppCompatRadioButton) panelView.findViewById(R.id
                .btn_seven);
        btn_seven.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(7)));
                        }
                    }
                });
        btn_eight = (AppCompatRadioButton) panelView.findViewById(R.id
                .btn_eight);
        btn_eight.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(8)));
                        }
                    }
                });
        btn_nine = (AppCompatRadioButton) panelView.findViewById(R.id
                .btn_nine);
        btn_nine.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(9)));
                        }
                    }
                });
        btn_ten = (AppCompatRadioButton) panelView.findViewById(R.id.btn_ten);
        btn_ten.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(10)));
                        }
                    }
                });
        btn_open = (AppCompatRadioButton) panelView.findViewById(R.id.btn_open);
        btn_open.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_OPEN));
                        }
                    }
                });

        btn_close = (AppCompatRadioButton) panelView.findViewById(R.id.btn_close);
        btn_close.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_CLOSE));
                        }
                    }
                });

    }
}
