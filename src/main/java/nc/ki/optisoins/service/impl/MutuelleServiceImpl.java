package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.MutuelleService;
import nc.ki.optisoins.domain.Mutuelle;
import nc.ki.optisoins.repository.MutuelleRepository;
import nc.ki.optisoins.service.dto.MutuelleDTO;
import nc.ki.optisoins.service.mapper.MutuelleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Mutuelle.
 */
@Service
@Transactional
public class MutuelleServiceImpl implements MutuelleService {

    private final Logger log = LoggerFactory.getLogger(MutuelleServiceImpl.class);

    private final MutuelleRepository mutuelleRepository;

    private final MutuelleMapper mutuelleMapper;

    public MutuelleServiceImpl(MutuelleRepository mutuelleRepository, MutuelleMapper mutuelleMapper) {
        this.mutuelleRepository = mutuelleRepository;
        this.mutuelleMapper = mutuelleMapper;
    }

    /**
     * Save a mutuelle.
     *
     * @param mutuelleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MutuelleDTO save(MutuelleDTO mutuelleDTO) {
        log.debug("Request to save Mutuelle : {}", mutuelleDTO);

        Mutuelle mutuelle = mutuelleMapper.toEntity(mutuelleDTO);
        mutuelle = mutuelleRepository.save(mutuelle);
        return mutuelleMapper.toDto(mutuelle);
    }

    /**
     * Get all the mutuelles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MutuelleDTO> findAll() {
        log.debug("Request to get all Mutuelles");
        return mutuelleRepository.findAll().stream()
            .map(mutuelleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one mutuelle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MutuelleDTO> findOne(Long id) {
        log.debug("Request to get Mutuelle : {}", id);
        return mutuelleRepository.findById(id)
            .map(mutuelleMapper::toDto);
    }

    /**
     * Delete the mutuelle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mutuelle : {}", id);
        mutuelleRepository.deleteById(id);
    }
}
