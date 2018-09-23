package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.CourrielDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Courriel and its DTO CourrielDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourrielMapper extends EntityMapper<CourrielDTO, Courriel> {



    default Courriel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Courriel courriel = new Courriel();
        courriel.setId(id);
        return courriel;
    }
}
