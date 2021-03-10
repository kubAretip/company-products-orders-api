package pl.kubaretip.cpo.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.kubaretip.cpo.api.security.jwt.JWTUtil;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@TestConfiguration
public class JWTUtilTestConfig {

    @Bean
    public JWTUtil jwtUtil() {

        var jwtUtil = mock(JWTUtil.class);

        given(jwtUtil.buildToken(any())).willReturn(null);
        given(jwtUtil.getAuthentication(any())).willReturn(null);
        given(jwtUtil.isValidAuthorizationHeaderValue(any())).willReturn(false);

        return jwtUtil;
    }


}
