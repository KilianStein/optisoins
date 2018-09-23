package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.RemplacanteDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Remplacante.
 */
public interface RemplacanteService {

    /**
     * Save a remplacante.
     *
     * @param remplacanteDTO the entity to save
     * @return the persisted entity
     */
    RemplacanteDTO save(RemplacanteDTO remplacanteDTO);

    /**
     * Get all the remplacantes.
     *
     * @return the list of entities
     */
    List<RemplacanteDTO> findAll();


    /**
     * Get the "id" remplacante.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RemplacanteDTO> findOne(Long id);

    /**
     * Delete the "id" remplacante.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
