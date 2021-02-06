package pl.kubaretip.cpo.api.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthenticationSuccessResponse {

    @JsonProperty("access_token")
    private String accessToken;

}
