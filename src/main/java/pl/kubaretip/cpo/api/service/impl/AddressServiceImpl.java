package pl.kubaretip.cpo.api.service.impl;

import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Address;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.AddressRepository;
import pl.kubaretip.cpo.api.service.AddressService;
import pl.kubaretip.cpo.api.util.Translator;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final Translator translator;

    public AddressServiceImpl(AddressRepository addressRepository,
                              Translator translator) {
        this.addressRepository = addressRepository;
        this.translator = translator;
    }

    @Override
    public Address findAddressById(long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(translator.translate("common.notFound.title"),
                        translator.translate("address.notFound.id.message", new Object[]{addressId})));
    }


}
