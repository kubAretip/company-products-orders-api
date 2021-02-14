package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.CountryCallingCode;
import pl.kubaretip.cpo.api.dto.CountryCallingCodeDTO;

public interface CountryCallingCodeService {
    CountryCallingCode createPhoneCountryCode(CountryCallingCodeDTO dto);
}
