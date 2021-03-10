package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.CountryCallingCode;
import pl.kubaretip.cpo.api.dto.CountryCallingCodeDTO;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.repository.CountryCallingCodeRepository;
import pl.kubaretip.cpo.api.service.CountryCallingCodeService;

@Service
public class CountryCallingCodeServiceImpl implements CountryCallingCodeService {

    private final CountryCallingCodeRepository countryCallingCodeRepository;

    public CountryCallingCodeServiceImpl(CountryCallingCodeRepository countryCallingCodeRepository) {
        this.countryCallingCodeRepository = countryCallingCodeRepository;
    }

    @Override
    public CountryCallingCode createPhoneCountryCode(CountryCallingCodeDTO dto) {

        if (countryCallingCodeRepository.existsByCountryIgnoreCase(dto.getCountry())) {
            throw new AlreadyExistsException("exception.phoneCode.alreadyExists.country", new Object[]{dto.getCountry()});
        }

        if (countryCallingCodeRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new AlreadyExistsException("exception.phoneCode.alreadyExists.code", new Object[]{dto.getCode()});
        }

        var countryName = StringUtils.capitalize(dto.getCountry().toLowerCase());
        return countryCallingCodeRepository.save(new CountryCallingCode(countryName, dto.getCode()));
    }


}
