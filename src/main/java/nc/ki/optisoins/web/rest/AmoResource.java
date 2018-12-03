package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.AmoService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.AmoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Amo.
 */
@RestController
@RequestMapping("/api")
public class AmoResource {

    private final Logger log = LoggerFactory.getLogger(AmoResource.class);

    private static final String ENTITY_NAME = "amo";

    private final AmoService amoService;

    public AmoResource(AmoService amoService) {
        this.amoService = amoService;
    }

    /**
     * POST  /amos : Create a new amo.
     *
     * @param amoDTO the amoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amoDTO, or with status 400 (Bad Request) if the amo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amos")
    @Timed
    public ResponseEntity<AmoDTO> createAmo(@RequestBody AmoDTO amoDTO) throws URISyntaxException {
        log.debug("REST request to save Amo : {}", amoDTO);
        if (amoDTO.getId() != null) {
            throw new BadRequestAlertException("A new amo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmoDTO result = amoService.save(amoDTO);
        return ResponseEntity.created(new URI("/api/amos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amos : Updates an existing amo.
     *
     * @param amoDTO the amoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amoDTO,
     * or with status 400 (Bad Request) if the amoDTO is not valid,
     * or with status 500 (Internal Server Error) if the amoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amos")
    @Timed
    public ResponseEntity<AmoDTO> updateAmo(@RequestBody AmoDTO amoDTO) throws URISyntaxException {
        log.debug("REST request to update Amo : {}", amoDTO);
        if (amoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmoDTO result = amoService.save(amoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amos : get all the amos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of amos in body
     */
    @GetMapping("/amos")
    @Timed
    public List<AmoDTO> getAllAmos() {
        log.debug("REST request to get all Amos");
        return amoService.findAll();
    }

    /**
     * GET  /amos/:id : get the "id" amo.
     *
     * @param id the id of the amoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/amos/{id}")
    @Timed
    public ResponseEntity<AmoDTO> getAmo(@PathVariable Long id) {
        log.debug("REST request to get Amo : {}", id);
        Optional<AmoDTO> amoDTO = amoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amoDTO);
    }

    /**
     * DELETE  /amos/:id : delete the "id" amo.
     *
     * @param id the id of the amoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmo(@PathVariable Long id) {
        log.debug("REST request to delete Amo : {}", id);
        amoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
