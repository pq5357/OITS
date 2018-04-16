package sg.rt.oits;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

public class OutActivity extends AppCompatActivity {

    private ElevatorDoorView doorOutView;
    private AppCompatButton btn_door;
    private AppCompatButton btn_change;
    private OutPanelView panelView;
    private RadioButton rb_up;
    private RadioButton rb_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setEnterTransition(new Fade().setDuration(2000));
        getWindow().setExitTransition(new Fade().setDuration(2000));
        setContentView(R.layout.activity_out);

        EventBus.getDefault().register(this);

        doorOutView = (ElevatorDoorView) findViewById(R.id.door_out);

        panelView = (OutPanelView) findViewById(R.id.panel_out);

        btn_door = (AppCompatButton)findViewById(R.id.btn_door);

        btn_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doorOutView.getState() == ElevatorDoorView.STATE_CLOSED){
                    doorOutView.open();
                }else if(doorOutView.getState() == ElevatorDoorView.STATE_OPENED){
                    doorOutView.close();
                }else{
                }
            }
        });

        btn_change = (AppCompatButton)findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OutActivity.this, InActivity.class), ActivityOptions
                        .makeSceneTransitionAnimation(OutActivity.this).toBundle());
            }
        });

        rb_up = (RadioButton)findViewById(R.id.rb_up);
        rb_up.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OperationEvent event = new OperationEvent(Operation.OUT_UP);
                EventBus.getDefault().post(event);
            }
        });
        rb_down = (RadioButton)findViewById(R.id.rb_down);
        rb_down.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });



        Elevator instance = Elevator.getInstance();
        showStatus(instance);
    }

    /**
     * 根据电梯状态,刷新UI
     */
    private void showStatus(Elevator elevator) {
        switch (elevator.getDirection()){
            case Elevator.STOP:
                panelView.findViewById(R.id.iv_arrow).setVisibility(View.INVISIBLE);
                break;
            case Elevator.UP:
                panelView.findViewById(R.id.iv_arrow).setVisibility(View.VISIBLE);
                ((ImageView)panelView.findViewById(R.id.iv_arrow)).setImageResource(R.drawable.arrow_up_orange);
                break;
            case Elevator.DOWN:
                panelView.findViewById(R.id.iv_arrow).setVisibility(View.VISIBLE);
                ((ImageView)panelView.findViewById(R.id.iv_arrow)).setImageResource(R.drawable.arrow_down_orange);
                break;
        }

        ((TextView)panelView.findViewById(R.id.tv_floor)).setText(elevator.getCurrent_floor() +"");
        ((TextView)panelView.findViewById(R.id.tv_floor_current)).setText(elevator
                .getPeople_floor()+"/10");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRereshUI(RefreshEvent event){
        int type = event.getType();
        switch (type){
            case RefreshEvent.REFRESH:
                showStatus(Elevator.getInstance());
                break;
            case RefreshEvent.OPEN:
                showStatus(Elevator.getInstance());
                doorOutView.open();
                rb_up.setChecked(false);
                rb_down.setChecked(false);
                break;
            case RefreshEvent.CLOSE:
                break;
        }
    }





}
