package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.OrthophonisteService;
import nc.ki.optisoins.domain.Orthophoniste;
import nc.ki.optisoins.repository.OrthophonisteRepository;
import nc.ki.optisoins.service.dto.OrthophonisteDTO;
import nc.ki.optisoins.service.mapper.OrthophonisteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Orthophoniste.
 */
@Service
@Transactional
public class OrthophonisteServiceImpl implements OrthophonisteService {

    private final Logger log = LoggerFactory.getLogger(OrthophonisteServiceImpl.class);

    private final OrthophonisteRepository orthophonisteRepository;

    private final OrthophonisteMapper orthophonisteMapper;

    public OrthophonisteServiceImpl(OrthophonisteRepository orthophonisteRepository, OrthophonisteMapper orthophonisteMapper) {
        this.orthophonisteRepository = orthophonisteRepository;
        this.orthophonisteMapper = orthophonisteMapper;
    }

    /**
     * Save a orthophoniste.
     *
     * @param orthophonisteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrthophonisteDTO save(OrthophonisteDTO orthophonisteDTO) {
        log.debug("Request to save Orthophoniste : {}", orthophonisteDTO);

        Orthophoniste orthophoniste = orthophonisteMapper.toEntity(orthophonisteDTO);
        orthophoniste = orthophonisteRepository.save(orthophoniste);
        return orthophonisteMapper.toDto(orthophoniste);
    }

    /**
     * Get all the orthophonistes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrthophonisteDTO> findAll() {
        log.debug("Request to get all Orthophonistes");
        return orthophonisteRepository.findAll().stream()
            .map(orthophonisteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orthophoniste by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrthophonisteDTO> findOne(Long id) {
        log.debug("Request to get Orthophoniste : {}", id);
        return orthophonisteRepository.findById(id)
            .map(orthophonisteMapper::toDto);
    }

    /**
     * Delete the orthophoniste by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Orthophoniste : {}", id);
        orthophonisteRepository.deleteById(id);
    }
}
