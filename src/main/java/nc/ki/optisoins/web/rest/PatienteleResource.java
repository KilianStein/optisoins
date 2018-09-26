package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.PatienteleService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.PatienteleDTO;
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
 * REST controller for managing Patientele.
 */
@RestController
@RequestMapping("/api")
public class PatienteleResource {

    private final Logger log = LoggerFactory.getLogger(PatienteleResource.class);

    private static final String ENTITY_NAME = "patientele";

    private final PatienteleService patienteleService;

    public PatienteleResource(PatienteleService patienteleService) {
        this.patienteleService = patienteleService;
    }

    /**
     * POST  /patienteles : Create a new patientele.
     *
     * @param patienteleDTO the patienteleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patienteleDTO, or with status 400 (Bad Request) if the patientele has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patienteles")
    @Timed
    public ResponseEntity<PatienteleDTO> createPatientele(@Valid @RequestBody PatienteleDTO patienteleDTO) throws URISyntaxException {
        log.debug("REST request to save Patientele : {}", patienteleDTO);
        if (patienteleDTO.getId() != null) {
            throw new BadRequestAlertException("A new patientele cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatienteleDTO result = patienteleService.save(patienteleDTO);
        return ResponseEntity.created(new URI("/api/patienteles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patienteles : Updates an existing patientele.
     *
     * @param patienteleDTO the patienteleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patienteleDTO,
     * or with status 400 (Bad Request) if the patienteleDTO is not valid,
     * or with status 500 (Internal Server Error) if the patienteleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patienteles")
    @Timed
    public ResponseEntity<PatienteleDTO> updatePatientele(@Valid @RequestBody PatienteleDTO patienteleDTO) throws URISyntaxException {
        log.debug("REST request to update Patientele : {}", patienteleDTO);
        if (patienteleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatienteleDTO result = patienteleService.save(patienteleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patienteleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patienteles : get all the patienteles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patienteles in body
     */
    @GetMapping("/patienteles")
    @Timed
    public List<PatienteleDTO> getAllPatienteles() {
        log.debug("REST request to get all Patienteles");
        return patienteleService.findAll();
    }

    /**
     * GET  /patienteles/:id : get the "id" patientele.
     *
     * @param id the id of the patienteleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patienteleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/patienteles/{id}")
    @Timed
    public ResponseEntity<PatienteleDTO> getPatientele(@PathVariable Long id) {
        log.debug("REST request to get Patientele : {}", id);
        Optional<PatienteleDTO> patienteleDTO = patienteleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patienteleDTO);
    }

    /**
     * DELETE  /patienteles/:id : delete the "id" patientele.
     *
     * @param id the id of the patienteleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patienteles/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientele(@PathVariable Long id) {
        log.debug("REST request to delete Patientele : {}", id);
        patienteleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
