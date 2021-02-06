package pl.kubaretip.cpo.api.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.kubaretip.cpo.api.security.exception.InvalidLoginRequestException;
import pl.kubaretip.cpo.api.security.exception.UserNotActivatedException;
import pl.kubaretip.cpo.api.security.model.AuthenticationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public JWTAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        AuthenticationRequest authenticationRequest;

        try {
            authenticationRequest = objectMapper.readValue(request.getInputStream(), AuthenticationRequest.class);
        } catch (IOException e) {
            throw new InvalidLoginRequestException();
        }

        try {
            log.debug("Attempt authentication user: " + authenticationRequest.getLogin());
            var authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(), authenticationRequest.getPassword());
            return this.getAuthenticationManager().authenticate(authentication);
        } catch (DisabledException ex) {
            throw new UserNotActivatedException(ex.getMessage(), authenticationRequest.getLogin());
        }

    }
}
