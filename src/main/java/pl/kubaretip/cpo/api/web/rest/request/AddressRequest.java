package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AddressRequest {

    @NotBlank(message = "{validation.address.country.notBlank}")
    @Size(max = 64, message = "{validation.address.country.size}")
    private String country;

    @NotBlank(message = "{validation.address.street.notBlank}")
    @Size(max = 50, message = "{validation.address.street.size}")
    private String street;

    @NotBlank(message = "{validation.address.zipCode.notBlank}")
    @Size(max = 20, message = "{validation.address.zipCode.size}")
    private String zipCode;

    @Size(max = 20, message = "{validation.address.apartment.size}")
    private String apartment;

    @NotBlank(message = "{validation.address.building.notBlank}")
    @Size(max = 20, message = "{validation.address.building.size}")
    private String building;

    @NotBlank(message = "{validation.address.city.notBlank}")
    @Size(max = 50, message = "{validation.address.city.size}")
    private String city;

}
