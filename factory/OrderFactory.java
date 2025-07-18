package com.foodordering.factory;

import com.foodordering.model.Order;

public class OrderFactory {
    public static Order createOrder(int userId, int restaurantId, String type) {
        return new Order(userId, restaurantId, type);
    }
}


