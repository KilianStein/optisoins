package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.OrthophonisteDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Orthophoniste.
 */
public interface OrthophonisteService {

    /**
     * Save a orthophoniste.
     *
     * @param orthophonisteDTO the entity to save
     * @return the persisted entity
     */
    OrthophonisteDTO save(OrthophonisteDTO orthophonisteDTO);

    /**
     * Get all the orthophonistes.
     *
     * @return the list of entities
     */
    List<OrthophonisteDTO> findAll();


    /**
     * Get the "id" orthophoniste.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrthophonisteDTO> findOne(Long id);

    /**
     * Delete the "id" orthophoniste.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
