package com.example.iot_msiia;


public class Solution {
    private String name;
    private int roomNumber;
    private boolean isActive;
    private boolean isChecked;

    public Solution(String name, int roomNumber, boolean isActive, boolean isChecked) {
        this.name = name;
        this.roomNumber = roomNumber;
        this.isActive = isActive;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
