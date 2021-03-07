package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ClientRequest {

    @NotBlank(message = "{validation.client.companyName.notBlank}")
    @Size(max = 50, message = "{validation.client.companyName.size}")
    protected String companyName;

    @NotBlank(message = "{validation.client.firstName.notBlank}")
    @Size(max = 50, message = "{validation.client.firstName.size}")
    protected String firstName;

    @NotBlank(message = "{validation.client.lastName.notBlank}")
    @Size(max = 50, message = "{validation.client.lastName.size}")
    protected String lastName;

    @Email
    @NotBlank(message = "{validation.client.email.notBlank}")
    @Size(max = 100, message = "{validation.client.email.size}")
    protected String email;

    @NotBlank(message = "{validation.client.phoneNumber.notBlank}")
    @Size(min = 7, max = 20, message = "{validation.client.phoneNumber.size}")
    protected String phoneNumber;


}
