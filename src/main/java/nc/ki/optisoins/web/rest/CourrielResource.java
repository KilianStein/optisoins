package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.CourrielService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.CourrielDTO;
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
 * REST controller for managing Courriel.
 */
@RestController
@RequestMapping("/api")
public class CourrielResource {

    private final Logger log = LoggerFactory.getLogger(CourrielResource.class);

    private static final String ENTITY_NAME = "courriel";

    private final CourrielService courrielService;

    public CourrielResource(CourrielService courrielService) {
        this.courrielService = courrielService;
    }

    /**
     * POST  /courriels : Create a new courriel.
     *
     * @param courrielDTO the courrielDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courrielDTO, or with status 400 (Bad Request) if the courriel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/courriels")
    @Timed
    public ResponseEntity<CourrielDTO> createCourriel(@Valid @RequestBody CourrielDTO courrielDTO) throws URISyntaxException {
        log.debug("REST request to save Courriel : {}", courrielDTO);
        if (courrielDTO.getId() != null) {
            throw new BadRequestAlertException("A new courriel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourrielDTO result = courrielService.save(courrielDTO);
        return ResponseEntity.created(new URI("/api/courriels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courriels : Updates an existing courriel.
     *
     * @param courrielDTO the courrielDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courrielDTO,
     * or with status 400 (Bad Request) if the courrielDTO is not valid,
     * or with status 500 (Internal Server Error) if the courrielDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/courriels")
    @Timed
    public ResponseEntity<CourrielDTO> updateCourriel(@Valid @RequestBody CourrielDTO courrielDTO) throws URISyntaxException {
        log.debug("REST request to update Courriel : {}", courrielDTO);
        if (courrielDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourrielDTO result = courrielService.save(courrielDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, courrielDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courriels : get all the courriels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of courriels in body
     */
    @GetMapping("/courriels")
    @Timed
    public List<CourrielDTO> getAllCourriels() {
        log.debug("REST request to get all Courriels");
        return courrielService.findAll();
    }

    /**
     * GET  /courriels/:id : get the "id" courriel.
     *
     * @param id the id of the courrielDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courrielDTO, or with status 404 (Not Found)
     */
    @GetMapping("/courriels/{id}")
    @Timed
    public ResponseEntity<CourrielDTO> getCourriel(@PathVariable Long id) {
        log.debug("REST request to get Courriel : {}", id);
        Optional<CourrielDTO> courrielDTO = courrielService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courrielDTO);
    }

    /**
     * DELETE  /courriels/:id : delete the "id" courriel.
     *
     * @param id the id of the courrielDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/courriels/{id}")
    @Timed
    public ResponseEntity<Void> deleteCourriel(@PathVariable Long id) {
        log.debug("REST request to delete Courriel : {}", id);
        courrielService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
