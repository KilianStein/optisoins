package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.PatientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Patient and its DTO PatientDTO.
 */
@Mapper(componentModel = "spring", uses = {PatienteleMapper.class, AssureMapper.class})
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {

    @Mapping(source = "patientele.id", target = "patienteleId")
    @Mapping(source = "assure.id", target = "assureId")
    PatientDTO toDto(Patient patient);

    @Mapping(source = "patienteleId", target = "patientele")
    @Mapping(source = "assureId", target = "assure")
    @Mapping(target = "prisesEnCharges", ignore = true)
    @Mapping(target = "telephones", ignore = true)
    @Mapping(target = "adresses", ignore = true)
    @Mapping(target = "courriels", ignore = true)
    Patient toEntity(PatientDTO patientDTO);

    default Patient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setId(id);
        return patient;
    }
}
