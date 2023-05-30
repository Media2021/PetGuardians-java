package pets.example.guardians.configuration;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebSocketConfigTest {
    @Autowired
    private WebSocketConfig webSocketConfig;



    @Test
   void testMessageBrokerConfiguration() {
        MessageBrokerRegistry registry = Mockito.mock(MessageBrokerRegistry.class);

        webSocketConfig.configureMessageBroker(registry);

        Mockito.verify(registry).enableSimpleBroker("/topic", "/user");
        Mockito.verify(registry).setUserDestinationPrefix("/user");
    }


    @Test
     void testEndpointRegistration() {
        StompEndpointRegistry registry = Mockito.mock(StompEndpointRegistry.class);
        StompWebSocketEndpointRegistration endpointRegistration = Mockito.mock(StompWebSocketEndpointRegistration.class);

        Mockito.when(registry.addEndpoint("/ws")).thenReturn(endpointRegistration);

        webSocketConfig.registerStompEndpoints(registry);

        Mockito.verify(endpointRegistration).setAllowedOrigins("http://localhost:3000");
    }

    @Test
 void testMessageBrokerConfigurationException() {
        MessageBrokerRegistry registry = Mockito.mock(MessageBrokerRegistry.class);


        Mockito.doThrow(RuntimeException.class).when(registry).enableSimpleBroker("/topic", "/user");

        assertThrows(RuntimeException.class, () -> webSocketConfig.configureMessageBroker(registry));
    }

    @Test
    void testEndpointRegistrationException() {
        StompEndpointRegistry registry = Mockito.mock(StompEndpointRegistry.class);


        Mockito.when(registry.addEndpoint("/ws")).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> webSocketConfig.registerStompEndpoints(registry));
    }
}