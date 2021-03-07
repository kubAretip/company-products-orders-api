package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.dto.AddressDTO;
import pl.kubaretip.cpo.api.dto.mapper.AddressMapper;
import pl.kubaretip.cpo.api.service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public AddressController(AddressService addressService,
                             AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @Secured(AuthoritiesConstants.Code.USER)
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable("id") long addressId) {
        return ResponseEntity.ok()
                .body(addressMapper.mapToDTO(addressService.findAddressById(addressId)));
    }


}
