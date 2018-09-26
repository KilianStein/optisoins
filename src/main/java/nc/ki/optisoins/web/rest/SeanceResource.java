package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.SeanceService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.SeanceDTO;
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
 * REST controller for managing Seance.
 */
@RestController
@RequestMapping("/api")
public class SeanceResource {

    private final Logger log = LoggerFactory.getLogger(SeanceResource.class);

    private static final String ENTITY_NAME = "seance";

    private final SeanceService seanceService;

    public SeanceResource(SeanceService seanceService) {
        this.seanceService = seanceService;
    }

    /**
     * POST  /seances : Create a new seance.
     *
     * @param seanceDTO the seanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new seanceDTO, or with status 400 (Bad Request) if the seance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seances")
    @Timed
    public ResponseEntity<SeanceDTO> createSeance(@Valid @RequestBody SeanceDTO seanceDTO) throws URISyntaxException {
        log.debug("REST request to save Seance : {}", seanceDTO);
        if (seanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new seance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeanceDTO result = seanceService.save(seanceDTO);
        return ResponseEntity.created(new URI("/api/seances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seances : Updates an existing seance.
     *
     * @param seanceDTO the seanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated seanceDTO,
     * or with status 400 (Bad Request) if the seanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the seanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seances")
    @Timed
    public ResponseEntity<SeanceDTO> updateSeance(@Valid @RequestBody SeanceDTO seanceDTO) throws URISyntaxException {
        log.debug("REST request to update Seance : {}", seanceDTO);
        if (seanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SeanceDTO result = seanceService.save(seanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, seanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seances : get all the seances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seances in body
     */
    @GetMapping("/seances")
    @Timed
    public List<SeanceDTO> getAllSeances() {
        log.debug("REST request to get all Seances");
        return seanceService.findAll();
    }

    /**
     * GET  /seances/:id : get the "id" seance.
     *
     * @param id the id of the seanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the seanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/seances/{id}")
    @Timed
    public ResponseEntity<SeanceDTO> getSeance(@PathVariable Long id) {
        log.debug("REST request to get Seance : {}", id);
        Optional<SeanceDTO> seanceDTO = seanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seanceDTO);
    }

    /**
     * DELETE  /seances/:id : delete the "id" seance.
     *
     * @param id the id of the seanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seances/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeance(@PathVariable Long id) {
        log.debug("REST request to delete Seance : {}", id);
        seanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
