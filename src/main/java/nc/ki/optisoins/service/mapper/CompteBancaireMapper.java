package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.CompteBancaireDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CompteBancaire and its DTO CompteBancaireDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompteBancaireMapper extends EntityMapper<CompteBancaireDTO, CompteBancaire> {



    default CompteBancaire fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompteBancaire compteBancaire = new CompteBancaire();
        compteBancaire.setId(id);
        return compteBancaire;
    }
}
