package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.Address;
import pl.kubaretip.cpo.api.dto.AddressDTO;

public interface AddressService {
    Address findAddressById(long addressId);

    Address updateAddressById(AddressDTO addressDTO);
}
