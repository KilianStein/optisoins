package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.DemandeEntentePrealableService;
import nc.ki.optisoins.domain.DemandeEntentePrealable;
import nc.ki.optisoins.repository.DemandeEntentePrealableRepository;
import nc.ki.optisoins.service.dto.DemandeEntentePrealableDTO;
import nc.ki.optisoins.service.mapper.DemandeEntentePrealableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DemandeEntentePrealable.
 */
@Service
@Transactional
public class DemandeEntentePrealableServiceImpl implements DemandeEntentePrealableService {

    private final Logger log = LoggerFactory.getLogger(DemandeEntentePrealableServiceImpl.class);

    private final DemandeEntentePrealableRepository demandeEntentePrealableRepository;

    private final DemandeEntentePrealableMapper demandeEntentePrealableMapper;

    public DemandeEntentePrealableServiceImpl(DemandeEntentePrealableRepository demandeEntentePrealableRepository, DemandeEntentePrealableMapper demandeEntentePrealableMapper) {
        this.demandeEntentePrealableRepository = demandeEntentePrealableRepository;
        this.demandeEntentePrealableMapper = demandeEntentePrealableMapper;
    }

    /**
     * Save a demandeEntentePrealable.
     *
     * @param demandeEntentePrealableDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DemandeEntentePrealableDTO save(DemandeEntentePrealableDTO demandeEntentePrealableDTO) {
        log.debug("Request to save DemandeEntentePrealable : {}", demandeEntentePrealableDTO);
        DemandeEntentePrealable demandeEntentePrealable = demandeEntentePrealableMapper.toEntity(demandeEntentePrealableDTO);
        demandeEntentePrealable = demandeEntentePrealableRepository.save(demandeEntentePrealable);
        return demandeEntentePrealableMapper.toDto(demandeEntentePrealable);
    }

    /**
     * Get all the demandeEntentePrealables.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DemandeEntentePrealableDTO> findAll() {
        log.debug("Request to get all DemandeEntentePrealables");
        return demandeEntentePrealableRepository.findAll().stream()
            .map(demandeEntentePrealableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one demandeEntentePrealable by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeEntentePrealableDTO> findOne(Long id) {
        log.debug("Request to get DemandeEntentePrealable : {}", id);
        return demandeEntentePrealableRepository.findById(id)
            .map(demandeEntentePrealableMapper::toDto);
    }

    /**
     * Delete the demandeEntentePrealable by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandeEntentePrealable : {}", id);
        demandeEntentePrealableRepository.deleteById(id);
    }
}
