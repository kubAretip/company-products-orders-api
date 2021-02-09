package pl.kubaretip.cpo.api.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.kubaretip.cpo.api.domain.Unit;
import pl.kubaretip.cpo.api.dto.UnitDTO;
import pl.kubaretip.cpo.api.dto.mapper.UnitMapper;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.UnitRepository;
import pl.kubaretip.cpo.api.service.UnitService;
import pl.kubaretip.cpo.api.util.Translator;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;
    private final Translator translator;

    public UnitServiceImpl(UnitRepository unitRepository,
                           UnitMapper unitMapper,
                           Translator translator) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
        this.translator = translator;
    }

    @Override
    public UnitDTO getUnitById(long unitId) {
        return unitMapper.mapToDTO(findUnitById(unitId));
    }

    @Override
    public UnitDTO createUnit(UnitDTO unitDTO) {

        if (unitRepository.existsByName(unitDTO.getName()))
            throw unitWithNameAlreadyExists(unitDTO.getName());

        if (unitRepository.existsBySymbol(unitDTO.getSymbol()))
            throw unitWithSymbolAlreadyExists(unitDTO.getSymbol());

        unitDTO.setId(null);
        unitDTO.setName(StringUtils.capitalize(unitDTO.getName().toLowerCase()));
        unitDTO.setSymbol(unitDTO.getSymbol().toUpperCase());

        var unit = unitMapper.mapToEntity(unitDTO);
        unitRepository.save(unit);
        return unitMapper.mapToDTO(unit);
    }


    @Override
    public void markUnitAsDeleted(long unitId) {
        var unit = findUnitById(unitId);
        unit.setDeleted(true);
        unitRepository.save(unit);
    }

    @Override
    public UnitDTO modifyUnit(UnitDTO unitDTO) {

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
        unitRepository.save(unit);
        return unitMapper.mapToDTO(unit);
    }

    Unit findUnitById(long unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(() -> unitWithIdNotFound(unitId));
    }

    private AlreadyExistsException unitWithNameAlreadyExists(String name) {
        return new AlreadyExistsException(translator.translate("exception.common.alreadyExists.title"),
                translator.translate("exception.unit.name.unique.message", new Object[]{name}));
    }

    private AlreadyExistsException unitWithSymbolAlreadyExists(String symbol) {
        return new AlreadyExistsException(translator.translate("exception.common.alreadyExists.title"),
                translator.translate("exception.unit.symbol.unique.message", new Object[]{symbol}));
    }

    private NotFoundException unitWithIdNotFound(long unitId) {
        return new NotFoundException(translator.translate("exception.common.notFound.title"),
                translator.translate("exception.unit.byIdNotFound.message", new Object[]{unitId}));
    }

}
