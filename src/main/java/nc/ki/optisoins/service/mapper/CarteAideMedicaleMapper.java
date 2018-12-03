package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.CarteAideMedicaleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CarteAideMedicale and its DTO CarteAideMedicaleDTO.
 */
@Mapper(componentModel = "spring", uses = {AssureMapper.class})
public interface CarteAideMedicaleMapper extends EntityMapper<CarteAideMedicaleDTO, CarteAideMedicale> {

    @Mapping(source = "assure.id", target = "assureId")
    CarteAideMedicaleDTO toDto(CarteAideMedicale carteAideMedicale);

    @Mapping(source = "assureId", target = "assure")
    CarteAideMedicale toEntity(CarteAideMedicaleDTO carteAideMedicaleDTO);

    default CarteAideMedicale fromId(Long id) {
        if (id == null) {
            return null;
        }
        CarteAideMedicale carteAideMedicale = new CarteAideMedicale();
        carteAideMedicale.setId(id);
        return carteAideMedicale;
    }
}
