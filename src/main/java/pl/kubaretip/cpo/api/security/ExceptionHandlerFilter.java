package pl.kubaretip.cpo.api.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import pl.kubaretip.cpo.api.security.exception.InvalidLoginRequestException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The main task of this filter is handle case when user pass invalid body during authentication.
 * Authentication require json body with field login and password.
 * So when user pass incorrect json, exception will catch and passed to ControllerAdvice.
 *
 * @see pl.kubaretip.cpo.api.security.model.AuthenticationRequest
 */
@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    public ExceptionHandlerFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidLoginRequestException ex) {
            resolver.resolveException(request, response, null, ex);
        }
    }
}
