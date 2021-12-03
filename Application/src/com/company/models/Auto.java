package com.company.models;

public class Auto {
    private int id;

    protected String brand;
    protected String model;
    protected String generation;

    protected int price;
    protected String transmission;
    protected String fuel;
    protected int year;
    protected float volumeE;

    public Auto( int id, String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.generation = generation;
        this.price = price;
        this.transmission = transmission;
        this.fuel = fuel;
        this.year = year;
        this.volumeE = volumeE;
    }

    public Auto(String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE) {
        this.brand = brand;
        this.model = model;
        this.generation = generation;
        this.price = price;
        this.transmission = transmission;
        this.fuel = fuel;
        this.year = year;
        this.volumeE = volumeE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getVolumeE() {
        return volumeE;
    }

    public void setVolumeE(float volumeE) {
        this.volumeE = volumeE;
    }
}
