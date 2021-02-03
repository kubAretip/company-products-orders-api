package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kubaretip.cpo.api.domain.PhoneCountryCode;
import pl.kubaretip.cpo.api.dto.PhoneCountryCodeDTO;

@Mapper(componentModel = "spring")
public interface PhoneCountryCodeMapper {

    PhoneCountryCode mapToEntity(PhoneCountryCodeDTO dto);

    PhoneCountryCodeDTO mapToDTO(PhoneCountryCode entity);

}
