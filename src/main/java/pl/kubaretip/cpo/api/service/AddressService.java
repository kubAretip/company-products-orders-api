package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.Address;

public interface AddressService {
    Address findAddressById(long addressId);
}
