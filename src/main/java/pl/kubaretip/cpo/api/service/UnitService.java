package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.dto.UnitDTO;

public interface UnitService {
    UnitDTO getUnitById(long unitId);

    UnitDTO createUnit(UnitDTO unitDTO);

    void markUnitAsDeleted(long unitId);

    UnitDTO modifyUnit(UnitDTO unitDTO);
}
