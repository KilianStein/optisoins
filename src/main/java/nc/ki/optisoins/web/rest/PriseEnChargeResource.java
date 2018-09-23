package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.PriseEnChargeService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.PriseEnChargeDTO;
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
 * REST controller for managing PriseEnCharge.
 */
@RestController
@RequestMapping("/api")
public class PriseEnChargeResource {

    private final Logger log = LoggerFactory.getLogger(PriseEnChargeResource.class);

    private static final String ENTITY_NAME = "priseEnCharge";

    private final PriseEnChargeService priseEnChargeService;

    public PriseEnChargeResource(PriseEnChargeService priseEnChargeService) {
        this.priseEnChargeService = priseEnChargeService;
    }

    /**
     * POST  /prise-en-charges : Create a new priseEnCharge.
     *
     * @param priseEnChargeDTO the priseEnChargeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priseEnChargeDTO, or with status 400 (Bad Request) if the priseEnCharge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prise-en-charges")
    @Timed
    public ResponseEntity<PriseEnChargeDTO> createPriseEnCharge(@Valid @RequestBody PriseEnChargeDTO priseEnChargeDTO) throws URISyntaxException {
        log.debug("REST request to save PriseEnCharge : {}", priseEnChargeDTO);
        if (priseEnChargeDTO.getId() != null) {
            throw new BadRequestAlertException("A new priseEnCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriseEnChargeDTO result = priseEnChargeService.save(priseEnChargeDTO);
        return ResponseEntity.created(new URI("/api/prise-en-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prise-en-charges : Updates an existing priseEnCharge.
     *
     * @param priseEnChargeDTO the priseEnChargeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated priseEnChargeDTO,
     * or with status 400 (Bad Request) if the priseEnChargeDTO is not valid,
     * or with status 500 (Internal Server Error) if the priseEnChargeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prise-en-charges")
    @Timed
    public ResponseEntity<PriseEnChargeDTO> updatePriseEnCharge(@Valid @RequestBody PriseEnChargeDTO priseEnChargeDTO) throws URISyntaxException {
        log.debug("REST request to update PriseEnCharge : {}", priseEnChargeDTO);
        if (priseEnChargeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PriseEnChargeDTO result = priseEnChargeService.save(priseEnChargeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, priseEnChargeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prise-en-charges : get all the priseEnCharges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of priseEnCharges in body
     */
    @GetMapping("/prise-en-charges")
    @Timed
    public List<PriseEnChargeDTO> getAllPriseEnCharges() {
        log.debug("REST request to get all PriseEnCharges");
        return priseEnChargeService.findAll();
    }

    /**
     * GET  /prise-en-charges/:id : get the "id" priseEnCharge.
     *
     * @param id the id of the priseEnChargeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priseEnChargeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prise-en-charges/{id}")
    @Timed
    public ResponseEntity<PriseEnChargeDTO> getPriseEnCharge(@PathVariable Long id) {
        log.debug("REST request to get PriseEnCharge : {}", id);
        Optional<PriseEnChargeDTO> priseEnChargeDTO = priseEnChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priseEnChargeDTO);
    }

    /**
     * DELETE  /prise-en-charges/:id : delete the "id" priseEnCharge.
     *
     * @param id the id of the priseEnChargeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prise-en-charges/{id}")
    @Timed
    public ResponseEntity<Void> deletePriseEnCharge(@PathVariable Long id) {
        log.debug("REST request to delete PriseEnCharge : {}", id);
        priseEnChargeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
