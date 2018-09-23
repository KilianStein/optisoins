package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.OrdonnanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ordonnance and its DTO OrdonnanceDTO.
 */
@Mapper(componentModel = "spring", uses = {MedecinMapper.class, PriseEnChargeMapper.class})
public interface OrdonnanceMapper extends EntityMapper<OrdonnanceDTO, Ordonnance> {

    @Mapping(source = "medecin.id", target = "medecinId")
    @Mapping(source = "priseEnCharge.id", target = "priseEnChargeId")
    OrdonnanceDTO toDto(Ordonnance ordonnance);

    @Mapping(source = "medecinId", target = "medecin")
    @Mapping(target = "demandeEntentePrealables", ignore = true)
    @Mapping(source = "priseEnChargeId", target = "priseEnCharge")
    Ordonnance toEntity(OrdonnanceDTO ordonnanceDTO);

    default Ordonnance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ordonnance ordonnance = new Ordonnance();
        ordonnance.setId(id);
        return ordonnance;
    }
}
