package pets.example.guardians.configuration.exceptionhandler;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;


import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class RestCustomExceptionHandlerTest {

    private final RestCustomExceptionHandler exceptionHandler = new RestCustomExceptionHandler();

    @Test
    void handleConstraintViolationException_ReturnsBadRequestResponse() {
        ConstraintViolationException exception = mock(ConstraintViolationException.class);

        ResponseEntity<Object> response = exceptionHandler.handleConstraintViolationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleResponseStatusException_ReturnsResponseStatusExceptionResponse() {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ResponseStatusException exception = new ResponseStatusException(status, "Invalid request");

        ResponseEntity<Object> response = exceptionHandler.handleResponseStatusException(exception);

        assertEquals(status, response.getStatusCode());
        assertEquals(exception.getReason(), response.getBody());
    }


    @Test
    void handleUnknownRuntimeError_ReturnsInternalServerErrorResponse() {
        RuntimeException exception = mock(RuntimeException.class);

        ResponseEntity<Object> response = exceptionHandler.handleUnknownRuntimeError(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error.", response.getBody());
    }

    @Test
    void handleAccessDeniedException_ReturnsForbiddenResponse() {
        AccessDeniedException exception = mock(AccessDeniedException.class);

        ResponseEntity<Object> response = exceptionHandler.handleConstraintViolationException(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    // Add more test cases for other exception handlers if needed

}