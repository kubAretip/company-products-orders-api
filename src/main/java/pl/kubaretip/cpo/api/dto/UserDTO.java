package pl.kubaretip.cpo.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.validation.groups.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static pl.kubaretip.cpo.api.config.AppConstants.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "{validation.userActivation.username.notBlank.message}", groups = {Update.class})
    private String username;

    @NotBlank(message = "{validation.user.firstName.notBlank.message}")
    @Size(min = 1, max = 50, message = "{validation.user.firstName.size.message}")
    @Pattern(regexp = "[a-zA-Z]+", message = "{validation.user.firstName.onlyLetters.message}")
    private String firstName;

    @NotBlank(message = "{validation.user.lastName.notBlank.message}")
    @Size(min = 1, max = 50, message = "{validation.user.lastName.size.message}")
    @Pattern(regexp = "[a-zA-Z]+", message = "{validation.user.lastName.onlyLetters.message}")
    private String lastName;

    @Email(message = "{validation.user.email.incorrect.message}")
    @NotBlank(message = "{validation.user.email.notBlank.message}")
    @Size(max = 100, message = "{validation.user.email.size.max.message}")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "{validation.userActivation.password.notBlank.message}", groups = {Update.class})
    @Pattern(regexp = PASSWORD_REGEXP, message = "{validation.userActivation.password.regexp.message}", groups = {Update.class})
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "{validation.userActivation.password.size.message}", groups = {Update.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "[0-9]+", message = "{validation.userActivation.activationKey.onlyDigits.message}", groups = {Update.class})
    @NotBlank(message = "{validation.userActivation.activationKey.notBlank.message}", groups = {Update.class})
    private String activationKey;

    private PhoneCountryCodeDTO phoneCountryCode;

    private String phoneNumber;

}
