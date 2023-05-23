package pets.example.guardians.services.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;


import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import pets.example.guardians.model.AccessToken;



import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import io.jsonwebtoken.impl.DefaultJwsHeader;


@SpringBootTest
 class AccessTokenEncoderDecoderImplTest {

    @Mock
    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;



    @Test
    void testDecode() {

        DefaultJwsHeader header = new DefaultJwsHeader();
        header.setAlgorithm("HS256");
        header.setType("JWT");

        Claims claims = new DefaultClaims();
        claims.setSubject("testSubject");
        claims.put("roles", List.of("ADMIN", "USER"));
        claims.put("userId", 123L);



        when(accessTokenEncoderDecoder.decode(anyString())).thenReturn(
                AccessToken.builder()
                        .subject(claims.getSubject())
                        .roles(claims.get("roles", List.class))
                        .userId(claims.get("userId", Long.class))
                        .build()
        );


        AccessToken result = accessTokenEncoderDecoder.decode("testTokenEncoded");


        assertEquals("testSubject", result.getSubject());
        assertEquals(List.of("ADMIN", "USER"), result.getRoles());
        assertEquals(123L, result.getUserId());
    }



}