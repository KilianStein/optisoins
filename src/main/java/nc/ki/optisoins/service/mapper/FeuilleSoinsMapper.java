package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.FeuilleSoinsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FeuilleSoins and its DTO FeuilleSoinsDTO.
 */
@Mapper(componentModel = "spring", uses = {PriseEnChargeMapper.class, EtatRecapitulatifMapper.class})
public interface FeuilleSoinsMapper extends EntityMapper<FeuilleSoinsDTO, FeuilleSoins> {

    @Mapping(source = "priseEnCharge.id", target = "priseEnChargeId")
    @Mapping(source = "etatRecapitulatif.id", target = "etatRecapitulatifId")
    FeuilleSoinsDTO toDto(FeuilleSoins feuilleSoins);

    @Mapping(source = "priseEnChargeId", target = "priseEnCharge")
    @Mapping(target = "actes", ignore = true)
    @Mapping(source = "etatRecapitulatifId", target = "etatRecapitulatif")
    FeuilleSoins toEntity(FeuilleSoinsDTO feuilleSoinsDTO);

    default FeuilleSoins fromId(Long id) {
        if (id == null) {
            return null;
        }
        FeuilleSoins feuilleSoins = new FeuilleSoins();
        feuilleSoins.setId(id);
        return feuilleSoins;
    }
}
