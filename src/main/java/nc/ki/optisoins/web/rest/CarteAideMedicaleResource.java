package nc.ki.optisoins.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.ki.optisoins.service.CarteAideMedicaleService;
import nc.ki.optisoins.web.rest.errors.BadRequestAlertException;
import nc.ki.optisoins.web.rest.util.HeaderUtil;
import nc.ki.optisoins.service.dto.CarteAideMedicaleDTO;
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
 * REST controller for managing CarteAideMedicale.
 */
@RestController
@RequestMapping("/api")
public class CarteAideMedicaleResource {

    private final Logger log = LoggerFactory.getLogger(CarteAideMedicaleResource.class);

    private static final String ENTITY_NAME = "carteAideMedicale";

    private final CarteAideMedicaleService carteAideMedicaleService;

    public CarteAideMedicaleResource(CarteAideMedicaleService carteAideMedicaleService) {
        this.carteAideMedicaleService = carteAideMedicaleService;
    }

    /**
     * POST  /carte-aide-medicales : Create a new carteAideMedicale.
     *
     * @param carteAideMedicaleDTO the carteAideMedicaleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carteAideMedicaleDTO, or with status 400 (Bad Request) if the carteAideMedicale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carte-aide-medicales")
    @Timed
    public ResponseEntity<CarteAideMedicaleDTO> createCarteAideMedicale(@Valid @RequestBody CarteAideMedicaleDTO carteAideMedicaleDTO) throws URISyntaxException {
        log.debug("REST request to save CarteAideMedicale : {}", carteAideMedicaleDTO);
        if (carteAideMedicaleDTO.getId() != null) {
            throw new BadRequestAlertException("A new carteAideMedicale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarteAideMedicaleDTO result = carteAideMedicaleService.save(carteAideMedicaleDTO);
        return ResponseEntity.created(new URI("/api/carte-aide-medicales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carte-aide-medicales : Updates an existing carteAideMedicale.
     *
     * @param carteAideMedicaleDTO the carteAideMedicaleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carteAideMedicaleDTO,
     * or with status 400 (Bad Request) if the carteAideMedicaleDTO is not valid,
     * or with status 500 (Internal Server Error) if the carteAideMedicaleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carte-aide-medicales")
    @Timed
    public ResponseEntity<CarteAideMedicaleDTO> updateCarteAideMedicale(@Valid @RequestBody CarteAideMedicaleDTO carteAideMedicaleDTO) throws URISyntaxException {
        log.debug("REST request to update CarteAideMedicale : {}", carteAideMedicaleDTO);
        if (carteAideMedicaleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarteAideMedicaleDTO result = carteAideMedicaleService.save(carteAideMedicaleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carteAideMedicaleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carte-aide-medicales : get all the carteAideMedicales.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carteAideMedicales in body
     */
    @GetMapping("/carte-aide-medicales")
    @Timed
    public List<CarteAideMedicaleDTO> getAllCarteAideMedicales() {
        log.debug("REST request to get all CarteAideMedicales");
        return carteAideMedicaleService.findAll();
    }

    /**
     * GET  /carte-aide-medicales/:id : get the "id" carteAideMedicale.
     *
     * @param id the id of the carteAideMedicaleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carteAideMedicaleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/carte-aide-medicales/{id}")
    @Timed
    public ResponseEntity<CarteAideMedicaleDTO> getCarteAideMedicale(@PathVariable Long id) {
        log.debug("REST request to get CarteAideMedicale : {}", id);
        Optional<CarteAideMedicaleDTO> carteAideMedicaleDTO = carteAideMedicaleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carteAideMedicaleDTO);
    }

    /**
     * DELETE  /carte-aide-medicales/:id : delete the "id" carteAideMedicale.
     *
     * @param id the id of the carteAideMedicaleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carte-aide-medicales/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarteAideMedicale(@PathVariable Long id) {
        log.debug("REST request to delete CarteAideMedicale : {}", id);
        carteAideMedicaleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
