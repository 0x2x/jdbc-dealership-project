package org.dealership.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dealership.Main;
import org.dealership.models.Vehicle;

public class VehiclesDAO {


    // get all vehicles
    public static ArrayList<String> allVehicles() {
        ArrayList<String> allItems = new ArrayList<>();
        Object returnData  = Main.Database.Execute("SELECT * FROM Vehicles");
        System.out.println(returnData);
        return allItems;
    }



    // add new Vehicle
    public static void addVehicle(Vehicle vehicle) {
        try{
            Main.Database.Execute("INSERT INTO Vehicles(vin, year, make, model, type, color, odometer, price) VALUES (?,?,?,?,?,?,?,?)",
                    vehicle.getVin(),
                    vehicle.getYear(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getType(),
                    vehicle.getColor(),
                    vehicle.getOdometer(),
                    vehicle.getPrice());
        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    // new vehicle with dealership
    public static void addVehicle(Vehicle vehicle, int dealerShipId) {
        try{
            Main.Database.Execute("INSERT INTO Vehicles(vin, year, make, model, type, color, odometer, price, DealershipID) VALUES (?,?,?,?,?,?,?,?,?)",
                    vehicle.getVin(),
                    vehicle.getYear(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getType(),
                    vehicle.getColor(),
                    vehicle.getOdometer(),
                    vehicle.getPrice(),
                    dealerShipId);
        }catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    // search vehicle by vin
    public static Vehicle searchVin(int vin) {
        try{
            List<Map<String, Object>> vehicle = (List<Map<String, Object>>) Main.Database.Execute(
                    "SELECT vin, year, Make, Model, Type, Color, Odometer, Price, DealershipID " +
                            "FROM Vehicles " +
                            "WHERE vin = ?",
                    vin);
            if(!vehicle.isEmpty()) {
                Map<String, Object> currentVehicle = vehicle.get(0);
                return new Vehicle(currentVehicle.get("vin"), currentVehicle.get("year"), currentVehicle.get("Make"), currentVehicle.get("Model"), currentVehicle.get("Type"), currentVehicle.get("Color"), currentVehicle.get("Odometer"), currentVehicle.get("Price"), currentVehicle.get("DealershipID"));
            } else {
                return null;
            }

        }catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return null;
    }

    // remove vehicles
    public static boolean removeVehicle(Vehicle vehicle) {
        try{
            Main.Database.Execute("DELETE FROM Vehicles WHERE vin = ?", vehicle.getVin());
        }catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }

        return true;
    }

    public static boolean removeVehicle(int vin) {
        try{
            Main.Database.Execute("DELETE FROM Vehicles WHERE vin = ?", vin);
        }catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
        return true;
    }



}
