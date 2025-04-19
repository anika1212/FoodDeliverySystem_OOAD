package com.foodordering.command;

public class MarkDeliveredCommand implements Command {
    private int orderId;

    public MarkDeliveredCommand(int orderId) {
        this.orderId = orderId;
    }

    public void execute() {
        System.out.println("Order " + orderId + " marked as delivered.");
        // Update DB code omitted for brevity
    }
}
