package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.TelephoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Telephone and its DTO TelephoneDTO.
 */
@Mapper(componentModel = "spring", uses = {OrthophonisteMapper.class, PatientMapper.class, MedecinMapper.class})
public interface TelephoneMapper extends EntityMapper<TelephoneDTO, Telephone> {

    @Mapping(source = "orthophoniste.id", target = "orthophonisteId")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "medecin.id", target = "medecinId")
    TelephoneDTO toDto(Telephone telephone);

    @Mapping(source = "orthophonisteId", target = "orthophoniste")
    @Mapping(source = "patientId", target = "patient")
    @Mapping(source = "medecinId", target = "medecin")
    Telephone toEntity(TelephoneDTO telephoneDTO);

    default Telephone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Telephone telephone = new Telephone();
        telephone.setId(id);
        return telephone;
    }
}
