package pl.kubaretip.cpo.api.web.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest extends UserRequest {

    @NotNull
    private Long id;

    @Size(min = 7, max = 20, message = "{validation.client.phoneNumber.size}")
    private String phoneNumber;

}
