package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static pl.kubaretip.cpo.api.constants.RegexpConstants.ONLY_LETTERS_REGEXP;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "{validation.user.firstName.notBlank}")
    @Size(min = 1, max = 50, message = "{validation.user.firstName.size}")
    @Pattern(regexp = ONLY_LETTERS_REGEXP, message = "{validation.user.firstName.pattern}")
    protected String firstName;

    @NotBlank(message = "{validation.user.lastName.notBlank}")
    @Size(min = 1, max = 50, message = "{validation.user.lastName.size}")
    @Pattern(regexp = ONLY_LETTERS_REGEXP, message = "{validation.user.lastName.pattern}")
    protected String lastName;

    @Email(message = "{validation.user.email.email}")
    @NotBlank(message = "{validation.user.email.notBlank}")
    @Size(max = 100, message = "{validation.user.email.size}")
    protected String email;

}
