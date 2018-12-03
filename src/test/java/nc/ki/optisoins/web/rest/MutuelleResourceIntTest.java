package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Mutuelle;
import nc.ki.optisoins.repository.MutuelleRepository;
import nc.ki.optisoins.service.MutuelleService;
import nc.ki.optisoins.service.dto.MutuelleDTO;
import nc.ki.optisoins.service.mapper.MutuelleMapper;
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

import nc.ki.optisoins.domain.enumeration.TypeMutuelle;
/**
 * Test class for the MutuelleResource REST controller.
 *
 * @see MutuelleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class MutuelleResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final TypeMutuelle DEFAULT_TYPE = TypeMutuelle.PRINCIPAL;
    private static final TypeMutuelle UPDATED_TYPE = TypeMutuelle.SECONDAIRE;

    @Autowired
    private MutuelleRepository mutuelleRepository;

    @Autowired
    private MutuelleMapper mutuelleMapper;

    @Autowired
    private MutuelleService mutuelleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMutuelleMockMvc;

    private Mutuelle mutuelle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MutuelleResource mutuelleResource = new MutuelleResource(mutuelleService);
        this.restMutuelleMockMvc = MockMvcBuilders.standaloneSetup(mutuelleResource)
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
    public static Mutuelle createEntity(EntityManager em) {
        Mutuelle mutuelle = new Mutuelle()
            .nom(DEFAULT_NOM)
            .numero(DEFAULT_NUMERO)
            .type(DEFAULT_TYPE);
        return mutuelle;
    }

    @Before
    public void initTest() {
        mutuelle = createEntity(em);
    }

    @Test
    @Transactional
    public void createMutuelle() throws Exception {
        int databaseSizeBeforeCreate = mutuelleRepository.findAll().size();

        // Create the Mutuelle
        MutuelleDTO mutuelleDTO = mutuelleMapper.toDto(mutuelle);
        restMutuelleMockMvc.perform(post("/api/mutuelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutuelleDTO)))
            .andExpect(status().isCreated());

        // Validate the Mutuelle in the database
        List<Mutuelle> mutuelleList = mutuelleRepository.findAll();
        assertThat(mutuelleList).hasSize(databaseSizeBeforeCreate + 1);
        Mutuelle testMutuelle = mutuelleList.get(mutuelleList.size() - 1);
        assertThat(testMutuelle.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMutuelle.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testMutuelle.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createMutuelleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mutuelleRepository.findAll().size();

        // Create the Mutuelle with an existing ID
        mutuelle.setId(1L);
        MutuelleDTO mutuelleDTO = mutuelleMapper.toDto(mutuelle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMutuelleMockMvc.perform(post("/api/mutuelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutuelleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mutuelle in the database
        List<Mutuelle> mutuelleList = mutuelleRepository.findAll();
        assertThat(mutuelleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = mutuelleRepository.findAll().size();
        // set the field null
        mutuelle.setNom(null);

        // Create the Mutuelle, which fails.
        MutuelleDTO mutuelleDTO = mutuelleMapper.toDto(mutuelle);

        restMutuelleMockMvc.perform(post("/api/mutuelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutuelleDTO)))
            .andExpect(status().isBadRequest());

        List<Mutuelle> mutuelleList = mutuelleRepository.findAll();
        assertThat(mutuelleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = mutuelleRepository.findAll().size();
        // set the field null
        mutuelle.setNumero(null);

        // Create the Mutuelle, which fails.
        MutuelleDTO mutuelleDTO = mutuelleMapper.toDto(mutuelle);

        restMutuelleMockMvc.perform(post("/api/mutuelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutuelleDTO)))
            .andExpect(status().isBadRequest());

        List<Mutuelle> mutuelleList = mutuelleRepository.findAll();
        assertThat(mutuelleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mutuelleRepository.findAll().size();
        // set the field null
        mutuelle.setType(null);

        // Create the Mutuelle, which fails.
        MutuelleDTO mutuelleDTO = mutuelleMapper.toDto(mutuelle);

        restMutuelleMockMvc.perform(post("/api/mutuelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutuelleDTO)))
            .andExpect(status().isBadRequest());

        List<Mutuelle> mutuelleList = mutuelleRepository.findAll();
        assertThat(mutuelleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMutuelles() throws Exception {
        // Initialize the database
        mutuelleRepository.saveAndFlush(mutuelle);

        // Get all the mutuelleList
        restMutuelleMockMvc.perform(get("/api/mutuelles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mutuelle.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getMutuelle() throws Exception {
        // Initialize the database
        mutuelleRepository.saveAndFlush(mutuelle);

        // Get the mutuelle
        restMutuelleMockMvc.perform(get("/api/mutuelles/{id}", mutuelle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mutuelle.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMutuelle() throws Exception {
        // Get the mutuelle
        restMutuelleMockMvc.perform(get("/api/mutuelles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMutuelle() throws Exception {
        // Initialize the database
        mutuelleRepository.saveAndFlush(mutuelle);

        int databaseSizeBeforeUpdate = mutuelleRepository.findAll().size();

        // Update the mutuelle
        Mutuelle updatedMutuelle = mutuelleRepository.findById(mutuelle.getId()).get();
        // Disconnect from session so that the updates on updatedMutuelle are not directly saved in db
        em.detach(updatedMutuelle);
        updatedMutuelle
            .nom(UPDATED_NOM)
            .numero(UPDATED_NUMERO)
            .type(UPDATED_TYPE);
        MutuelleDTO mutuelleDTO = mutuelleMapper.toDto(updatedMutuelle);

        restMutuelleMockMvc.perform(put("/api/mutuelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutuelleDTO)))
            .andExpect(status().isOk());

        // Validate the Mutuelle in the database
        List<Mutuelle> mutuelleList = mutuelleRepository.findAll();
        assertThat(mutuelleList).hasSize(databaseSizeBeforeUpdate);
        Mutuelle testMutuelle = mutuelleList.get(mutuelleList.size() - 1);
        assertThat(testMutuelle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMutuelle.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testMutuelle.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMutuelle() throws Exception {
        int databaseSizeBeforeUpdate = mutuelleRepository.findAll().size();

        // Create the Mutuelle
        MutuelleDTO mutuelleDTO = mutuelleMapper.toDto(mutuelle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMutuelleMockMvc.perform(put("/api/mutuelles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mutuelleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mutuelle in the database
        List<Mutuelle> mutuelleList = mutuelleRepository.findAll();
        assertThat(mutuelleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMutuelle() throws Exception {
        // Initialize the database
        mutuelleRepository.saveAndFlush(mutuelle);

        int databaseSizeBeforeDelete = mutuelleRepository.findAll().size();

        // Get the mutuelle
        restMutuelleMockMvc.perform(delete("/api/mutuelles/{id}", mutuelle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mutuelle> mutuelleList = mutuelleRepository.findAll();
        assertThat(mutuelleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mutuelle.class);
        Mutuelle mutuelle1 = new Mutuelle();
        mutuelle1.setId(1L);
        Mutuelle mutuelle2 = new Mutuelle();
        mutuelle2.setId(mutuelle1.getId());
        assertThat(mutuelle1).isEqualTo(mutuelle2);
        mutuelle2.setId(2L);
        assertThat(mutuelle1).isNotEqualTo(mutuelle2);
        mutuelle1.setId(null);
        assertThat(mutuelle1).isNotEqualTo(mutuelle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MutuelleDTO.class);
        MutuelleDTO mutuelleDTO1 = new MutuelleDTO();
        mutuelleDTO1.setId(1L);
        MutuelleDTO mutuelleDTO2 = new MutuelleDTO();
        assertThat(mutuelleDTO1).isNotEqualTo(mutuelleDTO2);
        mutuelleDTO2.setId(mutuelleDTO1.getId());
        assertThat(mutuelleDTO1).isEqualTo(mutuelleDTO2);
        mutuelleDTO2.setId(2L);
        assertThat(mutuelleDTO1).isNotEqualTo(mutuelleDTO2);
        mutuelleDTO1.setId(null);
        assertThat(mutuelleDTO1).isNotEqualTo(mutuelleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mutuelleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mutuelleMapper.fromId(null)).isNull();
    }
}
