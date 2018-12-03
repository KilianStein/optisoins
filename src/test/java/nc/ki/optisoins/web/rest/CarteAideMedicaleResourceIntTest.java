package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.CarteAideMedicale;
import nc.ki.optisoins.repository.CarteAideMedicaleRepository;
import nc.ki.optisoins.service.CarteAideMedicaleService;
import nc.ki.optisoins.service.dto.CarteAideMedicaleDTO;
import nc.ki.optisoins.service.mapper.CarteAideMedicaleMapper;
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
 * Test class for the CarteAideMedicaleResource REST controller.
 *
 * @see CarteAideMedicaleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class CarteAideMedicaleResourceIntTest {

    private static final LocalDate DEFAULT_DATE_DEBUT_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    @Autowired
    private CarteAideMedicaleRepository carteAideMedicaleRepository;

    @Autowired
    private CarteAideMedicaleMapper carteAideMedicaleMapper;

    @Autowired
    private CarteAideMedicaleService carteAideMedicaleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarteAideMedicaleMockMvc;

    private CarteAideMedicale carteAideMedicale;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarteAideMedicaleResource carteAideMedicaleResource = new CarteAideMedicaleResource(carteAideMedicaleService);
        this.restCarteAideMedicaleMockMvc = MockMvcBuilders.standaloneSetup(carteAideMedicaleResource)
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
    public static CarteAideMedicale createEntity(EntityManager em) {
        CarteAideMedicale carteAideMedicale = new CarteAideMedicale()
            .dateDebutValidite(DEFAULT_DATE_DEBUT_VALIDITE)
            .dateFinValidite(DEFAULT_DATE_FIN_VALIDITE)
            .numero(DEFAULT_NUMERO);
        return carteAideMedicale;
    }

    @Before
    public void initTest() {
        carteAideMedicale = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarteAideMedicale() throws Exception {
        int databaseSizeBeforeCreate = carteAideMedicaleRepository.findAll().size();

        // Create the CarteAideMedicale
        CarteAideMedicaleDTO carteAideMedicaleDTO = carteAideMedicaleMapper.toDto(carteAideMedicale);
        restCarteAideMedicaleMockMvc.perform(post("/api/carte-aide-medicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteAideMedicaleDTO)))
            .andExpect(status().isCreated());

        // Validate the CarteAideMedicale in the database
        List<CarteAideMedicale> carteAideMedicaleList = carteAideMedicaleRepository.findAll();
        assertThat(carteAideMedicaleList).hasSize(databaseSizeBeforeCreate + 1);
        CarteAideMedicale testCarteAideMedicale = carteAideMedicaleList.get(carteAideMedicaleList.size() - 1);
        assertThat(testCarteAideMedicale.getDateDebutValidite()).isEqualTo(DEFAULT_DATE_DEBUT_VALIDITE);
        assertThat(testCarteAideMedicale.getDateFinValidite()).isEqualTo(DEFAULT_DATE_FIN_VALIDITE);
        assertThat(testCarteAideMedicale.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createCarteAideMedicaleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carteAideMedicaleRepository.findAll().size();

        // Create the CarteAideMedicale with an existing ID
        carteAideMedicale.setId(1L);
        CarteAideMedicaleDTO carteAideMedicaleDTO = carteAideMedicaleMapper.toDto(carteAideMedicale);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarteAideMedicaleMockMvc.perform(post("/api/carte-aide-medicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteAideMedicaleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarteAideMedicale in the database
        List<CarteAideMedicale> carteAideMedicaleList = carteAideMedicaleRepository.findAll();
        assertThat(carteAideMedicaleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateDebutValiditeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteAideMedicaleRepository.findAll().size();
        // set the field null
        carteAideMedicale.setDateDebutValidite(null);

        // Create the CarteAideMedicale, which fails.
        CarteAideMedicaleDTO carteAideMedicaleDTO = carteAideMedicaleMapper.toDto(carteAideMedicale);

        restCarteAideMedicaleMockMvc.perform(post("/api/carte-aide-medicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteAideMedicaleDTO)))
            .andExpect(status().isBadRequest());

        List<CarteAideMedicale> carteAideMedicaleList = carteAideMedicaleRepository.findAll();
        assertThat(carteAideMedicaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinValiditeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteAideMedicaleRepository.findAll().size();
        // set the field null
        carteAideMedicale.setDateFinValidite(null);

        // Create the CarteAideMedicale, which fails.
        CarteAideMedicaleDTO carteAideMedicaleDTO = carteAideMedicaleMapper.toDto(carteAideMedicale);

        restCarteAideMedicaleMockMvc.perform(post("/api/carte-aide-medicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteAideMedicaleDTO)))
            .andExpect(status().isBadRequest());

        List<CarteAideMedicale> carteAideMedicaleList = carteAideMedicaleRepository.findAll();
        assertThat(carteAideMedicaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = carteAideMedicaleRepository.findAll().size();
        // set the field null
        carteAideMedicale.setNumero(null);

        // Create the CarteAideMedicale, which fails.
        CarteAideMedicaleDTO carteAideMedicaleDTO = carteAideMedicaleMapper.toDto(carteAideMedicale);

        restCarteAideMedicaleMockMvc.perform(post("/api/carte-aide-medicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteAideMedicaleDTO)))
            .andExpect(status().isBadRequest());

        List<CarteAideMedicale> carteAideMedicaleList = carteAideMedicaleRepository.findAll();
        assertThat(carteAideMedicaleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarteAideMedicales() throws Exception {
        // Initialize the database
        carteAideMedicaleRepository.saveAndFlush(carteAideMedicale);

        // Get all the carteAideMedicaleList
        restCarteAideMedicaleMockMvc.perform(get("/api/carte-aide-medicales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carteAideMedicale.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebutValidite").value(hasItem(DEFAULT_DATE_DEBUT_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].dateFinValidite").value(hasItem(DEFAULT_DATE_FIN_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())));
    }
    
    @Test
    @Transactional
    public void getCarteAideMedicale() throws Exception {
        // Initialize the database
        carteAideMedicaleRepository.saveAndFlush(carteAideMedicale);

        // Get the carteAideMedicale
        restCarteAideMedicaleMockMvc.perform(get("/api/carte-aide-medicales/{id}", carteAideMedicale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carteAideMedicale.getId().intValue()))
            .andExpect(jsonPath("$.dateDebutValidite").value(DEFAULT_DATE_DEBUT_VALIDITE.toString()))
            .andExpect(jsonPath("$.dateFinValidite").value(DEFAULT_DATE_FIN_VALIDITE.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarteAideMedicale() throws Exception {
        // Get the carteAideMedicale
        restCarteAideMedicaleMockMvc.perform(get("/api/carte-aide-medicales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarteAideMedicale() throws Exception {
        // Initialize the database
        carteAideMedicaleRepository.saveAndFlush(carteAideMedicale);

        int databaseSizeBeforeUpdate = carteAideMedicaleRepository.findAll().size();

        // Update the carteAideMedicale
        CarteAideMedicale updatedCarteAideMedicale = carteAideMedicaleRepository.findById(carteAideMedicale.getId()).get();
        // Disconnect from session so that the updates on updatedCarteAideMedicale are not directly saved in db
        em.detach(updatedCarteAideMedicale);
        updatedCarteAideMedicale
            .dateDebutValidite(UPDATED_DATE_DEBUT_VALIDITE)
            .dateFinValidite(UPDATED_DATE_FIN_VALIDITE)
            .numero(UPDATED_NUMERO);
        CarteAideMedicaleDTO carteAideMedicaleDTO = carteAideMedicaleMapper.toDto(updatedCarteAideMedicale);

        restCarteAideMedicaleMockMvc.perform(put("/api/carte-aide-medicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteAideMedicaleDTO)))
            .andExpect(status().isOk());

        // Validate the CarteAideMedicale in the database
        List<CarteAideMedicale> carteAideMedicaleList = carteAideMedicaleRepository.findAll();
        assertThat(carteAideMedicaleList).hasSize(databaseSizeBeforeUpdate);
        CarteAideMedicale testCarteAideMedicale = carteAideMedicaleList.get(carteAideMedicaleList.size() - 1);
        assertThat(testCarteAideMedicale.getDateDebutValidite()).isEqualTo(UPDATED_DATE_DEBUT_VALIDITE);
        assertThat(testCarteAideMedicale.getDateFinValidite()).isEqualTo(UPDATED_DATE_FIN_VALIDITE);
        assertThat(testCarteAideMedicale.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingCarteAideMedicale() throws Exception {
        int databaseSizeBeforeUpdate = carteAideMedicaleRepository.findAll().size();

        // Create the CarteAideMedicale
        CarteAideMedicaleDTO carteAideMedicaleDTO = carteAideMedicaleMapper.toDto(carteAideMedicale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteAideMedicaleMockMvc.perform(put("/api/carte-aide-medicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carteAideMedicaleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarteAideMedicale in the database
        List<CarteAideMedicale> carteAideMedicaleList = carteAideMedicaleRepository.findAll();
        assertThat(carteAideMedicaleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarteAideMedicale() throws Exception {
        // Initialize the database
        carteAideMedicaleRepository.saveAndFlush(carteAideMedicale);

        int databaseSizeBeforeDelete = carteAideMedicaleRepository.findAll().size();

        // Get the carteAideMedicale
        restCarteAideMedicaleMockMvc.perform(delete("/api/carte-aide-medicales/{id}", carteAideMedicale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CarteAideMedicale> carteAideMedicaleList = carteAideMedicaleRepository.findAll();
        assertThat(carteAideMedicaleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarteAideMedicale.class);
        CarteAideMedicale carteAideMedicale1 = new CarteAideMedicale();
        carteAideMedicale1.setId(1L);
        CarteAideMedicale carteAideMedicale2 = new CarteAideMedicale();
        carteAideMedicale2.setId(carteAideMedicale1.getId());
        assertThat(carteAideMedicale1).isEqualTo(carteAideMedicale2);
        carteAideMedicale2.setId(2L);
        assertThat(carteAideMedicale1).isNotEqualTo(carteAideMedicale2);
        carteAideMedicale1.setId(null);
        assertThat(carteAideMedicale1).isNotEqualTo(carteAideMedicale2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarteAideMedicaleDTO.class);
        CarteAideMedicaleDTO carteAideMedicaleDTO1 = new CarteAideMedicaleDTO();
        carteAideMedicaleDTO1.setId(1L);
        CarteAideMedicaleDTO carteAideMedicaleDTO2 = new CarteAideMedicaleDTO();
        assertThat(carteAideMedicaleDTO1).isNotEqualTo(carteAideMedicaleDTO2);
        carteAideMedicaleDTO2.setId(carteAideMedicaleDTO1.getId());
        assertThat(carteAideMedicaleDTO1).isEqualTo(carteAideMedicaleDTO2);
        carteAideMedicaleDTO2.setId(2L);
        assertThat(carteAideMedicaleDTO1).isNotEqualTo(carteAideMedicaleDTO2);
        carteAideMedicaleDTO1.setId(null);
        assertThat(carteAideMedicaleDTO1).isNotEqualTo(carteAideMedicaleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carteAideMedicaleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carteAideMedicaleMapper.fromId(null)).isNull();
    }
}
