package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.AssureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Assure and its DTO AssureDTO.
 */
@Mapper(componentModel = "spring", uses = {CourrielMapper.class, AdresseMapper.class, TelephoneMapper.class})
public interface AssureMapper extends EntityMapper<AssureDTO, Assure> {

    @Mapping(source = "courriel.id", target = "courrielId")
    @Mapping(source = "adresse.id", target = "adresseId")
    @Mapping(source = "telephone.id", target = "telephoneId")
    AssureDTO toDto(Assure assure);

    @Mapping(source = "courrielId", target = "courriel")
    @Mapping(source = "adresseId", target = "adresse")
    @Mapping(source = "telephoneId", target = "telephone")
    @Mapping(target = "cartesAideMedicales", ignore = true)
    @Mapping(target = "mutuelles", ignore = true)
    Assure toEntity(AssureDTO assureDTO);

    default Assure fromId(Long id) {
        if (id == null) {
            return null;
        }
        Assure assure = new Assure();
        assure.setId(id);
        return assure;
    }
}
