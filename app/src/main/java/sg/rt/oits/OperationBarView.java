package sg.rt.oits;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * Created by sg on 2018/4/13.
 */

public class OperationBarView extends RelativeLayout{

    public OperationBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.operation_bar, this, true);




    }




}
