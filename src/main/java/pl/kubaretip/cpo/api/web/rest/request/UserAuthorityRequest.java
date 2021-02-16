package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class UserAuthorityRequest {

    @NotNull(message = "{validation.authority.userId.notNull}")
    private Long userId;

    @NotBlank(message = "{validation.authority.authorityName.notBlank}")
    private String authorityName;

}
