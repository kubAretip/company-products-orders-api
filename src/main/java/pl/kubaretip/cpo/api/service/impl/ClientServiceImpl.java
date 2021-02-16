package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Client;
import pl.kubaretip.cpo.api.dto.ClientDTO;
import pl.kubaretip.cpo.api.dto.mapper.AddressMapper;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.ClientRepository;
import pl.kubaretip.cpo.api.service.ClientService;
import pl.kubaretip.cpo.api.util.Translator;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final Translator translator;
    private final AddressMapper addressMapper;

    public ClientServiceImpl(ClientRepository clientRepository,
                             Translator translator,
                             AddressMapper addressMapper) {
        this.clientRepository = clientRepository;
        this.translator = translator;
        this.addressMapper = addressMapper;
    }


    @Override
    public Client createClient(ClientDTO clientDTO) {

        if (clientRepository.existsByCompanyNameIgnoreCase(clientDTO.getCompanyName())) {
            throw new AlreadyExistsException(translator.translate("common.alreadyExists.title"),
                    translator.translate("client.alreadyExists.companyName", new Object[]{clientDTO.getCompanyName()}));
        }

        if (clientRepository.existsByEmailIgnoreCase(clientDTO.getEmail())) {
            throw new AlreadyExistsException(translator.translate("common.alreadyExists.title"),
                    translator.translate("client.alreadyExists.email", new Object[]{clientDTO.getEmail()}));
        }

        if (clientRepository.existsByPhoneNumberIgnoreCase(clientDTO.getPhoneNumber())) {
            throw new AlreadyExistsException(translator.translate("common.alreadyExists.title"),
                    translator.translate("client.alreadyExists.phoneNumber", new Object[]{clientDTO.getPhoneNumber()}));
        }

        var client = new Client();
        client.setId(null);
        var firstName = StringUtils.capitalize(clientDTO.getFirstName()
                .toLowerCase().replaceAll("\\s+", ""));

        var lastName = StringUtils.capitalize(clientDTO.getLastName()
                .toLowerCase().replaceAll("\\s+", ""));

        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(clientDTO.getEmail().toLowerCase());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setCompanyName(clientDTO.getCompanyName());
        var addresses = addressMapper.mapToEntityList(clientDTO.getAddresses());
        addresses.forEach(address -> address.setClient(client));
        client.setAddresses(addresses);
        return clientRepository.save(client);
    }

    @Override
    public Client findClientById(long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(translator.translate("common.notFound.title"),
                        translator.translate("client.notfound", new Object[]{id})));
    }


}
