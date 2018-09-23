package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.AmoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Amo.
 */
public interface AmoService {

    /**
     * Save a amo.
     *
     * @param amoDTO the entity to save
     * @return the persisted entity
     */
    AmoDTO save(AmoDTO amoDTO);

    /**
     * Get all the amos.
     *
     * @return the list of entities
     */
    List<AmoDTO> findAll();


    /**
     * Get the "id" amo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AmoDTO> findOne(Long id);

    /**
     * Delete the "id" amo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
