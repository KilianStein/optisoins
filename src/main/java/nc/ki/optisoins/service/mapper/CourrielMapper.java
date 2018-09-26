package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.CourrielDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Courriel and its DTO CourrielDTO.
 */
@Mapper(componentModel = "spring", uses = {OrthophonisteMapper.class, PatientMapper.class, MedecinMapper.class})
public interface CourrielMapper extends EntityMapper<CourrielDTO, Courriel> {

    @Mapping(source = "orthophoniste.id", target = "orthophonisteId")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "medecin.id", target = "medecinId")
    CourrielDTO toDto(Courriel courriel);

    @Mapping(source = "orthophonisteId", target = "orthophoniste")
    @Mapping(source = "patientId", target = "patient")
    @Mapping(source = "medecinId", target = "medecin")
    Courriel toEntity(CourrielDTO courrielDTO);

    default Courriel fromId(Long id) {
        if (id == null) {
            return null;
        }
        Courriel courriel = new Courriel();
        courriel.setId(id);
        return courriel;
    }
}
