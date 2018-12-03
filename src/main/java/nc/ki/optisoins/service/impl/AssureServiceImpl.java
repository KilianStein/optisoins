package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.AssureService;
import nc.ki.optisoins.domain.Assure;
import nc.ki.optisoins.repository.AssureRepository;
import nc.ki.optisoins.service.dto.AssureDTO;
import nc.ki.optisoins.service.mapper.AssureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Assure.
 */
@Service
@Transactional
public class AssureServiceImpl implements AssureService {

    private final Logger log = LoggerFactory.getLogger(AssureServiceImpl.class);

    private final AssureRepository assureRepository;

    private final AssureMapper assureMapper;

    public AssureServiceImpl(AssureRepository assureRepository, AssureMapper assureMapper) {
        this.assureRepository = assureRepository;
        this.assureMapper = assureMapper;
    }

    /**
     * Save a assure.
     *
     * @param assureDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AssureDTO save(AssureDTO assureDTO) {
        log.debug("Request to save Assure : {}", assureDTO);

        Assure assure = assureMapper.toEntity(assureDTO);
        assure = assureRepository.save(assure);
        return assureMapper.toDto(assure);
    }

    /**
     * Get all the assures.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AssureDTO> findAll() {
        log.debug("Request to get all Assures");
        return assureRepository.findAll().stream()
            .map(assureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one assure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AssureDTO> findOne(Long id) {
        log.debug("Request to get Assure : {}", id);
        return assureRepository.findById(id)
            .map(assureMapper::toDto);
    }

    /**
     * Delete the assure by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assure : {}", id);
        assureRepository.deleteById(id);
    }
}
