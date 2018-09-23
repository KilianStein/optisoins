package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.PriseEnChargeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PriseEnCharge and its DTO PriseEnChargeDTO.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface PriseEnChargeMapper extends EntityMapper<PriseEnChargeDTO, PriseEnCharge> {

    @Mapping(source = "patient.id", target = "patientId")
    PriseEnChargeDTO toDto(PriseEnCharge priseEnCharge);

    @Mapping(source = "patientId", target = "patient")
    @Mapping(target = "ordonnances", ignore = true)
    @Mapping(target = "seances", ignore = true)
    @Mapping(target = "feuillesSoins", ignore = true)
    PriseEnCharge toEntity(PriseEnChargeDTO priseEnChargeDTO);

    default PriseEnCharge fromId(Long id) {
        if (id == null) {
            return null;
        }
        PriseEnCharge priseEnCharge = new PriseEnCharge();
        priseEnCharge.setId(id);
        return priseEnCharge;
    }
}
