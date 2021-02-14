package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Unit;
import pl.kubaretip.cpo.api.dto.UnitDTO;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.UnitRepository;
import pl.kubaretip.cpo.api.service.UnitService;
import pl.kubaretip.cpo.api.util.Translator;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final Translator translator;

    public UnitServiceImpl(UnitRepository unitRepository,
                           Translator translator) {
        this.unitRepository = unitRepository;
        this.translator = translator;
    }

    @Override
    public Unit getUnitById(long unitId) {
        return findUnitById(unitId);
    }

    @Override
    public Unit createUnit(UnitDTO unitDTO) {

        if (unitRepository.existsByName(unitDTO.getName()))
            throw unitWithNameAlreadyExists(unitDTO.getName());

        if (unitRepository.existsBySymbol(unitDTO.getSymbol()))
            throw unitWithSymbolAlreadyExists(unitDTO.getSymbol());

        var newUnit = new Unit();
        newUnit.setName(StringUtils.capitalize(unitDTO.getName().toLowerCase()));
        newUnit.setSymbol(unitDTO.getSymbol().toUpperCase());
        return unitRepository.save(newUnit);
    }


    @Override
    public void markUnitAsDeleted(long unitId) {
        var unit = findUnitById(unitId);
        unit.setDeleted(true);
        unitRepository.save(unit);
    }

    @Override
    public Unit modifyUnit(UnitDTO unitDTO) {

        var unit = findUnitById(unitDTO.getId());

        unitRepository.findByName(unitDTO.getName())
                .ifPresent(existingUnit -> {
                    if (!existingUnit.getId().equals(unitDTO.getId()))
                        throw unitWithNameAlreadyExists(unitDTO.getName());
                });

        unitRepository.findBySymbol(unitDTO.getSymbol())
                .ifPresent(existingUnit -> {
                    if (!existingUnit.getId().equals(unitDTO.getId()))
                        throw unitWithSymbolAlreadyExists(unit.getSymbol());
                });

        unit.setName(StringUtils.capitalize(unitDTO.getName().toLowerCase()));
        unit.setSymbol(unitDTO.getSymbol().toUpperCase());
        return unitRepository.save(unit);
    }

    Unit findUnitById(long unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(() -> unitWithIdNotFound(unitId));
    }

    private AlreadyExistsException unitWithNameAlreadyExists(String name) {
        return new AlreadyExistsException(translator.translate("common.alreadyExists.title"),
                translator.translate("unit.alreadyExists.name.message", new Object[]{name}));
    }

    private AlreadyExistsException unitWithSymbolAlreadyExists(String symbol) {
        return new AlreadyExistsException(translator.translate("common.alreadyExists.title"),
                translator.translate("unit.alreadyExists.symbol.message", new Object[]{symbol}));
    }

    private NotFoundException unitWithIdNotFound(long unitId) {
        return new NotFoundException(translator.translate("common.notFound.title"),
                translator.translate("unit.notFound.id.message", new Object[]{unitId}));
    }

}
