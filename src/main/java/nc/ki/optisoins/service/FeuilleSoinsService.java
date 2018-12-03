package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.FeuilleSoinsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing FeuilleSoins.
 */
public interface FeuilleSoinsService {

    /**
     * Save a feuilleSoins.
     *
     * @param feuilleSoinsDTO the entity to save
     * @return the persisted entity
     */
    FeuilleSoinsDTO save(FeuilleSoinsDTO feuilleSoinsDTO);

    /**
     * Get all the feuilleSoins.
     *
     * @return the list of entities
     */
    List<FeuilleSoinsDTO> findAll();


    /**
     * Get the "id" feuilleSoins.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FeuilleSoinsDTO> findOne(Long id);

    /**
     * Delete the "id" feuilleSoins.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
