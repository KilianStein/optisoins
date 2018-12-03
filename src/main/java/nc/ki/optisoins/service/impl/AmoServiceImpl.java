package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.AmoService;
import nc.ki.optisoins.domain.Amo;
import nc.ki.optisoins.repository.AmoRepository;
import nc.ki.optisoins.service.dto.AmoDTO;
import nc.ki.optisoins.service.mapper.AmoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Amo.
 */
@Service
@Transactional
public class AmoServiceImpl implements AmoService {

    private final Logger log = LoggerFactory.getLogger(AmoServiceImpl.class);

    private final AmoRepository amoRepository;

    private final AmoMapper amoMapper;

    public AmoServiceImpl(AmoRepository amoRepository, AmoMapper amoMapper) {
        this.amoRepository = amoRepository;
        this.amoMapper = amoMapper;
    }

    /**
     * Save a amo.
     *
     * @param amoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AmoDTO save(AmoDTO amoDTO) {
        log.debug("Request to save Amo : {}", amoDTO);

        Amo amo = amoMapper.toEntity(amoDTO);
        amo = amoRepository.save(amo);
        return amoMapper.toDto(amo);
    }

    /**
     * Get all the amos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AmoDTO> findAll() {
        log.debug("Request to get all Amos");
        return amoRepository.findAll().stream()
            .map(amoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one amo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AmoDTO> findOne(Long id) {
        log.debug("Request to get Amo : {}", id);
        return amoRepository.findById(id)
            .map(amoMapper::toDto);
    }

    /**
     * Delete the amo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Amo : {}", id);
        amoRepository.deleteById(id);
    }
}
