package pets.example.guardians.services.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;


class InvalidAccessTokenExceptionTest {
    @Test
     void constructor_ErrorCause_ReturnsInvalidAccessTokenException() {
        String errorCause = "Invalid access token";
        InvalidAccessTokenException exception = new InvalidAccessTokenException(errorCause);

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals(errorCause, exception.getReason());
    }
    @Test
    void testSuperConstructor() {
        String errorCause = "Invalid access token";

        InvalidAccessTokenException exception = new InvalidAccessTokenException(errorCause);

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals(errorCause, exception.getReason());

        ResponseStatusException superException = (ResponseStatusException) exception;
        assertEquals(HttpStatus.UNAUTHORIZED, superException.getStatus());
        assertEquals(errorCause, superException.getReason());
    }
}