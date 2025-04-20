package ConnetctDatabase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationManagerTest {

    AuthenticationManager manager;

    @BeforeEach
    public void setUp() {
        manager = new AuthenticationManager();
    }

    @Test
    public void validEmailShouldPass() {
        assertTrue(manager.checkEmail("test@example.com"));
    }

    /*
    @Test
    public void invalidEmailShouldFail() {
        assertFalse(manager.checkEmail("userno@_domain"));
    }
*/
    @Test
    public void validPhoneShouldPass() {
        assertTrue(manager.checkPhone("0888123456"));
    }

    @Test
    public void invalidPhoneShouldFail() {
        assertFalse(manager.checkPhone("12345"));
    }

    @Test
    public void validPasswordShouldPass() {
        assertTrue(manager.checkPassword("123456"));
    }

    @Test
    public void shortPasswordShouldFail() {
        assertFalse(manager.checkPassword("123"));
    }

    @Test
    public void testValidEmailDoesNotThrow() {
        assertDoesNotThrow(() -> manager.checkEmail("test@example.com"));
    }

    @Test
    public void testInvalidEmailThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.checkEmail("invalidemail");
        });

        assertEquals("Invalid email", exception.getMessage());
    }

    @Test
    public void testNullEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> manager.checkEmail(null));
    }
}
