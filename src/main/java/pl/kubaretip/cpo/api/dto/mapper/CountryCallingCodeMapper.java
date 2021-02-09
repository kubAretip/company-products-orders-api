package pl.kubaretip.cpo.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kubaretip.cpo.api.domain.CountryCallingCode;
import pl.kubaretip.cpo.api.dto.CountryCallingCodeDTO;

@Mapper(componentModel = "spring")
public interface CountryCallingCodeMapper {

    CountryCallingCode mapToEntity(CountryCallingCodeDTO dto);

    CountryCallingCodeDTO mapToDTO(CountryCallingCode entity);

}
