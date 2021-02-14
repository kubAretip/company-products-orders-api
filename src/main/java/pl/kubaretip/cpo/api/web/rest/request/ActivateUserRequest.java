package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static pl.kubaretip.cpo.api.constants.AppConstants.*;
import static pl.kubaretip.cpo.api.constants.RegexpConstants.PASSWORD_REGEXP;

@NoArgsConstructor
@Getter
@Setter
public class ActivateUserRequest {

    @NotBlank(message = "{validation.user.username.notBlank}")
    private String username;

    @NotBlank(message = "{validation.user.password.notBlank}")
    @Pattern(regexp = PASSWORD_REGEXP, message = "{validation.user.password.pattern}")
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "{validation.user.password.size}")
    private String password;

    @Digits(integer = USER_ACTIVATION_KEY_LENGTH, fraction = 0, message = "{validation.user.activationKey.digits}")
    @NotBlank(message = "{validation.user.activationKey.notBlank}")
    private String activationKey;

}
