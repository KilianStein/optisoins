package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.RemplacanteService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.RemplacanteDTO;
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
 * REST controller for managing Remplacante.
 */
@RestController
@RequestMapping("/api")
public class RemplacanteResource {

    private final Logger log = LoggerFactory.getLogger(RemplacanteResource.class);

    private static final String ENTITY_NAME = "remplacante";

    private final RemplacanteService remplacanteService;

    public RemplacanteResource(RemplacanteService remplacanteService) {
        this.remplacanteService = remplacanteService;
    }

    /**
     * POST  /remplacantes : Create a new remplacante.
     *
     * @param remplacanteDTO the remplacanteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new remplacanteDTO, or with status 400 (Bad Request) if the remplacante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/remplacantes")
    @Timed
    public ResponseEntity<RemplacanteDTO> createRemplacante(@Valid @RequestBody RemplacanteDTO remplacanteDTO) throws URISyntaxException {
        log.debug("REST request to save Remplacante : {}", remplacanteDTO);
        if (remplacanteDTO.getId() != null) {
            throw new BadRequestAlertException("A new remplacante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RemplacanteDTO result = remplacanteService.save(remplacanteDTO);
        return ResponseEntity.created(new URI("/api/remplacantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /remplacantes : Updates an existing remplacante.
     *
     * @param remplacanteDTO the remplacanteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated remplacanteDTO,
     * or with status 400 (Bad Request) if the remplacanteDTO is not valid,
     * or with status 500 (Internal Server Error) if the remplacanteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/remplacantes")
    @Timed
    public ResponseEntity<RemplacanteDTO> updateRemplacante(@Valid @RequestBody RemplacanteDTO remplacanteDTO) throws URISyntaxException {
        log.debug("REST request to update Remplacante : {}", remplacanteDTO);
        if (remplacanteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RemplacanteDTO result = remplacanteService.save(remplacanteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, remplacanteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /remplacantes : get all the remplacantes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of remplacantes in body
     */
    @GetMapping("/remplacantes")
    @Timed
    public List<RemplacanteDTO> getAllRemplacantes() {
        log.debug("REST request to get all Remplacantes");
        return remplacanteService.findAll();
    }

    /**
     * GET  /remplacantes/:id : get the "id" remplacante.
     *
     * @param id the id of the remplacanteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the remplacanteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/remplacantes/{id}")
    @Timed
    public ResponseEntity<RemplacanteDTO> getRemplacante(@PathVariable Long id) {
        log.debug("REST request to get Remplacante : {}", id);
        Optional<RemplacanteDTO> remplacanteDTO = remplacanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remplacanteDTO);
    }

    /**
     * DELETE  /remplacantes/:id : delete the "id" remplacante.
     *
     * @param id the id of the remplacanteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/remplacantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRemplacante(@PathVariable Long id) {
        log.debug("REST request to delete Remplacante : {}", id);
        remplacanteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
