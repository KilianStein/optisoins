package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.CourrielDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Courriel.
 */
public interface CourrielService {

    /**
     * Save a courriel.
     *
     * @param courrielDTO the entity to save
     * @return the persisted entity
     */
    CourrielDTO save(CourrielDTO courrielDTO);

    /**
     * Get all the courriels.
     *
     * @return the list of entities
     */
    List<CourrielDTO> findAll();


    /**
     * Get the "id" courriel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CourrielDTO> findOne(Long id);

    /**
     * Delete the "id" courriel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
