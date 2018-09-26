package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.AssureService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.AssureDTO;
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
 * REST controller for managing Assure.
 */
@RestController
@RequestMapping("/api")
public class AssureResource {

    private final Logger log = LoggerFactory.getLogger(AssureResource.class);

    private static final String ENTITY_NAME = "assure";

    private final AssureService assureService;

    public AssureResource(AssureService assureService) {
        this.assureService = assureService;
    }

    /**
     * POST  /assures : Create a new assure.
     *
     * @param assureDTO the assureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assureDTO, or with status 400 (Bad Request) if the assure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assures")
    @Timed
    public ResponseEntity<AssureDTO> createAssure(@Valid @RequestBody AssureDTO assureDTO) throws URISyntaxException {
        log.debug("REST request to save Assure : {}", assureDTO);
        if (assureDTO.getId() != null) {
            throw new BadRequestAlertException("A new assure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssureDTO result = assureService.save(assureDTO);
        return ResponseEntity.created(new URI("/api/assures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assures : Updates an existing assure.
     *
     * @param assureDTO the assureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assureDTO,
     * or with status 400 (Bad Request) if the assureDTO is not valid,
     * or with status 500 (Internal Server Error) if the assureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assures")
    @Timed
    public ResponseEntity<AssureDTO> updateAssure(@Valid @RequestBody AssureDTO assureDTO) throws URISyntaxException {
        log.debug("REST request to update Assure : {}", assureDTO);
        if (assureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssureDTO result = assureService.save(assureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assures : get all the assures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of assures in body
     */
    @GetMapping("/assures")
    @Timed
    public List<AssureDTO> getAllAssures() {
        log.debug("REST request to get all Assures");
        return assureService.findAll();
    }

    /**
     * GET  /assures/:id : get the "id" assure.
     *
     * @param id the id of the assureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/assures/{id}")
    @Timed
    public ResponseEntity<AssureDTO> getAssure(@PathVariable Long id) {
        log.debug("REST request to get Assure : {}", id);
        Optional<AssureDTO> assureDTO = assureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assureDTO);
    }

    /**
     * DELETE  /assures/:id : delete the "id" assure.
     *
     * @param id the id of the assureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assures/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssure(@PathVariable Long id) {
        log.debug("REST request to delete Assure : {}", id);
        assureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
