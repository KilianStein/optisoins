package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Ordonnance;
import nc.ki.optisoins.repository.OrdonnanceRepository;
import nc.ki.optisoins.service.OrdonnanceService;
import nc.ki.optisoins.service.dto.OrdonnanceDTO;
import nc.ki.optisoins.service.mapper.OrdonnanceMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static nc.ki.optisoins.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrdonnanceResource REST controller.
 *
 * @see OrdonnanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class OrdonnanceResourceIntTest {

    private static final LocalDate DEFAULT_DATE_PRESCRIPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PRESCRIPTION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NOMBRE_SEANCES = 1;
    private static final Integer UPDATED_NOMBRE_SEANCES = 2;

    @Autowired
    private OrdonnanceRepository ordonnanceRepository;

    @Autowired
    private OrdonnanceMapper ordonnanceMapper;

    @Autowired
    private OrdonnanceService ordonnanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdonnanceMockMvc;

    private Ordonnance ordonnance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdonnanceResource ordonnanceResource = new OrdonnanceResource(ordonnanceService);
        this.restOrdonnanceMockMvc = MockMvcBuilders.standaloneSetup(ordonnanceResource)
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
    public static Ordonnance createEntity(EntityManager em) {
        Ordonnance ordonnance = new Ordonnance()
            .datePrescription(DEFAULT_DATE_PRESCRIPTION)
            .nombreSeances(DEFAULT_NOMBRE_SEANCES);
        return ordonnance;
    }

    @Before
    public void initTest() {
        ordonnance = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdonnance() throws Exception {
        int databaseSizeBeforeCreate = ordonnanceRepository.findAll().size();

        // Create the Ordonnance
        OrdonnanceDTO ordonnanceDTO = ordonnanceMapper.toDto(ordonnance);
        restOrdonnanceMockMvc.perform(post("/api/ordonnances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordonnanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeCreate + 1);
        Ordonnance testOrdonnance = ordonnanceList.get(ordonnanceList.size() - 1);
        assertThat(testOrdonnance.getDatePrescription()).isEqualTo(DEFAULT_DATE_PRESCRIPTION);
        assertThat(testOrdonnance.getNombreSeances()).isEqualTo(DEFAULT_NOMBRE_SEANCES);
    }

    @Test
    @Transactional
    public void createOrdonnanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordonnanceRepository.findAll().size();

        // Create the Ordonnance with an existing ID
        ordonnance.setId(1L);
        OrdonnanceDTO ordonnanceDTO = ordonnanceMapper.toDto(ordonnance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdonnanceMockMvc.perform(post("/api/ordonnances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordonnanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatePrescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordonnanceRepository.findAll().size();
        // set the field null
        ordonnance.setDatePrescription(null);

        // Create the Ordonnance, which fails.
        OrdonnanceDTO ordonnanceDTO = ordonnanceMapper.toDto(ordonnance);

        restOrdonnanceMockMvc.perform(post("/api/ordonnances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordonnanceDTO)))
            .andExpect(status().isBadRequest());

        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreSeancesIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordonnanceRepository.findAll().size();
        // set the field null
        ordonnance.setNombreSeances(null);

        // Create the Ordonnance, which fails.
        OrdonnanceDTO ordonnanceDTO = ordonnanceMapper.toDto(ordonnance);

        restOrdonnanceMockMvc.perform(post("/api/ordonnances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordonnanceDTO)))
            .andExpect(status().isBadRequest());

        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrdonnances() throws Exception {
        // Initialize the database
        ordonnanceRepository.saveAndFlush(ordonnance);

        // Get all the ordonnanceList
        restOrdonnanceMockMvc.perform(get("/api/ordonnances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordonnance.getId().intValue())))
            .andExpect(jsonPath("$.[*].datePrescription").value(hasItem(DEFAULT_DATE_PRESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].nombreSeances").value(hasItem(DEFAULT_NOMBRE_SEANCES)));
    }
    
    @Test
    @Transactional
    public void getOrdonnance() throws Exception {
        // Initialize the database
        ordonnanceRepository.saveAndFlush(ordonnance);

        // Get the ordonnance
        restOrdonnanceMockMvc.perform(get("/api/ordonnances/{id}", ordonnance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordonnance.getId().intValue()))
            .andExpect(jsonPath("$.datePrescription").value(DEFAULT_DATE_PRESCRIPTION.toString()))
            .andExpect(jsonPath("$.nombreSeances").value(DEFAULT_NOMBRE_SEANCES));
    }

    @Test
    @Transactional
    public void getNonExistingOrdonnance() throws Exception {
        // Get the ordonnance
        restOrdonnanceMockMvc.perform(get("/api/ordonnances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdonnance() throws Exception {
        // Initialize the database
        ordonnanceRepository.saveAndFlush(ordonnance);

        int databaseSizeBeforeUpdate = ordonnanceRepository.findAll().size();

        // Update the ordonnance
        Ordonnance updatedOrdonnance = ordonnanceRepository.findById(ordonnance.getId()).get();
        // Disconnect from session so that the updates on updatedOrdonnance are not directly saved in db
        em.detach(updatedOrdonnance);
        updatedOrdonnance
            .datePrescription(UPDATED_DATE_PRESCRIPTION)
            .nombreSeances(UPDATED_NOMBRE_SEANCES);
        OrdonnanceDTO ordonnanceDTO = ordonnanceMapper.toDto(updatedOrdonnance);

        restOrdonnanceMockMvc.perform(put("/api/ordonnances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordonnanceDTO)))
            .andExpect(status().isOk());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeUpdate);
        Ordonnance testOrdonnance = ordonnanceList.get(ordonnanceList.size() - 1);
        assertThat(testOrdonnance.getDatePrescription()).isEqualTo(UPDATED_DATE_PRESCRIPTION);
        assertThat(testOrdonnance.getNombreSeances()).isEqualTo(UPDATED_NOMBRE_SEANCES);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdonnance() throws Exception {
        int databaseSizeBeforeUpdate = ordonnanceRepository.findAll().size();

        // Create the Ordonnance
        OrdonnanceDTO ordonnanceDTO = ordonnanceMapper.toDto(ordonnance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdonnanceMockMvc.perform(put("/api/ordonnances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordonnanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ordonnance in the database
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrdonnance() throws Exception {
        // Initialize the database
        ordonnanceRepository.saveAndFlush(ordonnance);

        int databaseSizeBeforeDelete = ordonnanceRepository.findAll().size();

        // Get the ordonnance
        restOrdonnanceMockMvc.perform(delete("/api/ordonnances/{id}", ordonnance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ordonnance> ordonnanceList = ordonnanceRepository.findAll();
        assertThat(ordonnanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ordonnance.class);
        Ordonnance ordonnance1 = new Ordonnance();
        ordonnance1.setId(1L);
        Ordonnance ordonnance2 = new Ordonnance();
        ordonnance2.setId(ordonnance1.getId());
        assertThat(ordonnance1).isEqualTo(ordonnance2);
        ordonnance2.setId(2L);
        assertThat(ordonnance1).isNotEqualTo(ordonnance2);
        ordonnance1.setId(null);
        assertThat(ordonnance1).isNotEqualTo(ordonnance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdonnanceDTO.class);
        OrdonnanceDTO ordonnanceDTO1 = new OrdonnanceDTO();
        ordonnanceDTO1.setId(1L);
        OrdonnanceDTO ordonnanceDTO2 = new OrdonnanceDTO();
        assertThat(ordonnanceDTO1).isNotEqualTo(ordonnanceDTO2);
        ordonnanceDTO2.setId(ordonnanceDTO1.getId());
        assertThat(ordonnanceDTO1).isEqualTo(ordonnanceDTO2);
        ordonnanceDTO2.setId(2L);
        assertThat(ordonnanceDTO1).isNotEqualTo(ordonnanceDTO2);
        ordonnanceDTO1.setId(null);
        assertThat(ordonnanceDTO1).isNotEqualTo(ordonnanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ordonnanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ordonnanceMapper.fromId(null)).isNull();
    }
}
