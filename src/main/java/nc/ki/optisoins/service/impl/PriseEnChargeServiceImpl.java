package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.PriseEnChargeService;
import nc.ki.optisoins.domain.PriseEnCharge;
import nc.ki.optisoins.repository.PriseEnChargeRepository;
import nc.ki.optisoins.service.dto.PriseEnChargeDTO;
import nc.ki.optisoins.service.mapper.PriseEnChargeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PriseEnCharge.
 */
@Service
@Transactional
public class PriseEnChargeServiceImpl implements PriseEnChargeService {

    private final Logger log = LoggerFactory.getLogger(PriseEnChargeServiceImpl.class);

    private final PriseEnChargeRepository priseEnChargeRepository;

    private final PriseEnChargeMapper priseEnChargeMapper;

    public PriseEnChargeServiceImpl(PriseEnChargeRepository priseEnChargeRepository, PriseEnChargeMapper priseEnChargeMapper) {
        this.priseEnChargeRepository = priseEnChargeRepository;
        this.priseEnChargeMapper = priseEnChargeMapper;
    }

    /**
     * Save a priseEnCharge.
     *
     * @param priseEnChargeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PriseEnChargeDTO save(PriseEnChargeDTO priseEnChargeDTO) {
        log.debug("Request to save PriseEnCharge : {}", priseEnChargeDTO);

        PriseEnCharge priseEnCharge = priseEnChargeMapper.toEntity(priseEnChargeDTO);
        priseEnCharge = priseEnChargeRepository.save(priseEnCharge);
        return priseEnChargeMapper.toDto(priseEnCharge);
    }

    /**
     * Get all the priseEnCharges.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PriseEnChargeDTO> findAll() {
        log.debug("Request to get all PriseEnCharges");
        return priseEnChargeRepository.findAll().stream()
            .map(priseEnChargeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one priseEnCharge by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PriseEnChargeDTO> findOne(Long id) {
        log.debug("Request to get PriseEnCharge : {}", id);
        return priseEnChargeRepository.findById(id)
            .map(priseEnChargeMapper::toDto);
    }

    /**
     * Delete the priseEnCharge by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriseEnCharge : {}", id);
        priseEnChargeRepository.deleteById(id);
    }
}
