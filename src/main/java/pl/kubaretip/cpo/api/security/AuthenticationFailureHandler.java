package pl.kubaretip.cpo.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import pl.kubaretip.cpo.api.exception.model.Error;
import pl.kubaretip.cpo.api.security.exception.UserNotActivatedException;
import pl.kubaretip.cpo.api.util.Translator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final Translator translator;

    public AuthenticationFailureHandler(ObjectMapper objectMapper,
                                        Translator translator) {
        this.objectMapper = objectMapper;
        this.translator = translator;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        var out = response.getWriter();
        var errorBuilder = Error.builder()
                .status(HttpStatus.UNAUTHORIZED.value());

        if (exception instanceof UserNotActivatedException) {
            var userNotActivatedException = (UserNotActivatedException) exception;
            errorBuilder
                    .title(translator.translate("exception.user.notActivated.title"))
                    .detail(translator.translate("exception.user.notActivated.message",
                            new Object[]{userNotActivatedException.getInactiveUser()}));
        }

        if (exception instanceof BadCredentialsException) {
            errorBuilder
                    .title(translator.translate("security.badCredentials.title"))
                    .detail(translator.translate("security.badCredentials.message"));
        }

        var jsonErrorResponse = objectMapper.writeValueAsString(errorBuilder.build());
        out.print(jsonErrorResponse);
        out.flush();
    }


}
