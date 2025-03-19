package org.example;

import packageEnum.UserType;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Client extends User{

    private int id;
    Scanner input=new Scanner(System.in);
    public ArrayList<Car> listOfCars;
    public ArrayList<Request> listOfRequest;

    public Client(String name, String email, String password, String phone, String address) {
        super(name, email, password, phone, address);
        listOfCars=new ArrayList<>();
        listOfRequest=new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public UserType getTypeUser() {
        return UserType.CLIENT;
    }

    public void addCar(int countOfCars){
      while(countOfCars>0){
          System.out.println("Enter car's brand: ");
          String brandCar= input.next();
          System.out.println("Enter car's model: ");
          String modelCar=input.next();
          System.out.println("Enter car's year: ");
          int year= input.nextInt();
          System.out.println("Enter register number: ");
          String rgNumber=input.next();
          Car car=new Car(brandCar,modelCar,year,rgNumber);
          listOfCars.add(car);
          countOfCars--;
      }
    }


    public void addRequest(int countRequest){
        while(countRequest>0){

        }
    }

}
