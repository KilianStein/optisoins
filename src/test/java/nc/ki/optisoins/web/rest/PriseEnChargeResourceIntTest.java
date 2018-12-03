package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.PriseEnCharge;
import nc.ki.optisoins.repository.PriseEnChargeRepository;
import nc.ki.optisoins.service.PriseEnChargeService;
import nc.ki.optisoins.service.dto.PriseEnChargeDTO;
import nc.ki.optisoins.service.mapper.PriseEnChargeMapper;
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

import nc.ki.optisoins.domain.enumeration.TypePriseEnCharge;
/**
 * Test class for the PriseEnChargeResource REST controller.
 *
 * @see PriseEnChargeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class PriseEnChargeResourceIntTest {

    private static final TypePriseEnCharge DEFAULT_TYPE = TypePriseEnCharge.CAFAT_MUTUELLE;
    private static final TypePriseEnCharge UPDATED_TYPE = TypePriseEnCharge.LONGUE_MALADIE;

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACCIDENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCIDENT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOM_TIER_IMPLIQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_TIER_IMPLIQUE = "BBBBBBBBBB";

    @Autowired
    private PriseEnChargeRepository priseEnChargeRepository;

    @Autowired
    private PriseEnChargeMapper priseEnChargeMapper;

    @Autowired
    private PriseEnChargeService priseEnChargeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriseEnChargeMockMvc;

    private PriseEnCharge priseEnCharge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriseEnChargeResource priseEnChargeResource = new PriseEnChargeResource(priseEnChargeService);
        this.restPriseEnChargeMockMvc = MockMvcBuilders.standaloneSetup(priseEnChargeResource)
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
    public static PriseEnCharge createEntity(EntityManager em) {
        PriseEnCharge priseEnCharge = new PriseEnCharge()
            .type(DEFAULT_TYPE)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .accident(DEFAULT_ACCIDENT)
            .nomTierImplique(DEFAULT_NOM_TIER_IMPLIQUE);
        return priseEnCharge;
    }

    @Before
    public void initTest() {
        priseEnCharge = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriseEnCharge() throws Exception {
        int databaseSizeBeforeCreate = priseEnChargeRepository.findAll().size();

        // Create the PriseEnCharge
        PriseEnChargeDTO priseEnChargeDTO = priseEnChargeMapper.toDto(priseEnCharge);
        restPriseEnChargeMockMvc.perform(post("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnChargeDTO)))
            .andExpect(status().isCreated());

        // Validate the PriseEnCharge in the database
        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeCreate + 1);
        PriseEnCharge testPriseEnCharge = priseEnChargeList.get(priseEnChargeList.size() - 1);
        assertThat(testPriseEnCharge.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPriseEnCharge.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testPriseEnCharge.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testPriseEnCharge.getAccident()).isEqualTo(DEFAULT_ACCIDENT);
        assertThat(testPriseEnCharge.getNomTierImplique()).isEqualTo(DEFAULT_NOM_TIER_IMPLIQUE);
    }

    @Test
    @Transactional
    public void createPriseEnChargeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priseEnChargeRepository.findAll().size();

        // Create the PriseEnCharge with an existing ID
        priseEnCharge.setId(1L);
        PriseEnChargeDTO priseEnChargeDTO = priseEnChargeMapper.toDto(priseEnCharge);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriseEnChargeMockMvc.perform(post("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnChargeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriseEnCharge in the database
        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priseEnChargeRepository.findAll().size();
        // set the field null
        priseEnCharge.setType(null);

        // Create the PriseEnCharge, which fails.
        PriseEnChargeDTO priseEnChargeDTO = priseEnChargeMapper.toDto(priseEnCharge);

        restPriseEnChargeMockMvc.perform(post("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnChargeDTO)))
            .andExpect(status().isBadRequest());

        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = priseEnChargeRepository.findAll().size();
        // set the field null
        priseEnCharge.setDateDebut(null);

        // Create the PriseEnCharge, which fails.
        PriseEnChargeDTO priseEnChargeDTO = priseEnChargeMapper.toDto(priseEnCharge);

        restPriseEnChargeMockMvc.perform(post("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnChargeDTO)))
            .andExpect(status().isBadRequest());

        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriseEnCharges() throws Exception {
        // Initialize the database
        priseEnChargeRepository.saveAndFlush(priseEnCharge);

        // Get all the priseEnChargeList
        restPriseEnChargeMockMvc.perform(get("/api/prise-en-charges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priseEnCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].accident").value(hasItem(DEFAULT_ACCIDENT.toString())))
            .andExpect(jsonPath("$.[*].nomTierImplique").value(hasItem(DEFAULT_NOM_TIER_IMPLIQUE.toString())));
    }
    
    @Test
    @Transactional
    public void getPriseEnCharge() throws Exception {
        // Initialize the database
        priseEnChargeRepository.saveAndFlush(priseEnCharge);

        // Get the priseEnCharge
        restPriseEnChargeMockMvc.perform(get("/api/prise-en-charges/{id}", priseEnCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priseEnCharge.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.accident").value(DEFAULT_ACCIDENT.toString()))
            .andExpect(jsonPath("$.nomTierImplique").value(DEFAULT_NOM_TIER_IMPLIQUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPriseEnCharge() throws Exception {
        // Get the priseEnCharge
        restPriseEnChargeMockMvc.perform(get("/api/prise-en-charges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriseEnCharge() throws Exception {
        // Initialize the database
        priseEnChargeRepository.saveAndFlush(priseEnCharge);

        int databaseSizeBeforeUpdate = priseEnChargeRepository.findAll().size();

        // Update the priseEnCharge
        PriseEnCharge updatedPriseEnCharge = priseEnChargeRepository.findById(priseEnCharge.getId()).get();
        // Disconnect from session so that the updates on updatedPriseEnCharge are not directly saved in db
        em.detach(updatedPriseEnCharge);
        updatedPriseEnCharge
            .type(UPDATED_TYPE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .accident(UPDATED_ACCIDENT)
            .nomTierImplique(UPDATED_NOM_TIER_IMPLIQUE);
        PriseEnChargeDTO priseEnChargeDTO = priseEnChargeMapper.toDto(updatedPriseEnCharge);

        restPriseEnChargeMockMvc.perform(put("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnChargeDTO)))
            .andExpect(status().isOk());

        // Validate the PriseEnCharge in the database
        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeUpdate);
        PriseEnCharge testPriseEnCharge = priseEnChargeList.get(priseEnChargeList.size() - 1);
        assertThat(testPriseEnCharge.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPriseEnCharge.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPriseEnCharge.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testPriseEnCharge.getAccident()).isEqualTo(UPDATED_ACCIDENT);
        assertThat(testPriseEnCharge.getNomTierImplique()).isEqualTo(UPDATED_NOM_TIER_IMPLIQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPriseEnCharge() throws Exception {
        int databaseSizeBeforeUpdate = priseEnChargeRepository.findAll().size();

        // Create the PriseEnCharge
        PriseEnChargeDTO priseEnChargeDTO = priseEnChargeMapper.toDto(priseEnCharge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriseEnChargeMockMvc.perform(put("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnChargeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriseEnCharge in the database
        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePriseEnCharge() throws Exception {
        // Initialize the database
        priseEnChargeRepository.saveAndFlush(priseEnCharge);

        int databaseSizeBeforeDelete = priseEnChargeRepository.findAll().size();

        // Get the priseEnCharge
        restPriseEnChargeMockMvc.perform(delete("/api/prise-en-charges/{id}", priseEnCharge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriseEnCharge.class);
        PriseEnCharge priseEnCharge1 = new PriseEnCharge();
        priseEnCharge1.setId(1L);
        PriseEnCharge priseEnCharge2 = new PriseEnCharge();
        priseEnCharge2.setId(priseEnCharge1.getId());
        assertThat(priseEnCharge1).isEqualTo(priseEnCharge2);
        priseEnCharge2.setId(2L);
        assertThat(priseEnCharge1).isNotEqualTo(priseEnCharge2);
        priseEnCharge1.setId(null);
        assertThat(priseEnCharge1).isNotEqualTo(priseEnCharge2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriseEnChargeDTO.class);
        PriseEnChargeDTO priseEnChargeDTO1 = new PriseEnChargeDTO();
        priseEnChargeDTO1.setId(1L);
        PriseEnChargeDTO priseEnChargeDTO2 = new PriseEnChargeDTO();
        assertThat(priseEnChargeDTO1).isNotEqualTo(priseEnChargeDTO2);
        priseEnChargeDTO2.setId(priseEnChargeDTO1.getId());
        assertThat(priseEnChargeDTO1).isEqualTo(priseEnChargeDTO2);
        priseEnChargeDTO2.setId(2L);
        assertThat(priseEnChargeDTO1).isNotEqualTo(priseEnChargeDTO2);
        priseEnChargeDTO1.setId(null);
        assertThat(priseEnChargeDTO1).isNotEqualTo(priseEnChargeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(priseEnChargeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(priseEnChargeMapper.fromId(null)).isNull();
    }
}
