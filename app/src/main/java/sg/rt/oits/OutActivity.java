package sg.rt.oits;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.transition.Fade;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class OutActivity extends AppCompatActivity {

    private ElevatorDoorOutView doorOutView;
    private OutPanelView panelView;
    private RadioButton rb_up;
    private RadioButton rb_down;
    private AppCompatButton btn_left;
    private AppCompatButton btn_center;
    private AppCompatButton btn_right;

    private Context mContext;
    private AlertPopupWindow alertPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setEnterTransition(new Fade().setDuration(2000));
        getWindow().setExitTransition(new Fade().setDuration(2000));
        setContentView(R.layout.activity_out);


        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        windowMgr.getDefaultDisplay().getMetrics(dm);
        // 获取高度
        int height = dm.heightPixels;
        // 获取宽度
        int width = dm.widthPixels;

        Log.i("OTIS", height +"++++++++++++++" + width);

        doorOutView = (ElevatorDoorOutView) findViewById(R.id.door_out);
        panelView = (OutPanelView) findViewById(R.id.panel_out);
        rb_up = (RadioButton)findViewById(R.id.rb_up);
        rb_down = (RadioButton)findViewById(R.id.rb_down);
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
                startActivity(new Intent(OutActivity.this, InActivity.class), ActivityOptions
                        .makeSceneTransitionAnimation(OutActivity.this).toBundle());
            }
        });

        rb_up.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    OperationEvent event = new OperationEvent(Operation.OUT_UP);
                    EventBus.getDefault().post(event);
                }
            }
        });

        rb_down.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    OperationEvent event = new OperationEvent(Operation.OUT_DOWN);
                    EventBus.getDefault().post(event);
                }
            }
        });

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

    /**
     * 根据电梯状态,刷新UI
     */
    private void showStatus() {
        Elevator elevator = Elevator.getInstance();
        /**
         * 上下行按钮显示
         */
        int people_floor = elevator.getPeople_floor();
        if(people_floor == 1){
            rb_up.setVisibility(View.VISIBLE);
            rb_down.setVisibility(View.INVISIBLE);
        }else if(people_floor == 10){
            rb_up.setVisibility(View.INVISIBLE);
            rb_down.setVisibility(View.VISIBLE);
        }else{
            rb_up.setVisibility(View.VISIBLE);
            rb_down.setVisibility(View.VISIBLE);
        }
        /**
         * 电梯上下行方向指示
         */
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
        /**
         * 电梯楼层显示
         */
        ((TextView)panelView.findViewById(R.id.tv_floor)).setText(elevator.getCurrent_floor() +"");
        ((TextView)panelView.findViewById(R.id.tv_floor_current)).setText(elevator
                .getPeople_floor()+"/10");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRereshUI(RefreshEvent event){
        int type = event.getType();
        switch (type){
            case RefreshEvent.REFRESH:
                showStatus();
                break;
            case RefreshEvent.OPEN:
                showStatus();
                Elevator elevator = Elevator.getInstance();
                int door_open_status = elevator.getDoor_open_status();
                if(door_open_status == Elevator.CLOSED){
                    doorOutView.open();
                }
                rb_up.setChecked(false);
                rb_down.setChecked(false);
                break;
            case RefreshEvent.CLOSE:
                showStatus();
                Elevator elevator1 = Elevator.getInstance();
                int door_open_status1 = elevator1.getDoor_open_status();
                if(door_open_status1 == Elevator.OPENED){
                    doorOutView.close();
                }
                break;
        }
    }




}
