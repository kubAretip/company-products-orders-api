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

    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = "[a-zA-Z]+")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = "[a-zA-Z]+")
    private String lastName;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    private PhoneCountryCodeDTO phoneCountryCode;

    private String phoneNumber;

}
