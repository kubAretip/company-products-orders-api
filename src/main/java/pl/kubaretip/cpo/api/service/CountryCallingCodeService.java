package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.dto.CountryCallingCodeDTO;

public interface CountryCallingCodeService {
    CountryCallingCodeDTO createPhoneCountryCode(CountryCallingCodeDTO dto);
}
