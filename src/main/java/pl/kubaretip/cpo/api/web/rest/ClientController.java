package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.ClientDTO;
import pl.kubaretip.cpo.api.service.ClientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientDTO clientDTO,
                                                  UriComponentsBuilder uriComponentsBuilder) {
        var client = clientService.createClient(clientDTO);
        var locationURI = uriComponentsBuilder.path("/clients/{id}")
                .buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(locationURI).body(client);
    }

}
