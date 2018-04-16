package sg.rt.oits;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * 外部控制板
 * Created by sg on 2018/4/12.
 */
public class OutPanelView extends RelativeLayout{


    public OutPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.panel_out,this, true);

    }


}
