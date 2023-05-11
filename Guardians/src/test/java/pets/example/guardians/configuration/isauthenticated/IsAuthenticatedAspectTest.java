package pets.example.guardians.configuration.isauthenticated;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IsAuthenticatedAspectTest {
    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private IsAuthenticatedAspect isAuthenticatedAspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        isAuthenticatedAspect = new IsAuthenticatedAspect();
    }

    @Test
    void interceptMethod_ValidAuthentication_NoExceptionThrown() throws Throwable {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        Object result = new Object();
        when(proceedingJoinPoint.proceed()).thenReturn(result);

        Object interceptedResult = isAuthenticatedAspect.interceptMethod(proceedingJoinPoint);

        assertEquals(result, interceptedResult);
        verify(proceedingJoinPoint, times(1)).proceed();
        verify(securityContext, times(1)).getAuthentication();
    }

    @Test
    void interceptMethod_NullSecurityContext_ThrowsUnauthorizedException() throws Throwable {
        SecurityContextHolder.clearContext();

        assertThrows(ResponseStatusException.class, () -> isAuthenticatedAspect.interceptMethod(proceedingJoinPoint));

        verify(proceedingJoinPoint, never()).proceed();
    }

    @Test
    void interceptMethod_NullAuthentication_ThrowsUnauthorizedException() throws Throwable {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> isAuthenticatedAspect.interceptMethod(proceedingJoinPoint));

        verify(proceedingJoinPoint, never()).proceed();
    }

    @Test
    void interceptMethod_AnonymousAuthentication_ThrowsUnauthorizedException() throws Throwable {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(
                new AnonymousAuthenticationToken("key", "anonymous",
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));

        assertThrows(ResponseStatusException.class, () -> isAuthenticatedAspect.interceptMethod(proceedingJoinPoint));

        verify(proceedingJoinPoint, never()).proceed();
    }
}