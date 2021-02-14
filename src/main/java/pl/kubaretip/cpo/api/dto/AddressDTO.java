package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {

    private Long id;
    private String country;
    private String street;
    private String zipCode;
    private String apartment;
    private String building;
    private String city;

    public AddressDTO(Long id) {
        this.id = id;
    }
}
