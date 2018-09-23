package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.OrdonnanceService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.OrdonnanceDTO;
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
 * REST controller for managing Ordonnance.
 */
@RestController
@RequestMapping("/api")
public class OrdonnanceResource {

    private final Logger log = LoggerFactory.getLogger(OrdonnanceResource.class);

    private static final String ENTITY_NAME = "ordonnance";

    private final OrdonnanceService ordonnanceService;

    public OrdonnanceResource(OrdonnanceService ordonnanceService) {
        this.ordonnanceService = ordonnanceService;
    }

    /**
     * POST  /ordonnances : Create a new ordonnance.
     *
     * @param ordonnanceDTO the ordonnanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordonnanceDTO, or with status 400 (Bad Request) if the ordonnance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordonnances")
    @Timed
    public ResponseEntity<OrdonnanceDTO> createOrdonnance(@Valid @RequestBody OrdonnanceDTO ordonnanceDTO) throws URISyntaxException {
        log.debug("REST request to save Ordonnance : {}", ordonnanceDTO);
        if (ordonnanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new ordonnance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrdonnanceDTO result = ordonnanceService.save(ordonnanceDTO);
        return ResponseEntity.created(new URI("/api/ordonnances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordonnances : Updates an existing ordonnance.
     *
     * @param ordonnanceDTO the ordonnanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordonnanceDTO,
     * or with status 400 (Bad Request) if the ordonnanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the ordonnanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordonnances")
    @Timed
    public ResponseEntity<OrdonnanceDTO> updateOrdonnance(@Valid @RequestBody OrdonnanceDTO ordonnanceDTO) throws URISyntaxException {
        log.debug("REST request to update Ordonnance : {}", ordonnanceDTO);
        if (ordonnanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrdonnanceDTO result = ordonnanceService.save(ordonnanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordonnanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordonnances : get all the ordonnances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ordonnances in body
     */
    @GetMapping("/ordonnances")
    @Timed
    public List<OrdonnanceDTO> getAllOrdonnances() {
        log.debug("REST request to get all Ordonnances");
        return ordonnanceService.findAll();
    }

    /**
     * GET  /ordonnances/:id : get the "id" ordonnance.
     *
     * @param id the id of the ordonnanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordonnanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ordonnances/{id}")
    @Timed
    public ResponseEntity<OrdonnanceDTO> getOrdonnance(@PathVariable Long id) {
        log.debug("REST request to get Ordonnance : {}", id);
        Optional<OrdonnanceDTO> ordonnanceDTO = ordonnanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordonnanceDTO);
    }

    /**
     * DELETE  /ordonnances/:id : delete the "id" ordonnance.
     *
     * @param id the id of the ordonnanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordonnances/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdonnance(@PathVariable Long id) {
        log.debug("REST request to delete Ordonnance : {}", id);
        ordonnanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
