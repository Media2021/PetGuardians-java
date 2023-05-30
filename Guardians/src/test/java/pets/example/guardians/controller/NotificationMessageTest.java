package pets.example.guardians.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationMessageTest {

    @Test
    void testNotificationMessage() {
        String id = "123";
        String from = "John";
        String to = "Alice";
        String text = "Hello, Alice!";

        NotificationMessage message = new NotificationMessage();
        message.setId(id);
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);

        assertEquals(id, message.getId());
        assertEquals(from, message.getFrom());
        assertEquals(to, message.getTo());
        assertEquals(text, message.getText());
    }


}