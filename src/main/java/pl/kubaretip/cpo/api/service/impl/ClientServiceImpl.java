package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Client;
import pl.kubaretip.cpo.api.dto.ClientDTO;
import pl.kubaretip.cpo.api.dto.mapper.AddressMapper;
import pl.kubaretip.cpo.api.dto.mapper.ClientMapper;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.repository.ClientRepository;
import pl.kubaretip.cpo.api.service.ClientService;
import pl.kubaretip.cpo.api.util.Translator;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final Translator translator;
    private final AddressMapper addressMapper;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository,
                             Translator translator,
                             AddressMapper addressMapper,
                             ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.translator = translator;
        this.addressMapper = addressMapper;
        this.clientMapper = clientMapper;
    }


    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {

        if (clientRepository.existsByCompanyNameIgnoreCase(clientDTO.getCompanyName())) {
            throw new AlreadyExistsException(translator.translate("common.alreadyExists.title"),
                    "Client with company name already exists");
        }

        if (clientRepository.existsByEmailIgnoreCase(clientDTO.getEmail())) {
            throw new AlreadyExistsException(translator.translate("common.alreadyExists.title"),
                    "Client with email already exists");
        }

        if (clientRepository.existsByPhoneNumberIgnoreCase(clientDTO.getPhoneNumber())) {
            throw new AlreadyExistsException(translator.translate("common.alreadyExists.title"),
                    "Client with phone number already exists");
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
        var addresses = addressMapper.mapToEntityList(clientDTO.getDeliveryAddresses());
        addresses.forEach(address -> address.setClient(client));
        client.setDeliveryAddresses(addresses);
        clientRepository.save(client);
        return clientMapper.mapToDTO(client);
    }

}