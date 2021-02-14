package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.Unit;
import pl.kubaretip.cpo.api.dto.UnitDTO;

public interface UnitService {
    Unit getUnitById(long unitId);

    Unit createUnit(UnitDTO unitDTO);

    void markUnitAsDeleted(long unitId);

    Unit modifyUnit(UnitDTO unitDTO);
}
