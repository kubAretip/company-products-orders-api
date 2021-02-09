package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.kubaretip.cpo.api.dto.PhoneCountryCodeDTO;
import pl.kubaretip.cpo.api.dto.mapper.PhoneCountryCodeMapper;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.repository.PhoneCountryCodeRepository;
import pl.kubaretip.cpo.api.service.PhoneCountryCodeService;
import pl.kubaretip.cpo.api.util.Translator;

import javax.validation.Valid;

@Service
@Validated
public class PhoneCountryCodeServiceImpl implements PhoneCountryCodeService {

    private final PhoneCountryCodeMapper phoneCountryCodeMapper;
    private final PhoneCountryCodeRepository phoneCountryCodeRepository;
    private final Translator translator;

    public PhoneCountryCodeServiceImpl(PhoneCountryCodeMapper phoneCountryCodeMapper,
                                       PhoneCountryCodeRepository phoneCountryCodeRepository,
                                       Translator translator) {
        this.phoneCountryCodeMapper = phoneCountryCodeMapper;
        this.phoneCountryCodeRepository = phoneCountryCodeRepository;
        this.translator = translator;
    }

    @Override
    public PhoneCountryCodeDTO createPhoneCountryCode(@Valid PhoneCountryCodeDTO dto) {

        if (phoneCountryCodeRepository.existsByCountryIgnoreCase(dto.getCountry())) {
            throw new AlreadyExistsException(translator.translate("phoneCode.alreadyExists.title"),
                    translator.translate("phoneCode.alreadyExists.country.message", new Object[]{dto.getCountry()}));
        }

        if (phoneCountryCodeRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new AlreadyExistsException(translator.translate("phoneCode.alreadyExists.title"),
                    translator.translate("phoneCode.alreadyExists.code.message", new Object[]{dto.getCode()}));
        }

        dto.setCountry(StringUtils.capitalize(dto.getCountry().toLowerCase()));

        var phoneCountryCode = phoneCountryCodeMapper.mapToEntity(dto);
        phoneCountryCodeRepository.save(phoneCountryCode);
        return phoneCountryCodeMapper.mapToDTO(phoneCountryCode);
    }


}
