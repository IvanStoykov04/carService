package org.example;
import packageEnum.Status;

public class Request {
//
    private Client client;
    private Status status;
    private boolean modifiedRequest;

    public Request(Client client, Status status, boolean modifiedRequest) {
        this.client = client;
        this.status = status;
        this.modifiedRequest = modifiedRequest;
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

    public boolean isModifiedRequest() {
        return modifiedRequest;
    }

    public void setModifiedRequest(boolean modifiedRequest) {
        this.modifiedRequest = modifiedRequest;
    }


}
