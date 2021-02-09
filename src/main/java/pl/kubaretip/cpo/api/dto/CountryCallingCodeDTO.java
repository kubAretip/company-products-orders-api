package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static pl.kubaretip.cpo.api.util.RegexpConstants.COUNTRY_CALLING_CODE_REGEXP;
import static pl.kubaretip.cpo.api.util.RegexpConstants.ONLY_LETTERS_REGEXP;

@Setter
@Getter
@NoArgsConstructor
public class CountryCallingCodeDTO {

    @Size(min = 1, max = 64, message = "{validation.phoneCountryCode.country.size}")
    @NotBlank(message = "{validation.phoneCountryCode.country.notBlank}")
    @Pattern(regexp = ONLY_LETTERS_REGEXP, message = "{validation.phoneCountryCode.country.pattern}")
    private String country;

    @Size(min = 1, max = 7, message = "{validation.phoneCountryCode.code.size}")
    @NotBlank(message = "{validation.phoneCountryCode.code.notBlank}")
    @Pattern(regexp = COUNTRY_CALLING_CODE_REGEXP, message = "{validation.phoneCountryCode.code.pattern}")
    private String code;

}