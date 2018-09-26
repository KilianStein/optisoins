package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.DemandeEntentePrealableDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing DemandeEntentePrealable.
 */
public interface DemandeEntentePrealableService {

    /**
     * Save a demandeEntentePrealable.
     *
     * @param demandeEntentePrealableDTO the entity to save
     * @return the persisted entity
     */
    DemandeEntentePrealableDTO save(DemandeEntentePrealableDTO demandeEntentePrealableDTO);

    /**
     * Get all the demandeEntentePrealables.
     *
     * @return the list of entities
     */
    List<DemandeEntentePrealableDTO> findAll();


    /**
     * Get the "id" demandeEntentePrealable.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DemandeEntentePrealableDTO> findOne(Long id);

    /**
     * Delete the "id" demandeEntentePrealable.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
