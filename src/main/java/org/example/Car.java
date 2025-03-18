package org.example;

public class Car {

    private String brand;
    private String model;
    private int year;
    private String rgNumber;

    public Car(String brand, String model, int year, String rgNumber) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.rgNumber = rgNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRgNumber() {
        return rgNumber;
    }

    public void setRgNumber(String rgNumber) {
        this.rgNumber = rgNumber;
    }



}
