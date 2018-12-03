package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.DemandeEntentePrealableService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.DemandeEntentePrealableDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DemandeEntentePrealable.
 */
@RestController
@RequestMapping("/api")
public class DemandeEntentePrealableResource {

    private final Logger log = LoggerFactory.getLogger(DemandeEntentePrealableResource.class);

    private static final String ENTITY_NAME = "demandeEntentePrealable";

    private final DemandeEntentePrealableService demandeEntentePrealableService;

    public DemandeEntentePrealableResource(DemandeEntentePrealableService demandeEntentePrealableService) {
        this.demandeEntentePrealableService = demandeEntentePrealableService;
    }

    /**
     * POST  /demande-entente-prealables : Create a new demandeEntentePrealable.
     *
     * @param demandeEntentePrealableDTO the demandeEntentePrealableDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new demandeEntentePrealableDTO, or with status 400 (Bad Request) if the demandeEntentePrealable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/demande-entente-prealables")
    @Timed
    public ResponseEntity<DemandeEntentePrealableDTO> createDemandeEntentePrealable(@Valid @RequestBody DemandeEntentePrealableDTO demandeEntentePrealableDTO) throws URISyntaxException {
        log.debug("REST request to save DemandeEntentePrealable : {}", demandeEntentePrealableDTO);
        if (demandeEntentePrealableDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandeEntentePrealable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeEntentePrealableDTO result = demandeEntentePrealableService.save(demandeEntentePrealableDTO);
        return ResponseEntity.created(new URI("/api/demande-entente-prealables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /demande-entente-prealables : Updates an existing demandeEntentePrealable.
     *
     * @param demandeEntentePrealableDTO the demandeEntentePrealableDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated demandeEntentePrealableDTO,
     * or with status 400 (Bad Request) if the demandeEntentePrealableDTO is not valid,
     * or with status 500 (Internal Server Error) if the demandeEntentePrealableDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/demande-entente-prealables")
    @Timed
    public ResponseEntity<DemandeEntentePrealableDTO> updateDemandeEntentePrealable(@Valid @RequestBody DemandeEntentePrealableDTO demandeEntentePrealableDTO) throws URISyntaxException {
        log.debug("REST request to update DemandeEntentePrealable : {}", demandeEntentePrealableDTO);
        if (demandeEntentePrealableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DemandeEntentePrealableDTO result = demandeEntentePrealableService.save(demandeEntentePrealableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, demandeEntentePrealableDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /demande-entente-prealables : get all the demandeEntentePrealables.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of demandeEntentePrealables in body
     */
    @GetMapping("/demande-entente-prealables")
    @Timed
    public List<DemandeEntentePrealableDTO> getAllDemandeEntentePrealables() {
        log.debug("REST request to get all DemandeEntentePrealables");
        return demandeEntentePrealableService.findAll();
    }

    /**
     * GET  /demande-entente-prealables/:id : get the "id" demandeEntentePrealable.
     *
     * @param id the id of the demandeEntentePrealableDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the demandeEntentePrealableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/demande-entente-prealables/{id}")
    @Timed
    public ResponseEntity<DemandeEntentePrealableDTO> getDemandeEntentePrealable(@PathVariable Long id) {
        log.debug("REST request to get DemandeEntentePrealable : {}", id);
        Optional<DemandeEntentePrealableDTO> demandeEntentePrealableDTO = demandeEntentePrealableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeEntentePrealableDTO);
    }

    /**
     * DELETE  /demande-entente-prealables/:id : delete the "id" demandeEntentePrealable.
     *
     * @param id the id of the demandeEntentePrealableDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/demande-entente-prealables/{id}")
    @Timed
    public ResponseEntity<Void> deleteDemandeEntentePrealable(@PathVariable Long id) {
        log.debug("REST request to delete DemandeEntentePrealable : {}", id);
        demandeEntentePrealableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
