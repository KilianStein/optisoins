package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Courriel;
import nc.ki.optisoins.repository.CourrielRepository;
import nc.ki.optisoins.service.CourrielService;
import nc.ki.optisoins.service.dto.CourrielDTO;
import nc.ki.optisoins.service.mapper.CourrielMapper;
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
import java.util.List;


import static nc.ki.optisoins.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import nc.ki.optisoins.domain.enumeration.TypeCourriel;
/**
 * Test class for the CourrielResource REST controller.
 *
 * @see CourrielResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class CourrielResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final TypeCourriel DEFAULT_TYPE = TypeCourriel.PERSONNEL;
    private static final TypeCourriel UPDATED_TYPE = TypeCourriel.PROFESSIONEL;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    @Autowired
    private CourrielRepository courrielRepository;

    @Autowired
    private CourrielMapper courrielMapper;

    @Autowired
    private CourrielService courrielService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCourrielMockMvc;

    private Courriel courriel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourrielResource courrielResource = new CourrielResource(courrielService);
        this.restCourrielMockMvc = MockMvcBuilders.standaloneSetup(courrielResource)
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
    public static Courriel createEntity(EntityManager em) {
        Courriel courriel = new Courriel()
            .email(DEFAULT_EMAIL)
            .type(DEFAULT_TYPE)
            .commentaire(DEFAULT_COMMENTAIRE);
        return courriel;
    }

    @Before
    public void initTest() {
        courriel = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourriel() throws Exception {
        int databaseSizeBeforeCreate = courrielRepository.findAll().size();

        // Create the Courriel
        CourrielDTO courrielDTO = courrielMapper.toDto(courriel);
        restCourrielMockMvc.perform(post("/api/courriels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courrielDTO)))
            .andExpect(status().isCreated());

        // Validate the Courriel in the database
        List<Courriel> courrielList = courrielRepository.findAll();
        assertThat(courrielList).hasSize(databaseSizeBeforeCreate + 1);
        Courriel testCourriel = courrielList.get(courrielList.size() - 1);
        assertThat(testCourriel.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCourriel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCourriel.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void createCourrielWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courrielRepository.findAll().size();

        // Create the Courriel with an existing ID
        courriel.setId(1L);
        CourrielDTO courrielDTO = courrielMapper.toDto(courriel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourrielMockMvc.perform(post("/api/courriels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courrielDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Courriel in the database
        List<Courriel> courrielList = courrielRepository.findAll();
        assertThat(courrielList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = courrielRepository.findAll().size();
        // set the field null
        courriel.setEmail(null);

        // Create the Courriel, which fails.
        CourrielDTO courrielDTO = courrielMapper.toDto(courriel);

        restCourrielMockMvc.perform(post("/api/courriels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courrielDTO)))
            .andExpect(status().isBadRequest());

        List<Courriel> courrielList = courrielRepository.findAll();
        assertThat(courrielList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = courrielRepository.findAll().size();
        // set the field null
        courriel.setType(null);

        // Create the Courriel, which fails.
        CourrielDTO courrielDTO = courrielMapper.toDto(courriel);

        restCourrielMockMvc.perform(post("/api/courriels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courrielDTO)))
            .andExpect(status().isBadRequest());

        List<Courriel> courrielList = courrielRepository.findAll();
        assertThat(courrielList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourriels() throws Exception {
        // Initialize the database
        courrielRepository.saveAndFlush(courriel);

        // Get all the courrielList
        restCourrielMockMvc.perform(get("/api/courriels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courriel.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())));
    }
    
    @Test
    @Transactional
    public void getCourriel() throws Exception {
        // Initialize the database
        courrielRepository.saveAndFlush(courriel);

        // Get the courriel
        restCourrielMockMvc.perform(get("/api/courriels/{id}", courriel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courriel.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourriel() throws Exception {
        // Get the courriel
        restCourrielMockMvc.perform(get("/api/courriels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourriel() throws Exception {
        // Initialize the database
        courrielRepository.saveAndFlush(courriel);

        int databaseSizeBeforeUpdate = courrielRepository.findAll().size();

        // Update the courriel
        Courriel updatedCourriel = courrielRepository.findById(courriel.getId()).get();
        // Disconnect from session so that the updates on updatedCourriel are not directly saved in db
        em.detach(updatedCourriel);
        updatedCourriel
            .email(UPDATED_EMAIL)
            .type(UPDATED_TYPE)
            .commentaire(UPDATED_COMMENTAIRE);
        CourrielDTO courrielDTO = courrielMapper.toDto(updatedCourriel);

        restCourrielMockMvc.perform(put("/api/courriels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courrielDTO)))
            .andExpect(status().isOk());

        // Validate the Courriel in the database
        List<Courriel> courrielList = courrielRepository.findAll();
        assertThat(courrielList).hasSize(databaseSizeBeforeUpdate);
        Courriel testCourriel = courrielList.get(courrielList.size() - 1);
        assertThat(testCourriel.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCourriel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCourriel.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingCourriel() throws Exception {
        int databaseSizeBeforeUpdate = courrielRepository.findAll().size();

        // Create the Courriel
        CourrielDTO courrielDTO = courrielMapper.toDto(courriel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourrielMockMvc.perform(put("/api/courriels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courrielDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Courriel in the database
        List<Courriel> courrielList = courrielRepository.findAll();
        assertThat(courrielList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourriel() throws Exception {
        // Initialize the database
        courrielRepository.saveAndFlush(courriel);

        int databaseSizeBeforeDelete = courrielRepository.findAll().size();

        // Get the courriel
        restCourrielMockMvc.perform(delete("/api/courriels/{id}", courriel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Courriel> courrielList = courrielRepository.findAll();
        assertThat(courrielList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Courriel.class);
        Courriel courriel1 = new Courriel();
        courriel1.setId(1L);
        Courriel courriel2 = new Courriel();
        courriel2.setId(courriel1.getId());
        assertThat(courriel1).isEqualTo(courriel2);
        courriel2.setId(2L);
        assertThat(courriel1).isNotEqualTo(courriel2);
        courriel1.setId(null);
        assertThat(courriel1).isNotEqualTo(courriel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourrielDTO.class);
        CourrielDTO courrielDTO1 = new CourrielDTO();
        courrielDTO1.setId(1L);
        CourrielDTO courrielDTO2 = new CourrielDTO();
        assertThat(courrielDTO1).isNotEqualTo(courrielDTO2);
        courrielDTO2.setId(courrielDTO1.getId());
        assertThat(courrielDTO1).isEqualTo(courrielDTO2);
        courrielDTO2.setId(2L);
        assertThat(courrielDTO1).isNotEqualTo(courrielDTO2);
        courrielDTO1.setId(null);
        assertThat(courrielDTO1).isNotEqualTo(courrielDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(courrielMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(courrielMapper.fromId(null)).isNull();
    }
}
