package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.CarteAideMedicaleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CarteAideMedicale.
 */
public interface CarteAideMedicaleService {

    /**
     * Save a carteAideMedicale.
     *
     * @param carteAideMedicaleDTO the entity to save
     * @return the persisted entity
     */
    CarteAideMedicaleDTO save(CarteAideMedicaleDTO carteAideMedicaleDTO);

    /**
     * Get all the carteAideMedicales.
     *
     * @return the list of entities
     */
    List<CarteAideMedicaleDTO> findAll();


    /**
     * Get the "id" carteAideMedicale.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CarteAideMedicaleDTO> findOne(Long id);

    /**
     * Delete the "id" carteAideMedicale.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
