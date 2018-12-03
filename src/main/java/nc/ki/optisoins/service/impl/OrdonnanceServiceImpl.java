package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.OrdonnanceService;
import nc.ki.optisoins.domain.Ordonnance;
import nc.ki.optisoins.repository.OrdonnanceRepository;
import nc.ki.optisoins.service.dto.OrdonnanceDTO;
import nc.ki.optisoins.service.mapper.OrdonnanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Ordonnance.
 */
@Service
@Transactional
public class OrdonnanceServiceImpl implements OrdonnanceService {

    private final Logger log = LoggerFactory.getLogger(OrdonnanceServiceImpl.class);

    private final OrdonnanceRepository ordonnanceRepository;

    private final OrdonnanceMapper ordonnanceMapper;

    public OrdonnanceServiceImpl(OrdonnanceRepository ordonnanceRepository, OrdonnanceMapper ordonnanceMapper) {
        this.ordonnanceRepository = ordonnanceRepository;
        this.ordonnanceMapper = ordonnanceMapper;
    }

    /**
     * Save a ordonnance.
     *
     * @param ordonnanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrdonnanceDTO save(OrdonnanceDTO ordonnanceDTO) {
        log.debug("Request to save Ordonnance : {}", ordonnanceDTO);

        Ordonnance ordonnance = ordonnanceMapper.toEntity(ordonnanceDTO);
        ordonnance = ordonnanceRepository.save(ordonnance);
        return ordonnanceMapper.toDto(ordonnance);
    }

    /**
     * Get all the ordonnances.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrdonnanceDTO> findAll() {
        log.debug("Request to get all Ordonnances");
        return ordonnanceRepository.findAll().stream()
            .map(ordonnanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ordonnance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrdonnanceDTO> findOne(Long id) {
        log.debug("Request to get Ordonnance : {}", id);
        return ordonnanceRepository.findById(id)
            .map(ordonnanceMapper::toDto);
    }

    /**
     * Delete the ordonnance by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ordonnance : {}", id);
        ordonnanceRepository.deleteById(id);
    }
}
