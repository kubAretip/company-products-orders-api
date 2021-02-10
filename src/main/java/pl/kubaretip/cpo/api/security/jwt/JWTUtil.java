package pl.kubaretip.cpo.api.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.error.MissingEnvironmentVariableException;
import pl.kubaretip.cpo.api.security.SecurityUser;

import java.util.Date;
import java.util.stream.Collectors;

import static pl.kubaretip.cpo.api.constants.AppConstants.JWT_PREFIX;
import static pl.kubaretip.cpo.api.constants.JWTClaimsConstants.AUTHORITIES_KEY;
import static pl.kubaretip.cpo.api.constants.JWTClaimsConstants.SUB_ID_KEY;


@Component
public class JWTUtil {

    private final long tokenValidityTimeInMilliseconds;
    private final Algorithm sign;

    // default validity time is 12h
    public JWTUtil(@Value("${jwt.base64-secret}") String base64Secret,
                   @Value("${jwt.validity-time-in-seconds:43200}") Long validityTimeInSeconds) {

        if (!StringUtils.isNotBlank(base64Secret)) {
            throw new MissingEnvironmentVariableException("Secret can not be blank.");
        }

        if (!Base64.isBase64(base64Secret)) {
            throw new IllegalArgumentException("Secret is not Base64");
        }

        this.tokenValidityTimeInMilliseconds = validityTimeInSeconds * 1000;
        this.sign = Algorithm.HMAC512(Base64.decodeBase64(base64Secret));

    }

    public String buildToken(Authentication authentication) {
        var user = (SecurityUser) authentication.getPrincipal();
        var authorities = user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        var expiresAt = new Date(System.currentTimeMillis() + this.tokenValidityTimeInMilliseconds);

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiresAt)
                .withClaim(SUB_ID_KEY, user.getId())
                .withClaim(AUTHORITIES_KEY, authorities)
                .sign(sign);
    }

    public boolean isValidAuthorizationHeaderValue(String authHeaderValue) {
        return StringUtils.isNotBlank(authHeaderValue)
                && authHeaderValue.contains(JWT_PREFIX)
                && StringUtils.isNotBlank(authHeaderValue.replace(JWT_PREFIX, ""));
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {

        var decodedJWT = JWT
                .require(sign)
                .build()
                .verify(token);

        if (decodedJWT != null) {
            var authorities = decodedJWT.getClaim(AUTHORITIES_KEY)
                    .asList(String.class)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            var username = decodedJWT.getSubject();
            var userId = decodedJWT.getClaim(SUB_ID_KEY).asLong();
            var securityUser = new SecurityUser(userId, username, "", authorities);

            return new UsernamePasswordAuthenticationToken(securityUser, token, authorities);

        }
        return null;
    }


}

























