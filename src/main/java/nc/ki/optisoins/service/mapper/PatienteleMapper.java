package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.PatienteleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Patientele and its DTO PatienteleDTO.
 */
@Mapper(componentModel = "spring", uses = {OrthophonisteMapper.class})
public interface PatienteleMapper extends EntityMapper<PatienteleDTO, Patientele> {

    @Mapping(source = "orthophoniste.id", target = "orthophonisteId")
    @Mapping(source = "titulaire.id", target = "titulaireId")
    PatienteleDTO toDto(Patientele patientele);

    @Mapping(source = "orthophonisteId", target = "orthophoniste")
    @Mapping(source = "titulaireId", target = "titulaire")
    @Mapping(target = "patients", ignore = true)
    @Mapping(target = "remplacantes", ignore = true)
    Patientele toEntity(PatienteleDTO patienteleDTO);

    default Patientele fromId(Long id) {
        if (id == null) {
            return null;
        }
        Patientele patientele = new Patientele();
        patientele.setId(id);
        return patientele;
    }
}
