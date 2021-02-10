package pl.kubaretip.cpo.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kubaretip.cpo.api.security.AuthenticationFailureHandler;
import pl.kubaretip.cpo.api.security.AuthenticationSuccessHandler;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.security.ExceptionHandlerFilter;
import pl.kubaretip.cpo.api.security.jwt.JWTAuthenticationFilter;
import pl.kubaretip.cpo.api.security.jwt.JWTAuthorizationFilter;
import pl.kubaretip.cpo.api.security.jwt.JWTUtil;
import pl.kubaretip.cpo.api.util.Translator;

import static pl.kubaretip.cpo.api.constants.AppConstants.AUTHENTICATE_ENDPOINT;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;
    private final Translator translator;

    public SecurityConfig(ObjectMapper objectMapper,
                          JWTUtil jwtUtil,
                          ExceptionHandlerFilter exceptionHandlerFilter,
                          Translator translator) {
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
        this.translator = translator;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        http
                .cors()
        .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, AUTHENTICATE_ENDPOINT).permitAll()
                .mvcMatchers(HttpMethod.PATCH, "/users/activate").permitAll()
                .mvcMatchers(HttpMethod.POST,
                        "/users",
                        "/categories/**",
                        "/units/**",
                        "/products/**")
                        .hasAuthority(AuthoritiesConstants.ROLE_MODERATOR.name())
                .mvcMatchers(HttpMethod.PATCH,
                        "/users/role",
                        "/categories/**",
                        "/units/**",
                        "/products/**")
                        .hasAuthority(AuthoritiesConstants.ROLE_MODERATOR.name())
                .mvcMatchers(HttpMethod.GET,
                        "/categories",
                        "/products")
                        .hasAuthority(AuthoritiesConstants.ROLE_USER.name())
                .mvcMatchers(HttpMethod.POST,
                        "/clients")
                        .hasAuthority(AuthoritiesConstants.ROLE_MARKETER.name())
                .anyRequest()
                .authenticated()
        .and()
                .httpBasic()
                .disable()
                .exceptionHandling()
        .and()
                .addFilterBefore(exceptionHandlerFilter, JWTAuthenticationFilter.class)
                .addFilter(authorizationFilter())
                .addFilter(authenticationFilter());

        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter authenticationFilter() throws Exception {
        var filter = new JWTAuthenticationFilter(objectMapper);
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler(jwtUtil, objectMapper));
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler(objectMapper, translator));
        filter.setAuthenticationManager(super.authenticationManager());
        filter.setFilterProcessesUrl(AUTHENTICATE_ENDPOINT);
        return filter;
    }

    @Bean
    public JWTAuthorizationFilter authorizationFilter() throws Exception {
        return new JWTAuthorizationFilter(authenticationManager(), jwtUtil);
    }
}
