package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.AmoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Amo and its DTO AmoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AmoMapper extends EntityMapper<AmoDTO, Amo> {



    default Amo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Amo amo = new Amo();
        amo.setId(id);
        return amo;
    }
}
