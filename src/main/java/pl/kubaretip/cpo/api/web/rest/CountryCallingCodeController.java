package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.CountryCallingCodeDTO;
import pl.kubaretip.cpo.api.service.CountryCallingCodeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/country-calling-codes")
public class CountryCallingCodeController {

    private final CountryCallingCodeService countryCallingCodeService;

    public CountryCallingCodeController(CountryCallingCodeService countryCallingCodeService) {
        this.countryCallingCodeService = countryCallingCodeService;
    }

    @PostMapping
    public ResponseEntity<CountryCallingCodeDTO> createCountryCallingCode(@Valid @RequestBody CountryCallingCodeDTO dto,
                                                                          UriComponentsBuilder uriComponentsBuilder) {
        var phoneCountryCode = countryCallingCodeService.createPhoneCountryCode(dto);
        var location = uriComponentsBuilder.path("/phone-codes/{id}")
                .buildAndExpand(phoneCountryCode.getCountry().toLowerCase()).toUri();
        return ResponseEntity.created(location).body(phoneCountryCode);
    }


}
