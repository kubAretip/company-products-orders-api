package pl.kubaretip.cpo.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kubaretip.cpo.api.dto.UnitDTO;
import pl.kubaretip.cpo.api.service.UnitService;
import pl.kubaretip.cpo.api.util.ExceptionUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;
    private final ExceptionUtils exceptionUtils;

    public UnitController(UnitService unitService,
                          ExceptionUtils exceptionUtils) {
        this.unitService = unitService;
        this.exceptionUtils = exceptionUtils;
    }

    @PostMapping
    public ResponseEntity<UnitDTO> createUnit(@Valid @RequestBody UnitDTO unitDTO,
                                              UriComponentsBuilder uriComponentsBuilder) {
        var unit = unitService.createUnit(unitDTO);
        var locationURI = uriComponentsBuilder.path("/units/{id}")
                .buildAndExpand(unit.getId()).toUri();
        return ResponseEntity.created(locationURI).body(unit);
    }


    @PatchMapping(path = "/{id}", params = {"remove"})
    public ResponseEntity<Void> markUnitAsDeleted(@PathVariable("id") long unitId,
                                                  @RequestParam boolean remove) {
        unitService.markUnitAsDeleted(unitId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<UnitDTO> editUnit(@PathVariable("id") long unitId,
                                            @Valid @RequestBody UnitDTO unitDTO) {
        if (unitDTO.getId() == null || unitDTO.getId() != unitId)
            throw exceptionUtils.pathIdNotEqualsBodyId();

        return ResponseEntity.ok(unitService.modifyUnit(unitDTO));
    }

}
