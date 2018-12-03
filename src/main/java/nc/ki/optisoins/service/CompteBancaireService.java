package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.CompteBancaireDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CompteBancaire.
 */
public interface CompteBancaireService {

    /**
     * Save a compteBancaire.
     *
     * @param compteBancaireDTO the entity to save
     * @return the persisted entity
     */
    CompteBancaireDTO save(CompteBancaireDTO compteBancaireDTO);

    /**
     * Get all the compteBancaires.
     *
     * @return the list of entities
     */
    List<CompteBancaireDTO> findAll();


    /**
     * Get the "id" compteBancaire.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CompteBancaireDTO> findOne(Long id);

    /**
     * Delete the "id" compteBancaire.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
