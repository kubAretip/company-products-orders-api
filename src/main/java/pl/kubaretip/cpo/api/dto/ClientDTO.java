package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClientDTO {

    private Long id;

    @NotBlank(message = "{validation.client.companyName.notBlank}")
    @Size(max = 50, message = "{validation.client.companyName.size}")
    private String companyName;

    @NotBlank(message = "{validation.client.firstName.notBlank}")
    @Size(max = 50, message = "{validation.client.firstName.size}")
    private String firstName;

    @NotBlank(message = "{validation.client.lastName.notBlank}")
    @Size(max = 50, message = "{validation.client.lastName.size}")
    private String lastName;

    @Email
    @NotBlank(message = "{validation.client.email.notBlank}")
    @Size(max = 100, message = "{validation.client.email.size}")
    private String email;

    @NotBlank(message = "{validation.client.phoneNumber.notBlank}")
    @Size(min = 7, max = 20, message = "{validation.client.phoneNumber.size}")
    private String phoneNumber;

    @NotEmpty(message = "{validation.client.deliveryAddress.notEmpty}")
    private List<@Valid AddressDTO> deliveryAddresses;

}
