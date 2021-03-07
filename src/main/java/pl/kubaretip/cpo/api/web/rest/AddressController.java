package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.dto.AddressDTO;
import pl.kubaretip.cpo.api.dto.mapper.AddressMapper;
import pl.kubaretip.cpo.api.service.AddressService;
import pl.kubaretip.cpo.api.util.ExceptionUtils;
import pl.kubaretip.cpo.api.web.rest.request.UpdateAddressRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private final ExceptionUtils exceptionUtils;

    public AddressController(AddressService addressService,
                             AddressMapper addressMapper,
                             ExceptionUtils exceptionUtils) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
        this.exceptionUtils = exceptionUtils;
    }

    @Secured(AuthoritiesConstants.Code.USER)
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable("id") long addressId) {
        return ResponseEntity.ok()
                .body(addressMapper.mapToDTO(addressService.findAddressById(addressId)));
    }

    @Secured({AuthoritiesConstants.Code.MARKETER})
    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddressById(@PathVariable("id") long addressId,
                                                        @Valid @RequestBody UpdateAddressRequest request) {
        if (request.getId() != addressId) {
            throw exceptionUtils.pathIdNotEqualsBodyId();
        }
        var address = addressService.updateAddressById(addressMapper.mapUpdateAddressRequestToAddressDTO(request));
        return ResponseEntity.ok(addressMapper.mapToDTO(address));
    }

    @Secured({AuthoritiesConstants.Code.MARKETER})
    @PatchMapping("/{id}")
    public ResponseEntity<Void> markAddressAsDeleted(@PathVariable("id") long addressId) {
        addressService.markAddressAsDeleted(addressId);
        return ResponseEntity.noContent().build();
    }

}
