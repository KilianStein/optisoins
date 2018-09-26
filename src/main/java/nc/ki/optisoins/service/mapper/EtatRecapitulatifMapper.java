package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.EtatRecapitulatifDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EtatRecapitulatif and its DTO EtatRecapitulatifDTO.
 */
@Mapper(componentModel = "spring", uses = {OrthophonisteMapper.class})
public interface EtatRecapitulatifMapper extends EntityMapper<EtatRecapitulatifDTO, EtatRecapitulatif> {

    @Mapping(source = "orthophoniste.id", target = "orthophonisteId")
    EtatRecapitulatifDTO toDto(EtatRecapitulatif etatRecapitulatif);

    @Mapping(source = "orthophonisteId", target = "orthophoniste")
    @Mapping(target = "feuilleSoins", ignore = true)
    EtatRecapitulatif toEntity(EtatRecapitulatifDTO etatRecapitulatifDTO);

    default EtatRecapitulatif fromId(Long id) {
        if (id == null) {
            return null;
        }
        EtatRecapitulatif etatRecapitulatif = new EtatRecapitulatif();
        etatRecapitulatif.setId(id);
        return etatRecapitulatif;
    }
}
