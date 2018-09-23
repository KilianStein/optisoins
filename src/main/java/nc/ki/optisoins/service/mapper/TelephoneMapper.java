package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.TelephoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Telephone and its DTO TelephoneDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TelephoneMapper extends EntityMapper<TelephoneDTO, Telephone> {



    default Telephone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Telephone telephone = new Telephone();
        telephone.setId(id);
        return telephone;
    }
}
