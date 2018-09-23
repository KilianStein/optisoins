package nc.ki.optisoins.service.mapper;

import nc.ki.optisoins.domain.*;
import nc.ki.optisoins.service.dto.DemandeEntentePrealableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DemandeEntentePrealable and its DTO DemandeEntentePrealableDTO.
 */
@Mapper(componentModel = "spring", uses = {OrdonnanceMapper.class, AmoMapper.class})
public interface DemandeEntentePrealableMapper extends EntityMapper<DemandeEntentePrealableDTO, DemandeEntentePrealable> {

    @Mapping(source = "ordonnance.id", target = "ordonnanceId")
    @Mapping(source = "amo.id", target = "amoId")
    DemandeEntentePrealableDTO toDto(DemandeEntentePrealable demandeEntentePrealable);

    @Mapping(source = "ordonnanceId", target = "ordonnance")
    @Mapping(source = "amoId", target = "amo")
    DemandeEntentePrealable toEntity(DemandeEntentePrealableDTO demandeEntentePrealableDTO);

    default DemandeEntentePrealable fromId(Long id) {
        if (id == null) {
            return null;
        }
        DemandeEntentePrealable demandeEntentePrealable = new DemandeEntentePrealable();
        demandeEntentePrealable.setId(id);
        return demandeEntentePrealable;
    }
}
