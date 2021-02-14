package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClientDTO {

    private Long id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<AddressDTO> addresses;

    public ClientDTO(Long id) {
        this.id = id;
    }
}
