package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.EtatRecapitulatifService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.web.rest.util.PaginationUtil;
import nc.ki.optisoins.service.dto.EtatRecapitulatifDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EtatRecapitulatif.
 */
@RestController
@RequestMapping("/api")
public class EtatRecapitulatifResource {

    private final Logger log = LoggerFactory.getLogger(EtatRecapitulatifResource.class);

    private static final String ENTITY_NAME = "etatRecapitulatif";

    private final EtatRecapitulatifService etatRecapitulatifService;

    public EtatRecapitulatifResource(EtatRecapitulatifService etatRecapitulatifService) {
        this.etatRecapitulatifService = etatRecapitulatifService;
    }

    /**
     * POST  /etat-recapitulatifs : Create a new etatRecapitulatif.
     *
     * @param etatRecapitulatifDTO the etatRecapitulatifDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etatRecapitulatifDTO, or with status 400 (Bad Request) if the etatRecapitulatif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etat-recapitulatifs")
    @Timed
    public ResponseEntity<EtatRecapitulatifDTO> createEtatRecapitulatif(@RequestBody EtatRecapitulatifDTO etatRecapitulatifDTO) throws URISyntaxException {
        log.debug("REST request to save EtatRecapitulatif : {}", etatRecapitulatifDTO);
        if (etatRecapitulatifDTO.getId() != null) {
            throw new BadRequestAlertException("A new etatRecapitulatif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtatRecapitulatifDTO result = etatRecapitulatifService.save(etatRecapitulatifDTO);
        return ResponseEntity.created(new URI("/api/etat-recapitulatifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etat-recapitulatifs : Updates an existing etatRecapitulatif.
     *
     * @param etatRecapitulatifDTO the etatRecapitulatifDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etatRecapitulatifDTO,
     * or with status 400 (Bad Request) if the etatRecapitulatifDTO is not valid,
     * or with status 500 (Internal Server Error) if the etatRecapitulatifDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etat-recapitulatifs")
    @Timed
    public ResponseEntity<EtatRecapitulatifDTO> updateEtatRecapitulatif(@RequestBody EtatRecapitulatifDTO etatRecapitulatifDTO) throws URISyntaxException {
        log.debug("REST request to update EtatRecapitulatif : {}", etatRecapitulatifDTO);
        if (etatRecapitulatifDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtatRecapitulatifDTO result = etatRecapitulatifService.save(etatRecapitulatifDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etatRecapitulatifDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etat-recapitulatifs : get all the etatRecapitulatifs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of etatRecapitulatifs in body
     */
    @GetMapping("/etat-recapitulatifs")
    @Timed
    public ResponseEntity<List<EtatRecapitulatifDTO>> getAllEtatRecapitulatifs(Pageable pageable) {
        log.debug("REST request to get a page of EtatRecapitulatifs");
        Page<EtatRecapitulatifDTO> page = etatRecapitulatifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/etat-recapitulatifs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /etat-recapitulatifs/:id : get the "id" etatRecapitulatif.
     *
     * @param id the id of the etatRecapitulatifDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etatRecapitulatifDTO, or with status 404 (Not Found)
     */
    @GetMapping("/etat-recapitulatifs/{id}")
    @Timed
    public ResponseEntity<EtatRecapitulatifDTO> getEtatRecapitulatif(@PathVariable Long id) {
        log.debug("REST request to get EtatRecapitulatif : {}", id);
        Optional<EtatRecapitulatifDTO> etatRecapitulatifDTO = etatRecapitulatifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etatRecapitulatifDTO);
    }

    /**
     * DELETE  /etat-recapitulatifs/:id : delete the "id" etatRecapitulatif.
     *
     * @param id the id of the etatRecapitulatifDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etat-recapitulatifs/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtatRecapitulatif(@PathVariable Long id) {
        log.debug("REST request to delete EtatRecapitulatif : {}", id);
        etatRecapitulatifService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
