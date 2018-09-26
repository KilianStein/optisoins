package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.AdresseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Adresse and its DTO AdresseDTO.
 */
@Mapper(componentModel = "spring", uses = {OrthophonisteMapper.class, PatientMapper.class, MedecinMapper.class})
public interface AdresseMapper extends EntityMapper<AdresseDTO, Adresse> {

    @Mapping(source = "orthophoniste.id", target = "orthophonisteId")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "medecin.id", target = "medecinId")
    AdresseDTO toDto(Adresse adresse);

    @Mapping(source = "orthophonisteId", target = "orthophoniste")
    @Mapping(source = "patientId", target = "patient")
    @Mapping(source = "medecinId", target = "medecin")
    Adresse toEntity(AdresseDTO adresseDTO);

    default Adresse fromId(Long id) {
        if (id == null) {
            return null;
        }
        Adresse adresse = new Adresse();
        adresse.setId(id);
        return adresse;
    }
}
