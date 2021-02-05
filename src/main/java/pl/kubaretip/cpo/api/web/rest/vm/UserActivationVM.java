package pl.kubaretip.cpo.api.web.rest.vm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static pl.kubaretip.cpo.api.config.AppConstants.*;

@Getter
@Setter
public class UserActivationVM {

    @NotBlank(message = "{validation.userActivation.username.notBlank.message}")
    private String username;

    @NotBlank(message = "{validation.userActivation.password.notBlank.message}")
    @Pattern(regexp = PASSWORD_REGEXP, message = "{validation.userActivation.password.regexp.message}")
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "{validation.userActivation.password.size.message}")
    private String password;

    @Pattern(regexp = "[0-9]+", message = "{validation.userActivation.activationKey.onlyDigits.message}")
    @NotBlank(message = "{validation.userActivation.activationKey.notBlank.message}")
    private String activationKey;

}
