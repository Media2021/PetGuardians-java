package pets.example.guardians.configuration.auth;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import pets.example.guardians.services.AccessTokenDecoder;
import pets.example.guardians.services.exception.InvalidAccessTokenException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthenticationRequestFilterTest {
    @Mock
    private AccessTokenDecoder accessTokenDecoder;

    @Mock
    private FilterChain filterChain;
    @InjectMocks
    private AuthenticationRequestFilter authenticationRequestFilter;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationRequestFilter.setAccessTokenDecoder(accessTokenDecoder);
    }


    @Test
    void doFilterInternal_InvalidAccessToken_SendsUnauthorizedResponse() throws ServletException, IOException, InvalidAccessTokenException {
        MockitoAnnotations.openMocks(this);
        authenticationRequestFilter = new AuthenticationRequestFilter();
        authenticationRequestFilter.setAccessTokenDecoder(accessTokenDecoder);

        when(accessTokenDecoder.decode(anyString())).thenThrow(new InvalidAccessTokenException("Invalid token"));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid-access-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        authenticationRequestFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        verify(filterChain, never()).doFilter(request, response);
    }


}