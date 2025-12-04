package org.dealership;

import org.dealership.daos.VehiclesDAO;
import org.dealership.models.Vehicle;
import org.dealership.services.DataBase;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static DataBase Database = null;

    private static void startDatabase(String[] args) {
        Database = new DataBase("localhost", 3306, "dealerships", args[0], args[1]);
        Database.initalize("CREATE DATABASE IF NOT EXISTS dealerships");
        Database.initalize("""
                CREATE TABLE IF NOT EXISTS dealership(
                     id  INT auto_increment NOT NULL PRIMARY KEY,
                 	name TEXT NOT NULL,
                     phoneNumber TEXT,
                     location TEXT
                 );
                
                 CREATE TABLE IF NOT EXISTS Vehicles(
                 	 vin INT NOT NULL PRIMARY KEY,
                     year INT NOT NULL,
                     Make TEXT NOT NULL,
                     Model TEXT NOT NULL,
                     Type TEXT NOT NULL,
                     Color TEXT NOT NULL,
                     Odometer TEXT NOT NULL,
                     Price BIGINT NOT NULL,
                     DealershipID int,
                     CONSTRAINT fk_vehicles_dealership
                     FOREIGN KEY (DealershipID)
                     REFERENCES dealership(id)
                 );""");
    }

    private static void fillData() {
        //
        VehiclesDAO.addVehicle(new Vehicle(4204, 2000, "Toyota", "Cororlla", "Sedan", "Black", 23423, 8522));
        VehiclesDAO.addVehicle(new Vehicle(4204, 2000, "Toyota", "Cororlla", "Sedan", "Black", 23423, 8522));
        VehiclesDAO.addVehicle(new Vehicle(4205, 2015, "Honda", "Civic", "Sedan", "Blue", 45210, 10200));
        VehiclesDAO.addVehicle(new Vehicle(4206, 2018, "Ford", "Focus", "Hatchback", "White", 31000, 9500));
        VehiclesDAO.addVehicle(new Vehicle(4207, 2020, "Chevrolet", "Malibu", "Sedan", "Silver", 18000, 15000));
        VehiclesDAO.addVehicle(new Vehicle(4208, 2012, "Nissan", "Altima", "Sedan", "Gray", 78000, 6300));
        VehiclesDAO.addVehicle(new Vehicle(4209, 2019, "Hyundai", "Elantra", "Sedan", "Red", 25000, 11200));
        VehiclesDAO.addVehicle(new Vehicle(4210, 2017, "Kia", "Soul", "Crossover", "Green", 40000, 8700));
        VehiclesDAO.addVehicle(new Vehicle(4211, 2021, "Tesla", "Model 3", "Sedan", "White", 12000, 34000));
        VehiclesDAO.addVehicle(new Vehicle(4212, 2016, "BMW", "320i", "Sedan", "Black", 52000, 18000));
        VehiclesDAO.addVehicle(new Vehicle(4213, 2014, "Audi", "A4", "Sedan", "Blue", 69000, 16500));
        VehiclesDAO.addVehicle(new Vehicle(4214, 2013, "Volkswagen", "Jetta", "Sedan", "Silver", 83000, 7200));
        // add some to dealership


    }
    public
    static void main(String[] args) {
        startDatabase(args);
        fillData();

        VehiclesDAO.allVehicles();
        VehiclesDAO.searchVin(4204);
    }
}
