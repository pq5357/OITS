package sg.rt.oits;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * 内部楼梯板
 * Created by sg on 2018/4/13.
 */

public class InFloorBoard extends RelativeLayout {

    public InFloorBoard(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.in_floor_board, this, true);


    }

}
