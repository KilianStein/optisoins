package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.SeanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Seance and its DTO SeanceDTO.
 */
@Mapper(componentModel = "spring", uses = {OrthophonisteMapper.class, PriseEnChargeMapper.class, FeuilleSoinsMapper.class})
public interface SeanceMapper extends EntityMapper<SeanceDTO, Seance> {

    @Mapping(source = "orthophoniste.id", target = "orthophonisteId")
    @Mapping(source = "priseEnCharge.id", target = "priseEnChargeId")
    @Mapping(source = "feuilleSoins.id", target = "feuilleSoinsId")
    SeanceDTO toDto(Seance seance);

    @Mapping(source = "orthophonisteId", target = "orthophoniste")
    @Mapping(source = "priseEnChargeId", target = "priseEnCharge")
    @Mapping(source = "feuilleSoinsId", target = "feuilleSoins")
    Seance toEntity(SeanceDTO seanceDTO);

    default Seance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seance seance = new Seance();
        seance.setId(id);
        return seance;
    }
}
