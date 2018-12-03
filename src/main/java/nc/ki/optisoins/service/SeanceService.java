package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.SeanceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Seance.
 */
public interface SeanceService {

    /**
     * Save a seance.
     *
     * @param seanceDTO the entity to save
     * @return the persisted entity
     */
    SeanceDTO save(SeanceDTO seanceDTO);

    /**
     * Get all the seances.
     *
     * @return the list of entities
     */
    List<SeanceDTO> findAll();


    /**
     * Get the "id" seance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SeanceDTO> findOne(Long id);

    /**
     * Delete the "id" seance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
