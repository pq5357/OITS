package sg.rt.oits;

/**
 * Created by sg on 2018/4/16.
 */

public class RefreshEvent {

    /**
     * 定义view操作
     */
    public static final int OPEN = 1;
    public static final int CLOSE = 2;
    public static final int REFRESH = 3;

    /**
     * 刷新操作类型
     */
    private int type;

    public RefreshEvent(int type) {
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
