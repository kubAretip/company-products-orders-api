package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static pl.kubaretip.cpo.api.constants.RegexpConstants.COUNTRY_CALLING_CODE_REGEXP;
import static pl.kubaretip.cpo.api.constants.RegexpConstants.ONLY_LETTERS_REGEXP;

@Getter
@Setter
@NoArgsConstructor
public class NewCountryCallingCodeRequest {

    @Size(min = 1, max = 64, message = "{validation.phoneCountryCode.country.size}")
    @NotBlank(message = "{validation.phoneCountryCode.country.notBlank}")
    @Pattern(regexp = ONLY_LETTERS_REGEXP, message = "{validation.phoneCountryCode.country.pattern}")
    private String country;

    @Size(min = 1, max = 7, message = "{validation.phoneCountryCode.code.size}")
    @NotBlank(message = "{validation.phoneCountryCode.code.notBlank}")
    @Pattern(regexp = COUNTRY_CALLING_CODE_REGEXP, message = "{validation.phoneCountryCode.code.pattern}")
    private String code;

}
