package pets.example.guardians.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationMessageTest {


    @Test
    void testEmptyNotificationMessage() {
        NotificationMessage message = new NotificationMessage();

        assertNull(message.getId());
        assertNull(message.getFrom());
        assertNull(message.getTo());
        assertNull(message.getText());
    }

    @Test
    void testNotificationMessageWithNullFields() {
        String id = null;
        String from = null;
        String to = null;
        String text = null;

        NotificationMessage message = new NotificationMessage();
        message.setId(id);
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);

        assertNull(message.getId());
        assertNull(message.getFrom());
        assertNull(message.getTo());
        assertNull(message.getText());
    }

    @Test
    void testNotificationMessageEqualityWithDifferentInstances() {
        String id = "123";
        String from = "John";
        String to = "Alice";
        String text = "Hello, Alice!";

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

        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());
        assertNotSame(message1, message2);
    }
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
    @Test
    void testNotificationMessageWithEmptyFields() {
        String id = "";
        String from = "";
        String to = "";
        String text = "";

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
    void testNotificationMessageEqualityWithDifferentFields() {
        String id1 = "123";
        String from1 = "John";
        String to1 = "Alice";
        String text1 = "Hello, Alice!";

        String id2 = "456";
        String from2 = "Jane";
        String to2 = "Bob";
        String text2 = "Hi, Bob!";

        NotificationMessage message1 = new NotificationMessage();
        message1.setId(id1);
        message1.setFrom(from1);
        message1.setTo(to1);
        message1.setText(text1);

        NotificationMessage message2 = new NotificationMessage();
        message2.setId(id2);
        message2.setFrom(from2);
        message2.setTo(to2);
        message2.setText(text2);

        assertNotEquals(message1, message2);
        assertNotEquals(message1.hashCode(), message2.hashCode());
    }
}