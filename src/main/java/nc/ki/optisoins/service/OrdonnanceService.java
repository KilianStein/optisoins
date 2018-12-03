package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.OrdonnanceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Ordonnance.
 */
public interface OrdonnanceService {

    /**
     * Save a ordonnance.
     *
     * @param ordonnanceDTO the entity to save
     * @return the persisted entity
     */
    OrdonnanceDTO save(OrdonnanceDTO ordonnanceDTO);

    /**
     * Get all the ordonnances.
     *
     * @return the list of entities
     */
    List<OrdonnanceDTO> findAll();


    /**
     * Get the "id" ordonnance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrdonnanceDTO> findOne(Long id);

    /**
     * Delete the "id" ordonnance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
