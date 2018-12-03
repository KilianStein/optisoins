package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.EtatRecapitulatifService;
import nc.ki.optisoins.domain.EtatRecapitulatif;
import nc.ki.optisoins.repository.EtatRecapitulatifRepository;
import nc.ki.optisoins.service.dto.EtatRecapitulatifDTO;
import nc.ki.optisoins.service.mapper.EtatRecapitulatifMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing EtatRecapitulatif.
 */
@Service
@Transactional
public class EtatRecapitulatifServiceImpl implements EtatRecapitulatifService {

    private final Logger log = LoggerFactory.getLogger(EtatRecapitulatifServiceImpl.class);

    private final EtatRecapitulatifRepository etatRecapitulatifRepository;

    private final EtatRecapitulatifMapper etatRecapitulatifMapper;

    public EtatRecapitulatifServiceImpl(EtatRecapitulatifRepository etatRecapitulatifRepository, EtatRecapitulatifMapper etatRecapitulatifMapper) {
        this.etatRecapitulatifRepository = etatRecapitulatifRepository;
        this.etatRecapitulatifMapper = etatRecapitulatifMapper;
    }

    /**
     * Save a etatRecapitulatif.
     *
     * @param etatRecapitulatifDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EtatRecapitulatifDTO save(EtatRecapitulatifDTO etatRecapitulatifDTO) {
        log.debug("Request to save EtatRecapitulatif : {}", etatRecapitulatifDTO);

        EtatRecapitulatif etatRecapitulatif = etatRecapitulatifMapper.toEntity(etatRecapitulatifDTO);
        etatRecapitulatif = etatRecapitulatifRepository.save(etatRecapitulatif);
        return etatRecapitulatifMapper.toDto(etatRecapitulatif);
    }

    /**
     * Get all the etatRecapitulatifs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EtatRecapitulatifDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EtatRecapitulatifs");
        return etatRecapitulatifRepository.findAll(pageable)
            .map(etatRecapitulatifMapper::toDto);
    }


    /**
     * Get one etatRecapitulatif by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EtatRecapitulatifDTO> findOne(Long id) {
        log.debug("Request to get EtatRecapitulatif : {}", id);
        return etatRecapitulatifRepository.findById(id)
            .map(etatRecapitulatifMapper::toDto);
    }

    /**
     * Delete the etatRecapitulatif by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EtatRecapitulatif : {}", id);
        etatRecapitulatifRepository.deleteById(id);
    }
}
