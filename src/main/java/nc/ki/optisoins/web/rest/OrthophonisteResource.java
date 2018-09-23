package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.OrthophonisteService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.OrthophonisteDTO;
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
 * REST controller for managing Orthophoniste.
 */
@RestController
@RequestMapping("/api")
public class OrthophonisteResource {

    private final Logger log = LoggerFactory.getLogger(OrthophonisteResource.class);

    private static final String ENTITY_NAME = "orthophoniste";

    private final OrthophonisteService orthophonisteService;

    public OrthophonisteResource(OrthophonisteService orthophonisteService) {
        this.orthophonisteService = orthophonisteService;
    }

    /**
     * POST  /orthophonistes : Create a new orthophoniste.
     *
     * @param orthophonisteDTO the orthophonisteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orthophonisteDTO, or with status 400 (Bad Request) if the orthophoniste has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orthophonistes")
    @Timed
    public ResponseEntity<OrthophonisteDTO> createOrthophoniste(@Valid @RequestBody OrthophonisteDTO orthophonisteDTO) throws URISyntaxException {
        log.debug("REST request to save Orthophoniste : {}", orthophonisteDTO);
        if (orthophonisteDTO.getId() != null) {
            throw new BadRequestAlertException("A new orthophoniste cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrthophonisteDTO result = orthophonisteService.save(orthophonisteDTO);
        return ResponseEntity.created(new URI("/api/orthophonistes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orthophonistes : Updates an existing orthophoniste.
     *
     * @param orthophonisteDTO the orthophonisteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orthophonisteDTO,
     * or with status 400 (Bad Request) if the orthophonisteDTO is not valid,
     * or with status 500 (Internal Server Error) if the orthophonisteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orthophonistes")
    @Timed
    public ResponseEntity<OrthophonisteDTO> updateOrthophoniste(@Valid @RequestBody OrthophonisteDTO orthophonisteDTO) throws URISyntaxException {
        log.debug("REST request to update Orthophoniste : {}", orthophonisteDTO);
        if (orthophonisteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrthophonisteDTO result = orthophonisteService.save(orthophonisteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orthophonisteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orthophonistes : get all the orthophonistes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of orthophonistes in body
     */
    @GetMapping("/orthophonistes")
    @Timed
    public List<OrthophonisteDTO> getAllOrthophonistes(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Orthophonistes");
        return orthophonisteService.findAll();
    }

    /**
     * GET  /orthophonistes/:id : get the "id" orthophoniste.
     *
     * @param id the id of the orthophonisteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orthophonisteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/orthophonistes/{id}")
    @Timed
    public ResponseEntity<OrthophonisteDTO> getOrthophoniste(@PathVariable Long id) {
        log.debug("REST request to get Orthophoniste : {}", id);
        Optional<OrthophonisteDTO> orthophonisteDTO = orthophonisteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orthophonisteDTO);
    }

    /**
     * DELETE  /orthophonistes/:id : delete the "id" orthophoniste.
     *
     * @param id the id of the orthophonisteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orthophonistes/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrthophoniste(@PathVariable Long id) {
        log.debug("REST request to delete Orthophoniste : {}", id);
        orthophonisteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
