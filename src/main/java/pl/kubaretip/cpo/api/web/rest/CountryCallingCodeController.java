package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.CountryCallingCodeDTO;
import pl.kubaretip.cpo.api.dto.mapper.CountryCallingCodeMapper;
import pl.kubaretip.cpo.api.service.CountryCallingCodeService;
import pl.kubaretip.cpo.api.web.rest.request.NewCountryCallingCodeRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/country-calling-codes")
public class CountryCallingCodeController {

    private final CountryCallingCodeService countryCallingCodeService;
    private final CountryCallingCodeMapper countryCallingCodeMapper;

    public CountryCallingCodeController(CountryCallingCodeService countryCallingCodeService,
                                        CountryCallingCodeMapper countryCallingCodeMapper) {
        this.countryCallingCodeService = countryCallingCodeService;
        this.countryCallingCodeMapper = countryCallingCodeMapper;
    }

    @PostMapping
    public ResponseEntity<CountryCallingCodeDTO> createCountryCallingCode(@Valid @RequestBody NewCountryCallingCodeRequest request,
                                                                          UriComponentsBuilder uriComponentsBuilder) {
        var phoneCountryCode = countryCallingCodeService.createPhoneCountryCode(request.toDTO());
        var location = uriComponentsBuilder.path("/country-calling-codes/{id}")
                .buildAndExpand(phoneCountryCode.getCountry().toLowerCase()).toUri();
        return ResponseEntity.created(location).body(countryCallingCodeMapper.mapToDTO(phoneCountryCode));
    }


}
