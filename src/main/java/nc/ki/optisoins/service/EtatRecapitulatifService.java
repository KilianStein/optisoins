package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.EtatRecapitulatifDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EtatRecapitulatif.
 */
public interface EtatRecapitulatifService {

    /**
     * Save a etatRecapitulatif.
     *
     * @param etatRecapitulatifDTO the entity to save
     * @return the persisted entity
     */
    EtatRecapitulatifDTO save(EtatRecapitulatifDTO etatRecapitulatifDTO);

    /**
     * Get all the etatRecapitulatifs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EtatRecapitulatifDTO> findAll(Pageable pageable);


    /**
     * Get the "id" etatRecapitulatif.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EtatRecapitulatifDTO> findOne(Long id);

    /**
     * Delete the "id" etatRecapitulatif.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
