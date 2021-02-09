package pl.kubaretip.cpo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class PhoneCountryCodeDTO {

    @Size(min = 1, max = 64, message = "{validation.phoneCountryCode.country.size}")
    @NotBlank(message = "{validation.phoneCountryCode.country.notBlank}")
    @Pattern(regexp = "[a-zA-Z]+", message = "{validation.phoneCountryCode.country.pattern}")
    private String country;

    @Size(min = 1, max = 7, message = "{validation.phoneCountryCode.code.size}")
    @NotBlank(message = "{validation.phoneCountryCode.code.notBlank}")
    @Pattern(regexp = "^(?!-)[\\d-]+.*(?<!-)$", message = "{validation.phoneCountryCode.code.pattern}")
    private String code;

}