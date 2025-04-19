package org.example;

import org.junit.jupiter.api.Test;
import packageEnum.Status;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    @Test
    public void testAddServiceToRequest() {
        Request request = new Request(1, 1, 3, Status.PENDING, Timestamp.valueOf("2025-04-19 16:47:47"), Timestamp.valueOf("2025-04-19 16:47:76"),false);
        Service service1 = new Service(101, "Paint", "Red paint", 150.0);
        Service service2 = new Service(102, "Wheels", "Alloy wheels", 500.0);

        ArrayList<Service> services = new ArrayList<>();
        services.add(service1);
        services.add(service2);

        request.setListOfServices(services);

        assertEquals(2, request.getListOfServices().size());
        assertEquals("Paint", request.getListOfServices().get(0).getName());
    }

    @Test
    public void testSetValidCarId() {
        Request r = new Request(1, 1);
        assertDoesNotThrow(() -> r.setCar_id(5));
    }

    @Test
    public void testSetInvalidCarIdThrows() {
        Request r = new Request(1, 1);
        Exception e = assertThrows(IllegalArgumentException.class, () -> r.setCar_id(-1));
        assertEquals("Car ID must be positive", e.getMessage());
    }
}