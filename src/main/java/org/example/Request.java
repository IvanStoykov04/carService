package org.example;
import packageEnum.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Request {

    Client client;
    Car car;
    ArrayList<Service> listOfServices;

    private int car_id;
    private int services;
    private Status status;
    private LocalDateTime createdOn;
    private LocalDateTime completedOn;
    private boolean modifiedRequest;


    public Request(int car_id,int services) {
        this.car_id = car_id;
        this.services=services;
        this.status = Status.PENDING;
        this.createdOn = getDateLocalTime();
        this.completedOn = null;
        this.modifiedRequest = false;
    }


    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getServices() {
        return services;
    }

    public void setServices(int services) {
        this.services = services;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDateTime completedOn) {
        this.completedOn = completedOn;
    }

    public boolean isModifiedRequest() {
        return modifiedRequest;
    }

    public void setModifiedRequest(boolean modifiedRequest) {
        this.modifiedRequest = modifiedRequest;
    }


    public LocalDateTime getDateLocalTime(){
        return LocalDateTime.now();
    }


    /*
    public void addServices(Scanner input){
        int choice=1;
        System.out.println("Enter choice of services: ");
        //list of services
        while(choice!=0){
            choice= input.nextInt();
            //Service service=getServiceFromDatabase(choice)-metod който връща service обект po dadeno id(в случая това id е choice) и го записва в листа
            //listOfServices.add(service);
        }
    }
*/










}