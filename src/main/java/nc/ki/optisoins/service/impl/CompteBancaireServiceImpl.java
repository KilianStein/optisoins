package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.CompteBancaireService;
import nc.ki.optisoins.domain.CompteBancaire;
import nc.ki.optisoins.repository.CompteBancaireRepository;
import nc.ki.optisoins.service.dto.CompteBancaireDTO;
import nc.ki.optisoins.service.mapper.CompteBancaireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CompteBancaire.
 */
@Service
@Transactional
public class CompteBancaireServiceImpl implements CompteBancaireService {

    private final Logger log = LoggerFactory.getLogger(CompteBancaireServiceImpl.class);

    private final CompteBancaireRepository compteBancaireRepository;

    private final CompteBancaireMapper compteBancaireMapper;

    public CompteBancaireServiceImpl(CompteBancaireRepository compteBancaireRepository, CompteBancaireMapper compteBancaireMapper) {
        this.compteBancaireRepository = compteBancaireRepository;
        this.compteBancaireMapper = compteBancaireMapper;
    }

    /**
     * Save a compteBancaire.
     *
     * @param compteBancaireDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompteBancaireDTO save(CompteBancaireDTO compteBancaireDTO) {
        log.debug("Request to save CompteBancaire : {}", compteBancaireDTO);

        CompteBancaire compteBancaire = compteBancaireMapper.toEntity(compteBancaireDTO);
        compteBancaire = compteBancaireRepository.save(compteBancaire);
        return compteBancaireMapper.toDto(compteBancaire);
    }

    /**
     * Get all the compteBancaires.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompteBancaireDTO> findAll() {
        log.debug("Request to get all CompteBancaires");
        return compteBancaireRepository.findAll().stream()
            .map(compteBancaireMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one compteBancaire by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompteBancaireDTO> findOne(Long id) {
        log.debug("Request to get CompteBancaire : {}", id);
        return compteBancaireRepository.findById(id)
            .map(compteBancaireMapper::toDto);
    }

    /**
     * Delete the compteBancaire by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompteBancaire : {}", id);
        compteBancaireRepository.deleteById(id);
    }
}
