package nc.ki.optisoins.service.impl;

import nc.ki.optisoins.service.MedecinService;
import nc.ki.optisoins.domain.Medecin;
import nc.ki.optisoins.repository.MedecinRepository;
import nc.ki.optisoins.service.dto.MedecinDTO;
import nc.ki.optisoins.service.mapper.MedecinMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Medecin.
 */
@Service
@Transactional
public class MedecinServiceImpl implements MedecinService {

    private final Logger log = LoggerFactory.getLogger(MedecinServiceImpl.class);

    private final MedecinRepository medecinRepository;

    private final MedecinMapper medecinMapper;

    public MedecinServiceImpl(MedecinRepository medecinRepository, MedecinMapper medecinMapper) {
        this.medecinRepository = medecinRepository;
        this.medecinMapper = medecinMapper;
    }

    /**
     * Save a medecin.
     *
     * @param medecinDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MedecinDTO save(MedecinDTO medecinDTO) {
        log.debug("Request to save Medecin : {}", medecinDTO);

        Medecin medecin = medecinMapper.toEntity(medecinDTO);
        medecin = medecinRepository.save(medecin);
        return medecinMapper.toDto(medecin);
    }

    /**
     * Get all the medecins.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MedecinDTO> findAll() {
        log.debug("Request to get all Medecins");
        return medecinRepository.findAll().stream()
            .map(medecinMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one medecin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedecinDTO> findOne(Long id) {
        log.debug("Request to get Medecin : {}", id);
        return medecinRepository.findById(id)
            .map(medecinMapper::toDto);
    }

    /**
     * Delete the medecin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medecin : {}", id);
        medecinRepository.deleteById(id);
    }
}
