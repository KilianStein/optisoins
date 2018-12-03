package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.SeanceService;
import nc.ki.optisoins.domain.Seance;
import nc.ki.optisoins.repository.SeanceRepository;
import nc.ki.optisoins.service.dto.SeanceDTO;
import nc.ki.optisoins.service.mapper.SeanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Seance.
 */
@Service
@Transactional
public class SeanceServiceImpl implements SeanceService {

    private final Logger log = LoggerFactory.getLogger(SeanceServiceImpl.class);

    private final SeanceRepository seanceRepository;

    private final SeanceMapper seanceMapper;

    public SeanceServiceImpl(SeanceRepository seanceRepository, SeanceMapper seanceMapper) {
        this.seanceRepository = seanceRepository;
        this.seanceMapper = seanceMapper;
    }

    /**
     * Save a seance.
     *
     * @param seanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeanceDTO save(SeanceDTO seanceDTO) {
        log.debug("Request to save Seance : {}", seanceDTO);

        Seance seance = seanceMapper.toEntity(seanceDTO);
        seance = seanceRepository.save(seance);
        return seanceMapper.toDto(seance);
    }

    /**
     * Get all the seances.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SeanceDTO> findAll() {
        log.debug("Request to get all Seances");
        return seanceRepository.findAll().stream()
            .map(seanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one seance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SeanceDTO> findOne(Long id) {
        log.debug("Request to get Seance : {}", id);
        return seanceRepository.findById(id)
            .map(seanceMapper::toDto);
    }

    /**
     * Delete the seance by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seance : {}", id);
        seanceRepository.deleteById(id);
    }
}
