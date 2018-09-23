package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.FeuilleSoinsService;
import nc.ki.optisoins.domain.FeuilleSoins;
import nc.ki.optisoins.repository.FeuilleSoinsRepository;
import nc.ki.optisoins.service.dto.FeuilleSoinsDTO;
import nc.ki.optisoins.service.mapper.FeuilleSoinsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FeuilleSoins.
 */
@Service
@Transactional
public class FeuilleSoinsServiceImpl implements FeuilleSoinsService {

    private final Logger log = LoggerFactory.getLogger(FeuilleSoinsServiceImpl.class);

    private final FeuilleSoinsRepository feuilleSoinsRepository;

    private final FeuilleSoinsMapper feuilleSoinsMapper;

    public FeuilleSoinsServiceImpl(FeuilleSoinsRepository feuilleSoinsRepository, FeuilleSoinsMapper feuilleSoinsMapper) {
        this.feuilleSoinsRepository = feuilleSoinsRepository;
        this.feuilleSoinsMapper = feuilleSoinsMapper;
    }

    /**
     * Save a feuilleSoins.
     *
     * @param feuilleSoinsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FeuilleSoinsDTO save(FeuilleSoinsDTO feuilleSoinsDTO) {
        log.debug("Request to save FeuilleSoins : {}", feuilleSoinsDTO);
        FeuilleSoins feuilleSoins = feuilleSoinsMapper.toEntity(feuilleSoinsDTO);
        feuilleSoins = feuilleSoinsRepository.save(feuilleSoins);
        return feuilleSoinsMapper.toDto(feuilleSoins);
    }

    /**
     * Get all the feuilleSoins.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FeuilleSoinsDTO> findAll() {
        log.debug("Request to get all FeuilleSoins");
        return feuilleSoinsRepository.findAll().stream()
            .map(feuilleSoinsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one feuilleSoins by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FeuilleSoinsDTO> findOne(Long id) {
        log.debug("Request to get FeuilleSoins : {}", id);
        return feuilleSoinsRepository.findById(id)
            .map(feuilleSoinsMapper::toDto);
    }

    /**
     * Delete the feuilleSoins by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeuilleSoins : {}", id);
        feuilleSoinsRepository.deleteById(id);
    }
}
