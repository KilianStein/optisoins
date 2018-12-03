package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.MutuelleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Mutuelle.
 */
public interface MutuelleService {

    /**
     * Save a mutuelle.
     *
     * @param mutuelleDTO the entity to save
     * @return the persisted entity
     */
    MutuelleDTO save(MutuelleDTO mutuelleDTO);

    /**
     * Get all the mutuelles.
     *
     * @return the list of entities
     */
    List<MutuelleDTO> findAll();


    /**
     * Get the "id" mutuelle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MutuelleDTO> findOne(Long id);

    /**
     * Delete the "id" mutuelle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
