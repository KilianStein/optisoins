package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.MedecinDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Medecin.
 */
public interface MedecinService {

    /**
     * Save a medecin.
     *
     * @param medecinDTO the entity to save
     * @return the persisted entity
     */
    MedecinDTO save(MedecinDTO medecinDTO);

    /**
     * Get all the medecins.
     *
     * @return the list of entities
     */
    List<MedecinDTO> findAll();

    /**
     * Get all the Medecin with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<MedecinDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" medecin.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MedecinDTO> findOne(Long id);

    /**
     * Delete the "id" medecin.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
