package sg.rt.oits;

import android.app.ActivityOptions;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);
        getWindow().setEnterTransition(new Fade().setDuration(2000));
        getWindow().setExitTransition(new Fade().setDuration(2000));
        setContentView(R.layout.activity_in);

        EventBus.getDefault().register(this);

        doorInView = (ElevatorDoorView) findViewById(R.id.door_in);

        panelView = (InPanelView) findViewById(R.id.panel_in);

        AppCompatButton btn_change = (AppCompatButton) findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(InActivity.this, OutActivity.class), ActivityOptions
                        .makeSceneTransitionAnimation(InActivity.this).toBundle());
            }
        });

        addClickListener();

        Elevator instance = Elevator.getInstance();
        showStatus(instance);

    }

    private void showStatus(Elevator elevator) {
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

        ((TextView) panelView.findViewById(R.id.tv_floor)).setText(elevator.getCurrent_floor() +
                "");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent event) {

        int type = event.getType();
        switch (type) {
            case RefreshEvent.REFRESH:
                showStatus(Elevator.getInstance());
                break;
            case RefreshEvent.OPEN:
                showStatus(Elevator.getInstance());
                doorInView.open();
                resetFloorBtn();
                break;
            case RefreshEvent.CLOSE:
                showStatus(Elevator.getInstance());
                doorInView.close();
                break;
        }

    }

    /**
     * 重置楼层按钮状态
     */
    private void resetFloorBtn() {

    }

    /**
     * 给控制面板上按钮添加点击监听
     */
    private void addClickListener() {

        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_one)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR.setFloor(1)));
                        }
                    }
                });
        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_two)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(2)));
                        }
                    }
                });
        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_three)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(3)));
                        }
                    }
                });
        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_four)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(4)));
                        }
                    }
                });
        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_five)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(5)));
                        }
                    }
                });
        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_six)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(6)));
                        }
                    }
                });
        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_seven)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(7)));
                        }
                    }
                });
        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_eight)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(8)));
                        }
                    }
                });
        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_nine)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(9)));
                        }
                    }
                });
        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_ten)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_FLOOR
                                    .setFloor(10)));
                        }
                    }
                });
        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_open)).setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            EventBus.getDefault().post(new OperationEvent(Operation.IN_OPEN));
                        }
                    }
                });

        ((AppCompatRadioButton) panelView.findViewById(R.id.btn_close)).setOnCheckedChangeListener
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
