package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.MedecinDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Medecin and its DTO MedecinDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedecinMapper extends EntityMapper<MedecinDTO, Medecin> {


    @Mapping(target = "adresses", ignore = true)
    @Mapping(target = "courriels", ignore = true)
    @Mapping(target = "telephones", ignore = true)
    Medecin toEntity(MedecinDTO medecinDTO);

    default Medecin fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medecin medecin = new Medecin();
        medecin.setId(id);
        return medecin;
    }
}
