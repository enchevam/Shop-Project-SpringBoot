package com.project.ProjectShop.ProjectShop.constants;

public enum OrderStatus { //статуси са: нова, обработва се, изпратена по куриер, завършена

    NEW("New"),
    PROCESSED("Processed"),
    SEND("Send"),
    FINISHED("Finished");

    public final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return this.status;
    }
}
