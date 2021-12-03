package com.company.models;

public class Cart extends Auto{
    public Cart(String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE) {
        super(brand, model, generation, price, transmission, fuel, year, volumeE);
    }

    public Integer getTotalPrice(){
        return getPrice();
    }
}
