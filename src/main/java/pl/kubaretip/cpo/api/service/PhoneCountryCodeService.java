package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.dto.PhoneCountryCodeDTO;

import javax.validation.Valid;

public interface PhoneCountryCodeService {
    PhoneCountryCodeDTO createPhoneCountryCode(@Valid PhoneCountryCodeDTO dto);
}
