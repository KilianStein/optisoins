package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.TelephoneDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Telephone.
 */
public interface TelephoneService {

    /**
     * Save a telephone.
     *
     * @param telephoneDTO the entity to save
     * @return the persisted entity
     */
    TelephoneDTO save(TelephoneDTO telephoneDTO);

    /**
     * Get all the telephones.
     *
     * @return the list of entities
     */
    List<TelephoneDTO> findAll();


    /**
     * Get the "id" telephone.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TelephoneDTO> findOne(Long id);

    /**
     * Delete the "id" telephone.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
