package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.Client;
import pl.kubaretip.cpo.api.dto.ClientDTO;

public interface ClientService {
    ClientDTO createClient(ClientDTO clientDTO);

    Client findClientById(long id);
}
