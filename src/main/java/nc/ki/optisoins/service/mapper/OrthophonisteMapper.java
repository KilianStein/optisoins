package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.OrthophonisteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Orthophoniste and its DTO OrthophonisteDTO.
 */
@Mapper(componentModel = "spring", uses = {CompteBancaireMapper.class, TelephoneMapper.class, AdresseMapper.class, CourrielMapper.class})
public interface OrthophonisteMapper extends EntityMapper<OrthophonisteDTO, Orthophoniste> {

    @Mapping(source = "compteBancaire.id", target = "compteBancaireId")
    OrthophonisteDTO toDto(Orthophoniste orthophoniste);

    @Mapping(source = "compteBancaireId", target = "compteBancaire")
    @Mapping(target = "patienteles", ignore = true)
    @Mapping(target = "etatsRecapitulatifs", ignore = true)
    Orthophoniste toEntity(OrthophonisteDTO orthophonisteDTO);

    default Orthophoniste fromId(Long id) {
        if (id == null) {
            return null;
        }
        Orthophoniste orthophoniste = new Orthophoniste();
        orthophoniste.setId(id);
        return orthophoniste;
    }
}
