package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.AssureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Assure.
 */
public interface AssureService {

    /**
     * Save a assure.
     *
     * @param assureDTO the entity to save
     * @return the persisted entity
     */
    AssureDTO save(AssureDTO assureDTO);

    /**
     * Get all the assures.
     *
     * @return the list of entities
     */
    List<AssureDTO> findAll();


    /**
     * Get the "id" assure.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AssureDTO> findOne(Long id);

    /**
     * Delete the "id" assure.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
