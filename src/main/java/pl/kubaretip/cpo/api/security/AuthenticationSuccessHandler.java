package pl.kubaretip.cpo.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import pl.kubaretip.cpo.api.security.model.AuthenticationSuccessResponse;
import pl.kubaretip.cpo.api.security.jwt.JWTUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public AuthenticationSuccessHandler(JWTUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        var out = response.getWriter();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        var token = jwtUtil.buildToken(authentication);
        var tokenJson = objectMapper.writeValueAsString(new AuthenticationSuccessResponse(token));
        out.print(tokenJson);
        out.flush();
    }


}
