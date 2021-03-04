package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class EditUserRequest {

    @Size(min = 7, max = 20, message = "{validation.client.phoneNumber.size}")
    private String phoneNumber;

}
