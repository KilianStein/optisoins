package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Assure;
import nc.ki.optisoins.repository.AssureRepository;
import nc.ki.optisoins.service.AssureService;
import nc.ki.optisoins.service.dto.AssureDTO;
import nc.ki.optisoins.service.mapper.AssureMapper;
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

import nc.ki.optisoins.domain.enumeration.TypeLienAssure;
/**
 * Test class for the AssureResource REST controller.
 *
 * @see AssureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class AssureResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CAFAT = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CAFAT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final TypeLienAssure DEFAULT_LIEN_ASSURE = TypeLienAssure.CONJOINT;
    private static final TypeLienAssure UPDATED_LIEN_ASSURE = TypeLienAssure.CONCUBIN;

    @Autowired
    private AssureRepository assureRepository;

    @Autowired
    private AssureMapper assureMapper;

    @Autowired
    private AssureService assureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssureMockMvc;

    private Assure assure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssureResource assureResource = new AssureResource(assureService);
        this.restAssureMockMvc = MockMvcBuilders.standaloneSetup(assureResource)
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
    public static Assure createEntity(EntityManager em) {
        Assure assure = new Assure()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .numeroCafat(DEFAULT_NUMERO_CAFAT)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lienAssure(DEFAULT_LIEN_ASSURE);
        return assure;
    }

    @Before
    public void initTest() {
        assure = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssure() throws Exception {
        int databaseSizeBeforeCreate = assureRepository.findAll().size();

        // Create the Assure
        AssureDTO assureDTO = assureMapper.toDto(assure);
        restAssureMockMvc.perform(post("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isCreated());

        // Validate the Assure in the database
        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeCreate + 1);
        Assure testAssure = assureList.get(assureList.size() - 1);
        assertThat(testAssure.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAssure.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAssure.getNumeroCafat()).isEqualTo(DEFAULT_NUMERO_CAFAT);
        assertThat(testAssure.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testAssure.getLienAssure()).isEqualTo(DEFAULT_LIEN_ASSURE);
    }

    @Test
    @Transactional
    public void createAssureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assureRepository.findAll().size();

        // Create the Assure with an existing ID
        assure.setId(1L);
        AssureDTO assureDTO = assureMapper.toDto(assure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssureMockMvc.perform(post("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assure in the database
        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = assureRepository.findAll().size();
        // set the field null
        assure.setNom(null);

        // Create the Assure, which fails.
        AssureDTO assureDTO = assureMapper.toDto(assure);

        restAssureMockMvc.perform(post("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isBadRequest());

        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = assureRepository.findAll().size();
        // set the field null
        assure.setPrenom(null);

        // Create the Assure, which fails.
        AssureDTO assureDTO = assureMapper.toDto(assure);

        restAssureMockMvc.perform(post("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isBadRequest());

        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroCafatIsRequired() throws Exception {
        int databaseSizeBeforeTest = assureRepository.findAll().size();
        // set the field null
        assure.setNumeroCafat(null);

        // Create the Assure, which fails.
        AssureDTO assureDTO = assureMapper.toDto(assure);

        restAssureMockMvc.perform(post("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isBadRequest());

        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssures() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);

        // Get all the assureList
        restAssureMockMvc.perform(get("/api/assures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assure.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].numeroCafat").value(hasItem(DEFAULT_NUMERO_CAFAT.toString())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lienAssure").value(hasItem(DEFAULT_LIEN_ASSURE.toString())));
    }
    
    @Test
    @Transactional
    public void getAssure() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);

        // Get the assure
        restAssureMockMvc.perform(get("/api/assures/{id}", assure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assure.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.numeroCafat").value(DEFAULT_NUMERO_CAFAT.toString()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lienAssure").value(DEFAULT_LIEN_ASSURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssure() throws Exception {
        // Get the assure
        restAssureMockMvc.perform(get("/api/assures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssure() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);

        int databaseSizeBeforeUpdate = assureRepository.findAll().size();

        // Update the assure
        Assure updatedAssure = assureRepository.findById(assure.getId()).get();
        // Disconnect from session so that the updates on updatedAssure are not directly saved in db
        em.detach(updatedAssure);
        updatedAssure
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .numeroCafat(UPDATED_NUMERO_CAFAT)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lienAssure(UPDATED_LIEN_ASSURE);
        AssureDTO assureDTO = assureMapper.toDto(updatedAssure);

        restAssureMockMvc.perform(put("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isOk());

        // Validate the Assure in the database
        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeUpdate);
        Assure testAssure = assureList.get(assureList.size() - 1);
        assertThat(testAssure.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAssure.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAssure.getNumeroCafat()).isEqualTo(UPDATED_NUMERO_CAFAT);
        assertThat(testAssure.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testAssure.getLienAssure()).isEqualTo(UPDATED_LIEN_ASSURE);
    }

    @Test
    @Transactional
    public void updateNonExistingAssure() throws Exception {
        int databaseSizeBeforeUpdate = assureRepository.findAll().size();

        // Create the Assure
        AssureDTO assureDTO = assureMapper.toDto(assure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssureMockMvc.perform(put("/api/assures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assure in the database
        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssure() throws Exception {
        // Initialize the database
        assureRepository.saveAndFlush(assure);

        int databaseSizeBeforeDelete = assureRepository.findAll().size();

        // Get the assure
        restAssureMockMvc.perform(delete("/api/assures/{id}", assure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Assure> assureList = assureRepository.findAll();
        assertThat(assureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assure.class);
        Assure assure1 = new Assure();
        assure1.setId(1L);
        Assure assure2 = new Assure();
        assure2.setId(assure1.getId());
        assertThat(assure1).isEqualTo(assure2);
        assure2.setId(2L);
        assertThat(assure1).isNotEqualTo(assure2);
        assure1.setId(null);
        assertThat(assure1).isNotEqualTo(assure2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssureDTO.class);
        AssureDTO assureDTO1 = new AssureDTO();
        assureDTO1.setId(1L);
        AssureDTO assureDTO2 = new AssureDTO();
        assertThat(assureDTO1).isNotEqualTo(assureDTO2);
        assureDTO2.setId(assureDTO1.getId());
        assertThat(assureDTO1).isEqualTo(assureDTO2);
        assureDTO2.setId(2L);
        assertThat(assureDTO1).isNotEqualTo(assureDTO2);
        assureDTO1.setId(null);
        assertThat(assureDTO1).isNotEqualTo(assureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(assureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(assureMapper.fromId(null)).isNull();
    }
}
