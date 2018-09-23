package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Seance;
import nc.ki.optisoins.repository.SeanceRepository;
import nc.ki.optisoins.service.SeanceService;
import nc.ki.optisoins.service.dto.SeanceDTO;
import nc.ki.optisoins.service.mapper.SeanceMapper;
import nc.ki.optisoins.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static nc.ki.optisoins.web.rest.TestUtil.sameInstant;
import static nc.ki.optisoins.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import nc.ki.optisoins.domain.enumeration.EtatSeance;
/**
 * Test class for the SeanceResource REST controller.
 *
 * @see SeanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class SeanceResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEBUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ORIGINE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DOMICILE = false;
    private static final Boolean UPDATED_DOMICILE = true;

    private static final Boolean DEFAULT_TICKET_MODERATEUR = false;
    private static final Boolean UPDATED_TICKET_MODERATEUR = true;

    private static final Boolean DEFAULT_BILAN = false;
    private static final Boolean UPDATED_BILAN = true;

    private static final EtatSeance DEFAULT_ETAT = EtatSeance.PLANIFIE;
    private static final EtatSeance UPDATED_ETAT = EtatSeance.ANNULE;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private SeanceMapper seanceMapper;
    
    @Autowired
    private SeanceService seanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeanceMockMvc;

    private Seance seance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeanceResource seanceResource = new SeanceResource(seanceService);
        this.restSeanceMockMvc = MockMvcBuilders.standaloneSetup(seanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seance createEntity(EntityManager em) {
        Seance seance = new Seance()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .origine(DEFAULT_ORIGINE)
            .domicile(DEFAULT_DOMICILE)
            .ticketModerateur(DEFAULT_TICKET_MODERATEUR)
            .bilan(DEFAULT_BILAN)
            .etat(DEFAULT_ETAT)
            .commentaire(DEFAULT_COMMENTAIRE);
        return seance;
    }

    @Before
    public void initTest() {
        seance = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeance() throws Exception {
        int databaseSizeBeforeCreate = seanceRepository.findAll().size();

        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);
        restSeanceMockMvc.perform(post("/api/seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeCreate + 1);
        Seance testSeance = seanceList.get(seanceList.size() - 1);
        assertThat(testSeance.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testSeance.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testSeance.getOrigine()).isEqualTo(DEFAULT_ORIGINE);
        assertThat(testSeance.isDomicile()).isEqualTo(DEFAULT_DOMICILE);
        assertThat(testSeance.isTicketModerateur()).isEqualTo(DEFAULT_TICKET_MODERATEUR);
        assertThat(testSeance.isBilan()).isEqualTo(DEFAULT_BILAN);
        assertThat(testSeance.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testSeance.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createSeanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seanceRepository.findAll().size();

        // Create the Seance with an existing ID
        seance.setId(1L);
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeanceMockMvc.perform(post("/api/seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = seanceRepository.findAll().size();
        // set the field null
        seance.setDateDebut(null);

        // Create the Seance, which fails.
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        restSeanceMockMvc.perform(post("/api/seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isBadRequest());

        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = seanceRepository.findAll().size();
        // set the field null
        seance.setDateFin(null);

        // Create the Seance, which fails.
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        restSeanceMockMvc.perform(post("/api/seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isBadRequest());

        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeances() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        // Get all the seanceList
        restSeanceMockMvc.perform(get("/api/seances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seance.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(sameInstant(DEFAULT_DATE_DEBUT))))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(sameInstant(DEFAULT_DATE_FIN))))
            .andExpect(jsonPath("$.[*].origine").value(hasItem(DEFAULT_ORIGINE.toString())))
            .andExpect(jsonPath("$.[*].domicile").value(hasItem(DEFAULT_DOMICILE.booleanValue())))
            .andExpect(jsonPath("$.[*].ticketModerateur").value(hasItem(DEFAULT_TICKET_MODERATEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].bilan").value(hasItem(DEFAULT_BILAN.booleanValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())));
    }
    
    @Test
    @Transactional
    public void getSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        // Get the seance
        restSeanceMockMvc.perform(get("/api/seances/{id}", seance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seance.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(sameInstant(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.dateFin").value(sameInstant(DEFAULT_DATE_FIN)))
            .andExpect(jsonPath("$.origine").value(DEFAULT_ORIGINE.toString()))
            .andExpect(jsonPath("$.domicile").value(DEFAULT_DOMICILE.booleanValue()))
            .andExpect(jsonPath("$.ticketModerateur").value(DEFAULT_TICKET_MODERATEUR.booleanValue()))
            .andExpect(jsonPath("$.bilan").value(DEFAULT_BILAN.booleanValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeance() throws Exception {
        // Get the seance
        restSeanceMockMvc.perform(get("/api/seances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();

        // Update the seance
        Seance updatedSeance = seanceRepository.findById(seance.getId()).get();
        // Disconnect from session so that the updates on updatedSeance are not directly saved in db
        em.detach(updatedSeance);
        updatedSeance
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .origine(UPDATED_ORIGINE)
            .domicile(UPDATED_DOMICILE)
            .ticketModerateur(UPDATED_TICKET_MODERATEUR)
            .bilan(UPDATED_BILAN)
            .etat(UPDATED_ETAT)
            .commentaire(UPDATED_COMMENTAIRE);
        SeanceDTO seanceDTO = seanceMapper.toDto(updatedSeance);

        restSeanceMockMvc.perform(put("/api/seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isOk());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
        Seance testSeance = seanceList.get(seanceList.size() - 1);
        assertThat(testSeance.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testSeance.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testSeance.getOrigine()).isEqualTo(UPDATED_ORIGINE);
        assertThat(testSeance.isDomicile()).isEqualTo(UPDATED_DOMICILE);
        assertThat(testSeance.isTicketModerateur()).isEqualTo(UPDATED_TICKET_MODERATEUR);
        assertThat(testSeance.isBilan()).isEqualTo(UPDATED_BILAN);
        assertThat(testSeance.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testSeance.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingSeance() throws Exception {
        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();

        // Create the Seance
        SeanceDTO seanceDTO = seanceMapper.toDto(seance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeanceMockMvc.perform(put("/api/seances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        int databaseSizeBeforeDelete = seanceRepository.findAll().size();

        // Get the seance
        restSeanceMockMvc.perform(delete("/api/seances/{id}", seance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seance.class);
        Seance seance1 = new Seance();
        seance1.setId(1L);
        Seance seance2 = new Seance();
        seance2.setId(seance1.getId());
        assertThat(seance1).isEqualTo(seance2);
        seance2.setId(2L);
        assertThat(seance1).isNotEqualTo(seance2);
        seance1.setId(null);
        assertThat(seance1).isNotEqualTo(seance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeanceDTO.class);
        SeanceDTO seanceDTO1 = new SeanceDTO();
        seanceDTO1.setId(1L);
        SeanceDTO seanceDTO2 = new SeanceDTO();
        assertThat(seanceDTO1).isNotEqualTo(seanceDTO2);
        seanceDTO2.setId(seanceDTO1.getId());
        assertThat(seanceDTO1).isEqualTo(seanceDTO2);
        seanceDTO2.setId(2L);
        assertThat(seanceDTO1).isNotEqualTo(seanceDTO2);
        seanceDTO1.setId(null);
        assertThat(seanceDTO1).isNotEqualTo(seanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seanceMapper.fromId(null)).isNull();
    }
}
