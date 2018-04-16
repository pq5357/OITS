package sg.rt.oits;

/**
 * 人为操作事件
 * Created by sg on 2018/4/16.
 */

public class OperationEvent {

    private Operation operation;

    public OperationEvent(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}
