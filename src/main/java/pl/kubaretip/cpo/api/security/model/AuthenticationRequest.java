package pl.kubaretip.cpo.api.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthenticationRequest {

    private String login;
    private String password;

}
