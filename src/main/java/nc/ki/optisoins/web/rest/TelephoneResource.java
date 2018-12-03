package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.TelephoneService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.TelephoneDTO;
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
 * REST controller for managing Telephone.
 */
@RestController
@RequestMapping("/api")
public class TelephoneResource {

    private final Logger log = LoggerFactory.getLogger(TelephoneResource.class);

    private static final String ENTITY_NAME = "telephone";

    private final TelephoneService telephoneService;

    public TelephoneResource(TelephoneService telephoneService) {
        this.telephoneService = telephoneService;
    }

    /**
     * POST  /telephones : Create a new telephone.
     *
     * @param telephoneDTO the telephoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new telephoneDTO, or with status 400 (Bad Request) if the telephone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/telephones")
    @Timed
    public ResponseEntity<TelephoneDTO> createTelephone(@Valid @RequestBody TelephoneDTO telephoneDTO) throws URISyntaxException {
        log.debug("REST request to save Telephone : {}", telephoneDTO);
        if (telephoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new telephone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelephoneDTO result = telephoneService.save(telephoneDTO);
        return ResponseEntity.created(new URI("/api/telephones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /telephones : Updates an existing telephone.
     *
     * @param telephoneDTO the telephoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated telephoneDTO,
     * or with status 400 (Bad Request) if the telephoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the telephoneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/telephones")
    @Timed
    public ResponseEntity<TelephoneDTO> updateTelephone(@Valid @RequestBody TelephoneDTO telephoneDTO) throws URISyntaxException {
        log.debug("REST request to update Telephone : {}", telephoneDTO);
        if (telephoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TelephoneDTO result = telephoneService.save(telephoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, telephoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /telephones : get all the telephones.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of telephones in body
     */
    @GetMapping("/telephones")
    @Timed
    public List<TelephoneDTO> getAllTelephones() {
        log.debug("REST request to get all Telephones");
        return telephoneService.findAll();
    }

    /**
     * GET  /telephones/:id : get the "id" telephone.
     *
     * @param id the id of the telephoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the telephoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/telephones/{id}")
    @Timed
    public ResponseEntity<TelephoneDTO> getTelephone(@PathVariable Long id) {
        log.debug("REST request to get Telephone : {}", id);
        Optional<TelephoneDTO> telephoneDTO = telephoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telephoneDTO);
    }

    /**
     * DELETE  /telephones/:id : delete the "id" telephone.
     *
     * @param id the id of the telephoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/telephones/{id}")
    @Timed
    public ResponseEntity<Void> deleteTelephone(@PathVariable Long id) {
        log.debug("REST request to delete Telephone : {}", id);
        telephoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
