package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.CourrielService;
import nc.ki.optisoins.domain.Courriel;
import nc.ki.optisoins.repository.CourrielRepository;
import nc.ki.optisoins.service.dto.CourrielDTO;
import nc.ki.optisoins.service.mapper.CourrielMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Courriel.
 */
@Service
@Transactional
public class CourrielServiceImpl implements CourrielService {

    private final Logger log = LoggerFactory.getLogger(CourrielServiceImpl.class);

    private final CourrielRepository courrielRepository;

    private final CourrielMapper courrielMapper;

    public CourrielServiceImpl(CourrielRepository courrielRepository, CourrielMapper courrielMapper) {
        this.courrielRepository = courrielRepository;
        this.courrielMapper = courrielMapper;
    }

    /**
     * Save a courriel.
     *
     * @param courrielDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CourrielDTO save(CourrielDTO courrielDTO) {
        log.debug("Request to save Courriel : {}", courrielDTO);
        Courriel courriel = courrielMapper.toEntity(courrielDTO);
        courriel = courrielRepository.save(courriel);
        return courrielMapper.toDto(courriel);
    }

    /**
     * Get all the courriels.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourrielDTO> findAll() {
        log.debug("Request to get all Courriels");
        return courrielRepository.findAll().stream()
            .map(courrielMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one courriel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CourrielDTO> findOne(Long id) {
        log.debug("Request to get Courriel : {}", id);
        return courrielRepository.findById(id)
            .map(courrielMapper::toDto);
    }

    /**
     * Delete the courriel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Courriel : {}", id);
        courrielRepository.deleteById(id);
    }
}
