package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.RemplacanteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Remplacante and its DTO RemplacanteDTO.
 */
@Mapper(componentModel = "spring", uses = {PatienteleMapper.class, OrthophonisteMapper.class})
public interface RemplacanteMapper extends EntityMapper<RemplacanteDTO, Remplacante> {

    @Mapping(source = "patientele.id", target = "patienteleId")
    @Mapping(source = "orthophoniste.id", target = "orthophonisteId")
    RemplacanteDTO toDto(Remplacante remplacante);

    @Mapping(source = "patienteleId", target = "patientele")
    @Mapping(source = "orthophonisteId", target = "orthophoniste")
    Remplacante toEntity(RemplacanteDTO remplacanteDTO);

    default Remplacante fromId(Long id) {
        if (id == null) {
            return null;
        }
        Remplacante remplacante = new Remplacante();
        remplacante.setId(id);
        return remplacante;
    }
}
