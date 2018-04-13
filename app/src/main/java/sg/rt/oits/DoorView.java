package sg.rt.oits;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by sg on 2018/4/12.
 */

public abstract class DoorView extends RelativeLayout{

    public static final int STATE_OPENED  = 0;
    public static final int STATE_OPENING = 1;
    public static final int STATE_CLOSING = 2;
    public static final int STATE_CLOSED = 3;

    protected int state;

    public DoorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void open();

    public abstract void close();

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
