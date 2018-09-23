package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.AdresseDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Adresse.
 */
public interface AdresseService {

    /**
     * Save a adresse.
     *
     * @param adresseDTO the entity to save
     * @return the persisted entity
     */
    AdresseDTO save(AdresseDTO adresseDTO);

    /**
     * Get all the adresses.
     *
     * @return the list of entities
     */
    List<AdresseDTO> findAll();


    /**
     * Get the "id" adresse.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AdresseDTO> findOne(Long id);

    /**
     * Delete the "id" adresse.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
