package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.PriseEnChargeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PriseEnCharge.
 */
public interface PriseEnChargeService {

    /**
     * Save a priseEnCharge.
     *
     * @param priseEnChargeDTO the entity to save
     * @return the persisted entity
     */
    PriseEnChargeDTO save(PriseEnChargeDTO priseEnChargeDTO);

    /**
     * Get all the priseEnCharges.
     *
     * @return the list of entities
     */
    List<PriseEnChargeDTO> findAll();


    /**
     * Get the "id" priseEnCharge.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PriseEnChargeDTO> findOne(Long id);

    /**
     * Delete the "id" priseEnCharge.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
