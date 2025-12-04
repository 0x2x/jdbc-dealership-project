package org.dealership.models;

import org.dealership.daos.VehiclesDAO;

public class Vehicle {
    private int Vin;
    private int Year;
    private String Make;
    private String Model;
    private String Type;
    private String Color;
    private double Odometer;
    private double Price;


    // Constructor
    public Vehicle(int vin, int year, String make, String Model, String Type, String Color, double Odometer, double Price) {
        this.Vin = vin;
        this.Year = year;
        this.Make = make;
        this.Model = Model;
        this.Type = Type;
        this.Color = Color;
        this.Odometer = Odometer;
        this.Price = Price;
    }

    public Vehicle(Object vin, Object year, Object make, Object model, Object type, Object color, Object odometer, Object price, Object dealershipID) {
    }


    // Getters


    public int getVin() {
        return Vin;
    }

    public int getYear() {
        return Year;
    }

    public String getMake() {
        return Make;
    }

    public String getModel() {
        return Model;
    }

    public String getType() {
        return Type;
    }

    public String getColor() {
        return Color;
    }

    public double getOdometer() {
        return Odometer;
    }

    public double getPrice() {
        return Price;
    }

    // toString

    @Override
    public String toString() {
        return "Vehicle{" +
                "vin=" + Vin +
                ", year=" + Year +
                ", Make='" + Make + '\'' +
                ", Model='" + Model + '\'' +
                ", Type='" + Type + '\'' +
                ", Color='" + Color + '\'' +
                ", odometer=" + Odometer +
                ", price=" + Price +
                '}';
    }
}


