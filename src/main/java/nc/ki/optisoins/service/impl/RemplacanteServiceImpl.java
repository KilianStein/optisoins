package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.RemplacanteService;
import nc.ki.optisoins.domain.Remplacante;
import nc.ki.optisoins.repository.RemplacanteRepository;
import nc.ki.optisoins.service.dto.RemplacanteDTO;
import nc.ki.optisoins.service.mapper.RemplacanteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Remplacante.
 */
@Service
@Transactional
public class RemplacanteServiceImpl implements RemplacanteService {

    private final Logger log = LoggerFactory.getLogger(RemplacanteServiceImpl.class);

    private final RemplacanteRepository remplacanteRepository;

    private final RemplacanteMapper remplacanteMapper;

    public RemplacanteServiceImpl(RemplacanteRepository remplacanteRepository, RemplacanteMapper remplacanteMapper) {
        this.remplacanteRepository = remplacanteRepository;
        this.remplacanteMapper = remplacanteMapper;
    }

    /**
     * Save a remplacante.
     *
     * @param remplacanteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RemplacanteDTO save(RemplacanteDTO remplacanteDTO) {
        log.debug("Request to save Remplacante : {}", remplacanteDTO);

        Remplacante remplacante = remplacanteMapper.toEntity(remplacanteDTO);
        remplacante = remplacanteRepository.save(remplacante);
        return remplacanteMapper.toDto(remplacante);
    }

    /**
     * Get all the remplacantes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RemplacanteDTO> findAll() {
        log.debug("Request to get all Remplacantes");
        return remplacanteRepository.findAll().stream()
            .map(remplacanteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one remplacante by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RemplacanteDTO> findOne(Long id) {
        log.debug("Request to get Remplacante : {}", id);
        return remplacanteRepository.findById(id)
            .map(remplacanteMapper::toDto);
    }

    /**
     * Delete the remplacante by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Remplacante : {}", id);
        remplacanteRepository.deleteById(id);
    }
}
