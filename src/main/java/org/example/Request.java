package org.example;
import ConnetctDatabase.ClientRequests;
import packageEnum.Status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Request {
    ArrayList<Service> listOfServices;

    private int requestId;
    private int car_id;
    private int services;
    private Status status;
    private Timestamp createdOn;
    private Timestamp completedOn;
    private boolean modifiedRequest;


    public Request(int car_id,int services) {
        this.car_id = car_id;
        this.services=services;
    }

    public Request(int requestId,int car_id, int services, Status status, Timestamp createdOn, Timestamp completedOn, boolean modifiedRequest) {
        this.requestId=requestId;
        this.car_id = car_id;
        this.services = services;
        this.status = status;
        this.createdOn = createdOn;
        this.completedOn = completedOn;
        this.modifiedRequest = modifiedRequest;
        listOfServices=new ArrayList<>();
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        if (car_id <= 0) {
            throw new IllegalArgumentException("Car ID must be positive");
        }
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

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Timestamp completedOn) {
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

    public ArrayList<Service> getListOfServices() {
        return listOfServices;
    }

    public void setListOfServices(ArrayList<Service> listOfServices) {
        this.listOfServices = listOfServices;
    }

    public void addServices(Client client, Scanner input){
        ClientRequests clientRequests=new ClientRequests();
        int choice=1;
        System.out.println("Enter choice of services: ");
        System.out.println("Enter 0 for exit");
        //преглед на всички възможни услуги в базата
        clientRequests.viewServices(client);
        while(choice!=0){
            choice= input.nextInt();
            if(choice!=0){
                //избор на услуга
                //връщаме обект услуга и я записваме в списък
                Service service=clientRequests.getService(client,choice);
                listOfServices.add(service);
            }
        }
    }














}