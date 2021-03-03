package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.dto.ClientDTO;
import pl.kubaretip.cpo.api.dto.mapper.ClientMapper;
import pl.kubaretip.cpo.api.service.ClientService;
import pl.kubaretip.cpo.api.web.rest.request.NewClientRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService,
                            ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @Secured(AuthoritiesConstants.Code.MARKETER)
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody NewClientRequest request,
                                                  UriComponentsBuilder uriComponentsBuilder) {
        var client = clientService.createClient(request.toDTO());
        var locationURI = uriComponentsBuilder.path("/clients/{id}")
                .buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(locationURI).body(clientMapper.mapToDTO(client));
    }

}
