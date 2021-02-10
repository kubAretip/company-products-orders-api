package pl.kubaretip.cpo.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.validation.groups.Update;

import javax.validation.constraints.*;

import static pl.kubaretip.cpo.api.constants.AppConstants.*;
import static pl.kubaretip.cpo.api.constants.RegexpConstants.ONLY_LETTERS_REGEXP;
import static pl.kubaretip.cpo.api.constants.RegexpConstants.PASSWORD_REGEXP;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "{validation.user.username.notBlank}", groups = {Update.class})
    private String username;

    @NotBlank(message = "{validation.user.firstName.notBlank}")
    @Size(min = 1, max = 50, message = "{validation.user.firstName.size}")
    @Pattern(regexp = ONLY_LETTERS_REGEXP, message = "{validation.user.firstName.pattern}")
    private String firstName;

    @NotBlank(message = "{validation.user.lastName.notBlank}")
    @Size(min = 1, max = 50, message = "{validation.user.lastName.size}")
    @Pattern(regexp = ONLY_LETTERS_REGEXP, message = "{validation.user.lastName.pattern}")
    private String lastName;

    @Email(message = "{validation.user.email.email}")
    @NotBlank(message = "{validation.user.email.notBlank}")
    @Size(max = 100, message = "{validation.user.email.size}")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "{validation.user.password.notBlank}", groups = {Update.class})
    @Pattern(regexp = PASSWORD_REGEXP, message = "{validation.user.password.pattern}", groups = {Update.class})
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "{validation.user.password.size}", groups = {Update.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Digits(integer = USER_ACTIVATION_KEY_LENGTH, fraction = 0, message = "{validation.user.activationKey.digits}", groups = {Update.class})
    @NotBlank(message = "{validation.user.activationKey.notBlank}", groups = {Update.class})
    private String activationKey;

    private String phoneNumber;

}
