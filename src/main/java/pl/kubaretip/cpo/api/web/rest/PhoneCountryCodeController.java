package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.PhoneCountryCodeDTO;
import pl.kubaretip.cpo.api.service.PhoneCountryCodeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/phone-codes")
public class PhoneCountryCodeController {

    private final PhoneCountryCodeService phoneCountryCodeService;

    public PhoneCountryCodeController(PhoneCountryCodeService phoneCountryCodeService) {
        this.phoneCountryCodeService = phoneCountryCodeService;
    }

    @PostMapping
    public ResponseEntity<PhoneCountryCodeDTO> createPhoneCountryCode(@Valid @RequestBody PhoneCountryCodeDTO dto,
                                                                      UriComponentsBuilder uriComponentsBuilder) {
        var phoneCountryCode = phoneCountryCodeService.createPhoneCountryCode(dto);
        var location = uriComponentsBuilder.path("/phone-codes/{id}")
                .buildAndExpand(phoneCountryCode.getCountry().toLowerCase()).toUri();
        return ResponseEntity.created(location).body(phoneCountryCode);
    }


}
