package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.MutuelleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mutuelle and its DTO MutuelleDTO.
 */
@Mapper(componentModel = "spring", uses = {AssureMapper.class})
public interface MutuelleMapper extends EntityMapper<MutuelleDTO, Mutuelle> {

    @Mapping(source = "assure.id", target = "assureId")
    MutuelleDTO toDto(Mutuelle mutuelle);

    @Mapping(source = "assureId", target = "assure")
    Mutuelle toEntity(MutuelleDTO mutuelleDTO);

    default Mutuelle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mutuelle mutuelle = new Mutuelle();
        mutuelle.setId(id);
        return mutuelle;
    }
}
