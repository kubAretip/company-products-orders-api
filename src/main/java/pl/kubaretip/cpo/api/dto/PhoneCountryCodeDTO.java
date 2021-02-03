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

    @Size(min = 1, max = 64)
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+")
    private String country;

    @Size(min = 1, max = 7)
    @NotBlank
    @Pattern(regexp = "^(?!-)[\\d-]+.*(?<!-)$")
    private String code;

}