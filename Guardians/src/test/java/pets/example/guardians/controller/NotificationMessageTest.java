package pets.example.guardians.controller;

import org.junit.jupiter.api.Assertions;
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
    @Test
    void testToString() {
        String id = "123";
        String from = "John";
        String to = "Jane";
        String text = "Hello, World!";

        NotificationMessage message = new NotificationMessage();
        message.setId(id);
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);

        String expectedToString = "NotificationMessage(id=123, from=John, to=Jane, text=Hello, World!)";

        Assertions.assertEquals(expectedToString, message.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        String id = "123";
        String from = "John";
        String to = "Jane";
        String text = "Hello, World!";

        NotificationMessage message1 = new NotificationMessage();
        message1.setId(id);
        message1.setFrom(from);
        message1.setTo(to);
        message1.setText(text);

        NotificationMessage message2 = new NotificationMessage();
        message2.setId(id);
        message2.setFrom(from);
        message2.setTo(to);
        message2.setText(text);

        Assertions.assertEquals(message1, message2);
        Assertions.assertEquals(message1.hashCode(), message2.hashCode());
    }

}