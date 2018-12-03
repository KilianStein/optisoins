package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.MutuelleService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.MutuelleDTO;
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
 * REST controller for managing Mutuelle.
 */
@RestController
@RequestMapping("/api")
public class MutuelleResource {

    private final Logger log = LoggerFactory.getLogger(MutuelleResource.class);

    private static final String ENTITY_NAME = "mutuelle";

    private final MutuelleService mutuelleService;

    public MutuelleResource(MutuelleService mutuelleService) {
        this.mutuelleService = mutuelleService;
    }

    /**
     * POST  /mutuelles : Create a new mutuelle.
     *
     * @param mutuelleDTO the mutuelleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mutuelleDTO, or with status 400 (Bad Request) if the mutuelle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mutuelles")
    @Timed
    public ResponseEntity<MutuelleDTO> createMutuelle(@Valid @RequestBody MutuelleDTO mutuelleDTO) throws URISyntaxException {
        log.debug("REST request to save Mutuelle : {}", mutuelleDTO);
        if (mutuelleDTO.getId() != null) {
            throw new BadRequestAlertException("A new mutuelle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MutuelleDTO result = mutuelleService.save(mutuelleDTO);
        return ResponseEntity.created(new URI("/api/mutuelles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mutuelles : Updates an existing mutuelle.
     *
     * @param mutuelleDTO the mutuelleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mutuelleDTO,
     * or with status 400 (Bad Request) if the mutuelleDTO is not valid,
     * or with status 500 (Internal Server Error) if the mutuelleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mutuelles")
    @Timed
    public ResponseEntity<MutuelleDTO> updateMutuelle(@Valid @RequestBody MutuelleDTO mutuelleDTO) throws URISyntaxException {
        log.debug("REST request to update Mutuelle : {}", mutuelleDTO);
        if (mutuelleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MutuelleDTO result = mutuelleService.save(mutuelleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mutuelleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mutuelles : get all the mutuelles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mutuelles in body
     */
    @GetMapping("/mutuelles")
    @Timed
    public List<MutuelleDTO> getAllMutuelles() {
        log.debug("REST request to get all Mutuelles");
        return mutuelleService.findAll();
    }

    /**
     * GET  /mutuelles/:id : get the "id" mutuelle.
     *
     * @param id the id of the mutuelleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mutuelleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/mutuelles/{id}")
    @Timed
    public ResponseEntity<MutuelleDTO> getMutuelle(@PathVariable Long id) {
        log.debug("REST request to get Mutuelle : {}", id);
        Optional<MutuelleDTO> mutuelleDTO = mutuelleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mutuelleDTO);
    }

    /**
     * DELETE  /mutuelles/:id : delete the "id" mutuelle.
     *
     * @param id the id of the mutuelleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mutuelles/{id}")
    @Timed
    public ResponseEntity<Void> deleteMutuelle(@PathVariable Long id) {
        log.debug("REST request to delete Mutuelle : {}", id);
        mutuelleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
