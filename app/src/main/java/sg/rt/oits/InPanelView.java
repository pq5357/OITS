package sg.rt.oits;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by sg on 2018/4/13.
 */

public class InPanelView extends RelativeLayout{

    public InPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.panel_in,this,true);



    }
}
