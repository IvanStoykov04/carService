package org.example;
import packageEnum.Status;

import java.time.LocalDateTime;

public class Request {

    private int userId;
    private int car_id;
    private Status status;
    private LocalDateTime createdOn;
    private LocalDateTime completedOn;
    private boolean modifiedRequest;

    public Request(){
        this.createdOn= LocalDateTime.now();
    }

    public Request(int userId, int car_id, Status status, LocalDateTime createdOn) {
        this.userId = userId;
        this.car_id = car_id;
        this.status = Status.PENDING;
        this.createdOn = getDateLocalTime();
        this.completedOn = null;
        this.modifiedRequest = false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
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
*/



}