package pl.kubaretip.cpo.api.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pl.kubaretip.cpo.api.config.AppConstants.JWT_HEADER;
import static pl.kubaretip.cpo.api.config.AppConstants.JWT_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtil jwtUtil;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JWTUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var authorizationHeaderValue = request.getHeader(JWT_HEADER);
        if (jwtUtil.isValidAuthorizationHeaderValue(authorizationHeaderValue)) {
            var token = authorizationHeaderValue.replace(JWT_PREFIX, "");
            var authentication = jwtUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
        chain.doFilter(request, response);
    }
}
