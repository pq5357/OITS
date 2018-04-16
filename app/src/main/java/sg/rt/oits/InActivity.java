package sg.rt.oits;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setEnterTransition(new Fade().setDuration(2000));
        getWindow().setExitTransition(new Fade().setDuration(2000));
        setContentView(R.layout.activity_in);

        EventBus.getDefault().register(this);

        doorInView = (ElevatorDoorView) findViewById(R.id.door_in);

        panelView = (InPanelView) findViewById(R.id.panel_in);

        AppCompatButton btn_door = (AppCompatButton)findViewById(R.id.btn_door);

        btn_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(doorInView.getState() == ElevatorDoorView.STATE_CLOSED){
                    doorInView.open();
                }else if(doorInView.getState() == ElevatorDoorView.STATE_OPENED){
                    doorInView.close();
                }else{

                }
            }
        });


        AppCompatButton btn_change = (AppCompatButton)findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(InActivity.this, OutActivity.class), ActivityOptions
                        .makeSceneTransitionAnimation(InActivity.this).toBundle());
            }
        });

        Elevator instance = Elevator.getInstance();
        showStatus(instance);

    }

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

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRereshEvent(RefreshEvent event){

        int type = event.getType();
        switch (type){
            case RefreshEvent.REFRESH:
                showStatus(Elevator.getInstance());
                break;
            case RefreshEvent.OPEN:
                showStatus(Elevator.getInstance());
                doorInView.open();

                break;
            case RefreshEvent.CLOSE:
                break;
        }


    }
}
