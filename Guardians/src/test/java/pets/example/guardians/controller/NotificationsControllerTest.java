package pets.example.guardians.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
class NotificationsControllerTest {

    @Test
    void testSendNotificationToUsers() {

        SimpMessagingTemplate messagingTemplate = Mockito.mock(SimpMessagingTemplate.class);


        NotificationsController notificationsController = new NotificationsController(messagingTemplate);


        NotificationMessage message = new NotificationMessage();


        ResponseEntity<Void> response = notificationsController.sendNotificationToUsers(message);


        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
        verify(messagingTemplate).convertAndSend(destinationCaptor.capture(), payloadCaptor.capture());

        assertEquals("/topic/public-messages", destinationCaptor.getValue());
        assertEquals(message, payloadCaptor.getValue());


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}