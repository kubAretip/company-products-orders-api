package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.constants.AuthoritiesConstants;
import pl.kubaretip.cpo.api.dto.UnitDTO;
import pl.kubaretip.cpo.api.dto.mapper.UnitMapper;
import pl.kubaretip.cpo.api.service.UnitService;
import pl.kubaretip.cpo.api.util.ExceptionUtils;
import pl.kubaretip.cpo.api.web.rest.request.EditUnitRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewUnitRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;
    private final ExceptionUtils exceptionUtils;
    private final UnitMapper unitMapper;

    public UnitController(UnitService unitService,
                          ExceptionUtils exceptionUtils,
                          UnitMapper unitMapper) {
        this.unitService = unitService;
        this.exceptionUtils = exceptionUtils;
        this.unitMapper = unitMapper;
    }

    @Secured(AuthoritiesConstants.Code.MODERATOR)
    @PostMapping
    public ResponseEntity<UnitDTO> createUnit(@Valid @RequestBody NewUnitRequest request,
                                              UriComponentsBuilder uriComponentsBuilder) {
        var resultUnitDTO = unitMapper.mapToDTO(unitService.createUnit(request.toDTO()));
        var locationURI = uriComponentsBuilder.path("/units/{id}")
                .buildAndExpand(resultUnitDTO.getId()).toUri();
        return ResponseEntity.created(locationURI).body(resultUnitDTO);
    }

    @Secured(AuthoritiesConstants.Code.MODERATOR)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> markUnitAsDeleted(@PathVariable("id") long unitId) {
        unitService.markUnitAsDeleted(unitId);
        return ResponseEntity.accepted().build();
    }

    @Secured(AuthoritiesConstants.Code.MODERATOR)
    @PatchMapping(path = "/{id}")
    public ResponseEntity<UnitDTO> editUnit(@PathVariable("id") long unitId, @Valid @RequestBody EditUnitRequest request) {
        if (request.getId() != unitId) {
            throw exceptionUtils.pathIdNotEqualsBodyId();
        }
        return ResponseEntity.ok(unitMapper.mapToDTO(unitService.modifyUnit(request.toDTO())));
    }

}
