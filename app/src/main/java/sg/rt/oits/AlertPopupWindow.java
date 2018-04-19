package sg.rt.oits;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sg on 2018/4/18.
 */

public class AlertPopupWindow extends PopupWindow {

    public AlertPopupWindow(Context context) {
        super(context);

        final View contentView= LayoutInflater.from(context).inflate(R.layout.pop_window_alert, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        final PopupWindow window=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#36000000")));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = contentView.findViewById(R.id.ll_content).getTop();
                int left = contentView.findViewById(R.id.ll_content).getLeft();
                int right = contentView.findViewById(R.id.ll_content).getRight();
                int bottom = contentView.findViewById(R.id.ll_content).getBottom();
                int y=(int) event.getY();
                int x=(int) event.getX();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if( y > height || x < left || x > right ||  y < bottom){
                        window.dismiss();
                    }
                }
                return true;
            }
        });

        addClickListener(contentView);

        View parent = ((ViewGroup) (((Activity) context).findViewById(android.R.id.content)))
                .getChildAt(0);

        window.showAtLocation(parent, Gravity.CENTER, 0, 0);

    }

    /**
     * 添加监听器
     * @param contentView
     */
    private void addClickListener(View contentView) {


        contentView.findViewById(R.id.btn_alert_stop_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AlertEvent(AlertEvent.ALERT_STOP_OUT));
            }
        });

        contentView.findViewById(R.id.btn_alert_rush_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AlertEvent(AlertEvent.ALERT_RUSH_HEAD));
            }
        });
        contentView.findViewById(R.id.btn_alert_rush_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AlertEvent(AlertEvent.ALERT_RUSH_BOTTOM));
            }
        });
        contentView.findViewById(R.id.btn_alert_running_door_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AlertEvent(AlertEvent.ALERT_RUNNING_DOOR_OPEN));
            }
        });
        contentView.findViewById(R.id.btn_alert_overspeed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AlertEvent(AlertEvent.ALERT_OVERSPEED));
            }
        });
        contentView.findViewById(R.id.btn_alert_door_running).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AlertEvent(AlertEvent.ALERT_DOOR_RUNNING));
            }
        });




    }


}
