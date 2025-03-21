package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Car {


    private String brand;
    private String model;
    private int year;
    private String rgNumber;

   // ArrayList<Service> listOfService;

    public Car(String brand, String model, int year, String rgNumber) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.rgNumber = rgNumber;
       // listOfService=new ArrayList<>();
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

/*
    public ArrayList<Integer> chooseServicesForCar(Scanner input){
        ArrayList<Integer> choicesServices=new ArrayList<>();
        //CustomerRequest for all services and choose from them
        System.out.println("Enter all services which you want and enter 0 for end: ");
        System.out.println("Choice: ");
        int choice=1;
        while(choice!=0){
            choice=input.nextInt();
            choicesServices.add(choice);
        }
        return choicesServices;
    }


    public void addServices(ArrayList<Integer> choicesServices){
        for (Integer services: choicesServices) {
            //CustomerRequest for create and return Service object for every service
            //този метод ще има параметър индекс на масива и ще създава по него обект ако ли не връща грешка
            //tuk na мястото на вътрешната част на листа трябва да е върнатия обект а не хардкоднат като сега (само за пример)
            Service service=new Service("a","ew",120);
            listOfService.add(service);
        }
    }
    */


}
