package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.MedecinDTO;

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
