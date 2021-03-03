package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kubaretip.cpo.api.dto.AddressDTO;
import pl.kubaretip.cpo.api.dto.ClientDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
public class NewClientRequest extends ClientRequest{

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

    @Override
    public ClientDTO toDTO() {

        var addressDTO = new AddressDTO();
        addressDTO.setCountry(this.country);
        addressDTO.setStreet(this.street);
        addressDTO.setZipCode(this.zipCode);
        addressDTO.setApartment(this.apartment);
        addressDTO.setBuilding(this.building);
        addressDTO.setCity(this.city);

        var clientDTO = new ClientDTO();
        clientDTO.setCompanyName(this.companyName);
        clientDTO.setFirstName(this.firstName);
        clientDTO.setLastName(this.lastName);
        clientDTO.setEmail(this.email);
        clientDTO.setPhoneNumber(this.phoneNumber);
        clientDTO.setAddresses(Collections.singletonList(addressDTO));

        return clientDTO;
    }


}
