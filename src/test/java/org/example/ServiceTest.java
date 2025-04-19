package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    @Test
    public void testServiceCreation() {
        Service s = new Service(1, "Polishing", "Exterior polish", 100.0);

        assertEquals(1, s.getId());
        assertEquals("Polishing", s.getName());
        assertEquals("Exterior polish", s.getDescription());
        assertEquals(100.0, s.getPrice());
    }
}