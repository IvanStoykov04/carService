package org.example;
import packageEnum.Status;

import java.time.LocalDateTime;

public class Request {
    private Integer requestId;
    private Client client;
    private Status status;
    private Car car;
    private LocalDateTime createdOn;
    private LocalDateTime completedOn;
    private boolean modifiedRequest;

    public Request(){
        this.createdOn= LocalDateTime.now();
    }
    public Request(Integer requestId, Client client, Status status, Car car, LocalDateTime createdOn, LocalDateTime completedOn, boolean modifiedRequest) {
        this.requestId = requestId;
        this.client = client;
        this.status = status;
        this.car = car;
        this.createdOn = createdOn;
        this.completedOn = completedOn;
        this.modifiedRequest = modifiedRequest;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", client=" + client +
                ", status=" + status +
                ", car=" + car +
                ", createdOn=" + createdOn +
                ", completedOn=" + completedOn +
                ", modifiedRequest=" + modifiedRequest +
                '}';
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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
}