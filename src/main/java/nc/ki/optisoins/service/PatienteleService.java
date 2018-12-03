package nc.ki.optisoins.service;

import nc.ki.optisoins.service.dto.PatienteleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Patientele.
 */
public interface PatienteleService {

    /**
     * Save a patientele.
     *
     * @param patienteleDTO the entity to save
     * @return the persisted entity
     */
    PatienteleDTO save(PatienteleDTO patienteleDTO);

    /**
     * Get all the patienteles.
     *
     * @return the list of entities
     */
    List<PatienteleDTO> findAll();


    /**
     * Get the "id" patientele.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PatienteleDTO> findOne(Long id);

    /**
     * Delete the "id" patientele.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
