package sg.rt.oits;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by sg on 2018/4/13.
 */

public class InStationBoardView extends LinearLayout {

    public InStationBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.in_station_broad, this, true);

    }
}
