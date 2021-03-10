package pl.kubaretip.cpo.api.service.impl;

import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Address;
import pl.kubaretip.cpo.api.dto.AddressDTO;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.AddressRepository;
import pl.kubaretip.cpo.api.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address findAddressById(long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("exception.address.notFound", new Object[]{addressId}));
    }


    @Override
    public Address updateAddressById(AddressDTO addressDTO) {
        var address = findAddressById(addressDTO.getId());

        address.setApartment(addressDTO.getApartment());
        address.setBuilding(addressDTO.getBuilding());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        address.setZipCode(addressDTO.getZipCode());
        address.setStreet(addressDTO.getStreet());

        return addressRepository.save(address);
    }

    @Override
    public void markAddressAsDeleted(long addressId) {
        var address = findAddressById(addressId);
        address.setDeleted(true);
        addressRepository.save(address);
    }


}
