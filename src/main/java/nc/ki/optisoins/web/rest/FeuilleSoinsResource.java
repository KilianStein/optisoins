package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.FeuilleSoinsService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.FeuilleSoinsDTO;
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
 * REST controller for managing FeuilleSoins.
 */
@RestController
@RequestMapping("/api")
public class FeuilleSoinsResource {

    private final Logger log = LoggerFactory.getLogger(FeuilleSoinsResource.class);

    private static final String ENTITY_NAME = "feuilleSoins";

    private final FeuilleSoinsService feuilleSoinsService;

    public FeuilleSoinsResource(FeuilleSoinsService feuilleSoinsService) {
        this.feuilleSoinsService = feuilleSoinsService;
    }

    /**
     * POST  /feuille-soins : Create a new feuilleSoins.
     *
     * @param feuilleSoinsDTO the feuilleSoinsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feuilleSoinsDTO, or with status 400 (Bad Request) if the feuilleSoins has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/feuille-soins")
    @Timed
    public ResponseEntity<FeuilleSoinsDTO> createFeuilleSoins(@RequestBody FeuilleSoinsDTO feuilleSoinsDTO) throws URISyntaxException {
        log.debug("REST request to save FeuilleSoins : {}", feuilleSoinsDTO);
        if (feuilleSoinsDTO.getId() != null) {
            throw new BadRequestAlertException("A new feuilleSoins cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeuilleSoinsDTO result = feuilleSoinsService.save(feuilleSoinsDTO);
        return ResponseEntity.created(new URI("/api/feuille-soins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feuille-soins : Updates an existing feuilleSoins.
     *
     * @param feuilleSoinsDTO the feuilleSoinsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated feuilleSoinsDTO,
     * or with status 400 (Bad Request) if the feuilleSoinsDTO is not valid,
     * or with status 500 (Internal Server Error) if the feuilleSoinsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/feuille-soins")
    @Timed
    public ResponseEntity<FeuilleSoinsDTO> updateFeuilleSoins(@RequestBody FeuilleSoinsDTO feuilleSoinsDTO) throws URISyntaxException {
        log.debug("REST request to update FeuilleSoins : {}", feuilleSoinsDTO);
        if (feuilleSoinsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeuilleSoinsDTO result = feuilleSoinsService.save(feuilleSoinsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, feuilleSoinsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feuille-soins : get all the feuilleSoins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of feuilleSoins in body
     */
    @GetMapping("/feuille-soins")
    @Timed
    public List<FeuilleSoinsDTO> getAllFeuilleSoins() {
        log.debug("REST request to get all FeuilleSoins");
        return feuilleSoinsService.findAll();
    }

    /**
     * GET  /feuille-soins/:id : get the "id" feuilleSoins.
     *
     * @param id the id of the feuilleSoinsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feuilleSoinsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/feuille-soins/{id}")
    @Timed
    public ResponseEntity<FeuilleSoinsDTO> getFeuilleSoins(@PathVariable Long id) {
        log.debug("REST request to get FeuilleSoins : {}", id);
        Optional<FeuilleSoinsDTO> feuilleSoinsDTO = feuilleSoinsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feuilleSoinsDTO);
    }

    /**
     * DELETE  /feuille-soins/:id : delete the "id" feuilleSoins.
     *
     * @param id the id of the feuilleSoinsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/feuille-soins/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeuilleSoins(@PathVariable Long id) {
        log.debug("REST request to delete FeuilleSoins : {}", id);
        feuilleSoinsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
