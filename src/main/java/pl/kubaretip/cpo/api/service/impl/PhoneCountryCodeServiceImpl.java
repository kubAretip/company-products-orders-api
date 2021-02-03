package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.kubaretip.cpo.api.dto.PhoneCountryCodeDTO;
import pl.kubaretip.cpo.api.dto.mapper.PhoneCountryCodeMapper;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.repository.PhoneCountryCodeRepository;
import pl.kubaretip.cpo.api.service.PhoneCountryCodeService;

import javax.validation.Valid;

@Service
@Validated
public class PhoneCountryCodeServiceImpl implements PhoneCountryCodeService {

    private final PhoneCountryCodeMapper phoneCountryCodeMapper;
    private final PhoneCountryCodeRepository phoneCountryCodeRepository;

    public PhoneCountryCodeServiceImpl(PhoneCountryCodeMapper phoneCountryCodeMapper,
                                       PhoneCountryCodeRepository phoneCountryCodeRepository) {
        this.phoneCountryCodeMapper = phoneCountryCodeMapper;
        this.phoneCountryCodeRepository = phoneCountryCodeRepository;
    }

    @Override
    public PhoneCountryCodeDTO createPhoneCountryCode(@Valid PhoneCountryCodeDTO dto) {

        if (phoneCountryCodeRepository.existsByCountryIgnoreCase(dto.getCountry())) {
            throw new AlreadyExistsException("Phone code already exist", "Phone code for country " + dto.getCountry()
                    + " already exists.");
        }

        if (phoneCountryCodeRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new AlreadyExistsException("Phone code already exists", "Phone code " + dto.getCode()
                    + " already exists.");
        }

        dto.setCountry(StringUtils.capitalize(dto.getCountry().toLowerCase()));

        var phoneCountryCode = phoneCountryCodeMapper.mapToEntity(dto);
        phoneCountryCodeRepository.save(phoneCountryCode);
        return phoneCountryCodeMapper.mapToDTO(phoneCountryCode);
    }


}
