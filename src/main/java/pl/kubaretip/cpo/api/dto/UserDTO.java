package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;
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

    private PhoneCountryCodeDTO phoneCountryCode;

    private String phoneNumber;

}
