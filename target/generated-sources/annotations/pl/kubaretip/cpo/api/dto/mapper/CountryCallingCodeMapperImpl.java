package pl.kubaretip.cpo.api.dto.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pl.kubaretip.cpo.api.domain.CountryCallingCode;
import pl.kubaretip.cpo.api.dto.CountryCallingCodeDTO;
import pl.kubaretip.cpo.api.web.rest.request.NewCountryCallingCodeRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-06T10:37:16+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.21 (Eclipse Adoptium)"
)
@Component
public class CountryCallingCodeMapperImpl implements CountryCallingCodeMapper {

    @Override
    public CountryCallingCode mapToEntity(CountryCallingCodeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CountryCallingCode countryCallingCode = new CountryCallingCode();

        countryCallingCode.setCountry( dto.getCountry() );
        countryCallingCode.setCode( dto.getCode() );

        return countryCallingCode;
    }

    @Override
    public CountryCallingCodeDTO mapToDTO(CountryCallingCode entity) {
        if ( entity == null ) {
            return null;
        }

        CountryCallingCodeDTO countryCallingCodeDTO = new CountryCallingCodeDTO();

        countryCallingCodeDTO.setCountry( entity.getCountry() );
        countryCallingCodeDTO.setCode( entity.getCode() );

        return countryCallingCodeDTO;
    }

    @Override
    public CountryCallingCodeDTO mapNewCountryCallingCodeRequestToCountryCallingCodeDTO(NewCountryCallingCodeRequest request) {
        if ( request == null ) {
            return null;
        }

        CountryCallingCodeDTO countryCallingCodeDTO = new CountryCallingCodeDTO();

        countryCallingCodeDTO.setCountry( request.getCountry() );
        countryCallingCodeDTO.setCode( request.getCode() );

        return countryCallingCodeDTO;
    }
}
