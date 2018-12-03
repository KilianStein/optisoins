package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.CarteAideMedicaleService;
import nc.ki.optisoins.domain.CarteAideMedicale;
import nc.ki.optisoins.repository.CarteAideMedicaleRepository;
import nc.ki.optisoins.service.dto.CarteAideMedicaleDTO;
import nc.ki.optisoins.service.mapper.CarteAideMedicaleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CarteAideMedicale.
 */
@Service
@Transactional
public class CarteAideMedicaleServiceImpl implements CarteAideMedicaleService {

    private final Logger log = LoggerFactory.getLogger(CarteAideMedicaleServiceImpl.class);

    private final CarteAideMedicaleRepository carteAideMedicaleRepository;

    private final CarteAideMedicaleMapper carteAideMedicaleMapper;

    public CarteAideMedicaleServiceImpl(CarteAideMedicaleRepository carteAideMedicaleRepository, CarteAideMedicaleMapper carteAideMedicaleMapper) {
        this.carteAideMedicaleRepository = carteAideMedicaleRepository;
        this.carteAideMedicaleMapper = carteAideMedicaleMapper;
    }

    /**
     * Save a carteAideMedicale.
     *
     * @param carteAideMedicaleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CarteAideMedicaleDTO save(CarteAideMedicaleDTO carteAideMedicaleDTO) {
        log.debug("Request to save CarteAideMedicale : {}", carteAideMedicaleDTO);

        CarteAideMedicale carteAideMedicale = carteAideMedicaleMapper.toEntity(carteAideMedicaleDTO);
        carteAideMedicale = carteAideMedicaleRepository.save(carteAideMedicale);
        return carteAideMedicaleMapper.toDto(carteAideMedicale);
    }

    /**
     * Get all the carteAideMedicales.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarteAideMedicaleDTO> findAll() {
        log.debug("Request to get all CarteAideMedicales");
        return carteAideMedicaleRepository.findAll().stream()
            .map(carteAideMedicaleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one carteAideMedicale by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CarteAideMedicaleDTO> findOne(Long id) {
        log.debug("Request to get CarteAideMedicale : {}", id);
        return carteAideMedicaleRepository.findById(id)
            .map(carteAideMedicaleMapper::toDto);
    }

    /**
     * Delete the carteAideMedicale by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarteAideMedicale : {}", id);
        carteAideMedicaleRepository.deleteById(id);
    }
}
