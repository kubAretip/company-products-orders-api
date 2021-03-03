package pl.kubaretip.cpo.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import pl.kubaretip.cpo.api.exception.model.Error;
import pl.kubaretip.cpo.api.util.Translator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    private final Translator translator;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper, Translator translator) {
        this.objectMapper = objectMapper;
        this.translator = translator;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException ex) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        var out = response.getWriter();
        var error = Error.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .title(translator.translate("common.forbidden.title"))
                .detail(translator.translate("common.forbidden.message"))
                .build();

        var jsonErrorResponse = objectMapper.writeValueAsString(error);
        out.print(jsonErrorResponse);
        out.flush();
    }
}
