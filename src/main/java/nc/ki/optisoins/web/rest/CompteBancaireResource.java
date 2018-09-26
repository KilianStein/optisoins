package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.CompteBancaireService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.CompteBancaireDTO;
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
 * REST controller for managing CompteBancaire.
 */
@RestController
@RequestMapping("/api")
public class CompteBancaireResource {

    private final Logger log = LoggerFactory.getLogger(CompteBancaireResource.class);

    private static final String ENTITY_NAME = "compteBancaire";

    private final CompteBancaireService compteBancaireService;

    public CompteBancaireResource(CompteBancaireService compteBancaireService) {
        this.compteBancaireService = compteBancaireService;
    }

    /**
     * POST  /compte-bancaires : Create a new compteBancaire.
     *
     * @param compteBancaireDTO the compteBancaireDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compteBancaireDTO, or with status 400 (Bad Request) if the compteBancaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compte-bancaires")
    @Timed
    public ResponseEntity<CompteBancaireDTO> createCompteBancaire(@Valid @RequestBody CompteBancaireDTO compteBancaireDTO) throws URISyntaxException {
        log.debug("REST request to save CompteBancaire : {}", compteBancaireDTO);
        if (compteBancaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new compteBancaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompteBancaireDTO result = compteBancaireService.save(compteBancaireDTO);
        return ResponseEntity.created(new URI("/api/compte-bancaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compte-bancaires : Updates an existing compteBancaire.
     *
     * @param compteBancaireDTO the compteBancaireDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compteBancaireDTO,
     * or with status 400 (Bad Request) if the compteBancaireDTO is not valid,
     * or with status 500 (Internal Server Error) if the compteBancaireDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compte-bancaires")
    @Timed
    public ResponseEntity<CompteBancaireDTO> updateCompteBancaire(@Valid @RequestBody CompteBancaireDTO compteBancaireDTO) throws URISyntaxException {
        log.debug("REST request to update CompteBancaire : {}", compteBancaireDTO);
        if (compteBancaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompteBancaireDTO result = compteBancaireService.save(compteBancaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteBancaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compte-bancaires : get all the compteBancaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of compteBancaires in body
     */
    @GetMapping("/compte-bancaires")
    @Timed
    public List<CompteBancaireDTO> getAllCompteBancaires() {
        log.debug("REST request to get all CompteBancaires");
        return compteBancaireService.findAll();
    }

    /**
     * GET  /compte-bancaires/:id : get the "id" compteBancaire.
     *
     * @param id the id of the compteBancaireDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compteBancaireDTO, or with status 404 (Not Found)
     */
    @GetMapping("/compte-bancaires/{id}")
    @Timed
    public ResponseEntity<CompteBancaireDTO> getCompteBancaire(@PathVariable Long id) {
        log.debug("REST request to get CompteBancaire : {}", id);
        Optional<CompteBancaireDTO> compteBancaireDTO = compteBancaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compteBancaireDTO);
    }

    /**
     * DELETE  /compte-bancaires/:id : delete the "id" compteBancaire.
     *
     * @param id the id of the compteBancaireDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compte-bancaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompteBancaire(@PathVariable Long id) {
        log.debug("REST request to delete CompteBancaire : {}", id);
        compteBancaireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
