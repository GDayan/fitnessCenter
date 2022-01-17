package by.epam.webtask.model.entity;

import java.time.LocalDateTime;

public class Order extends CustomEntity{
    Long orderId;
    LocalDateTime orderDate;
    String exercises;
    String nutrition;
    int price;
    boolean isActive;
    private long userId;

    public Order(){

    }

    public Order(Long orderId, LocalDateTime orderDate, String exercises, String nutrition, int price, boolean isActive, long userId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.exercises = exercises;
        this.nutrition = nutrition;
        this.price = price;
        this.isActive = isActive;
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = isActive;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
