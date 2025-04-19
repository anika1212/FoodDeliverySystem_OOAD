package com.foodordering.observer;

import java.util.*;

public class OrderStatusSubject {
    private List<Observer> observers = new ArrayList<>();
    private String status;

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void setStatus(String status) {
        this.status = status;
        notifyAllObservers();
    }

    public void notifyAllObservers() {
        for (Observer o : observers) {
            o.update(status);
        }
    }
}
