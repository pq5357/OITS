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

public class OutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        final ElevatorDoorView doorOutView = (ElevatorDoorView) findViewById(R.id.door_out);

        AppCompatButton btn_door = (AppCompatButton)findViewById(R.id.btn_door);

        btn_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(doorOutView.getState() == DoorView.STATE_CLOSED){
                    doorOutView.open();
                }else if(doorOutView.getState() == DoorView.STATE_OPENED){
                    doorOutView.close();
                }else{
                }
            }
        });

        AppCompatButton btn_change = (AppCompatButton)findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OutActivity.this, InActivity.class), ActivityOptions
                        .makeSceneTransitionAnimation(OutActivity.this).toBundle());
            }
        });



    }
}
