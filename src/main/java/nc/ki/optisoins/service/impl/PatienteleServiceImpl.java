package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.PatienteleService;
import nc.ki.optisoins.domain.Patientele;
import nc.ki.optisoins.repository.PatienteleRepository;
import nc.ki.optisoins.service.dto.PatienteleDTO;
import nc.ki.optisoins.service.mapper.PatienteleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Patientele.
 */
@Service
@Transactional
public class PatienteleServiceImpl implements PatienteleService {

    private final Logger log = LoggerFactory.getLogger(PatienteleServiceImpl.class);

    private final PatienteleRepository patienteleRepository;

    private final PatienteleMapper patienteleMapper;

    public PatienteleServiceImpl(PatienteleRepository patienteleRepository, PatienteleMapper patienteleMapper) {
        this.patienteleRepository = patienteleRepository;
        this.patienteleMapper = patienteleMapper;
    }

    /**
     * Save a patientele.
     *
     * @param patienteleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PatienteleDTO save(PatienteleDTO patienteleDTO) {
        log.debug("Request to save Patientele : {}", patienteleDTO);

        Patientele patientele = patienteleMapper.toEntity(patienteleDTO);
        patientele = patienteleRepository.save(patientele);
        return patienteleMapper.toDto(patientele);
    }

    /**
     * Get all the patienteles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatienteleDTO> findAll() {
        log.debug("Request to get all Patienteles");
        return patienteleRepository.findAll().stream()
            .map(patienteleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one patientele by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PatienteleDTO> findOne(Long id) {
        log.debug("Request to get Patientele : {}", id);
        return patienteleRepository.findById(id)
            .map(patienteleMapper::toDto);
    }

    /**
     * Delete the patientele by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patientele : {}", id);
        patienteleRepository.deleteById(id);
    }
}
