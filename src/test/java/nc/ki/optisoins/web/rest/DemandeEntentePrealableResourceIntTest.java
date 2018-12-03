package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.DemandeEntentePrealable;
import nc.ki.optisoins.repository.DemandeEntentePrealableRepository;
import nc.ki.optisoins.service.DemandeEntentePrealableService;
import nc.ki.optisoins.service.dto.DemandeEntentePrealableDTO;
import nc.ki.optisoins.service.mapper.DemandeEntentePrealableMapper;
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

/**
 * Test class for the DemandeEntentePrealableResource REST controller.
 *
 * @see DemandeEntentePrealableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class DemandeEntentePrealableResourceIntTest {

    private static final String DEFAULT_NUMERO_ACP = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_ACP = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_SEANCES = 1;
    private static final Integer UPDATED_NOMBRE_SEANCES = 2;

    @Autowired
    private DemandeEntentePrealableRepository demandeEntentePrealableRepository;

    @Autowired
    private DemandeEntentePrealableMapper demandeEntentePrealableMapper;

    @Autowired
    private DemandeEntentePrealableService demandeEntentePrealableService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDemandeEntentePrealableMockMvc;

    private DemandeEntentePrealable demandeEntentePrealable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DemandeEntentePrealableResource demandeEntentePrealableResource = new DemandeEntentePrealableResource(demandeEntentePrealableService);
        this.restDemandeEntentePrealableMockMvc = MockMvcBuilders.standaloneSetup(demandeEntentePrealableResource)
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
    public static DemandeEntentePrealable createEntity(EntityManager em) {
        DemandeEntentePrealable demandeEntentePrealable = new DemandeEntentePrealable()
            .numeroACP(DEFAULT_NUMERO_ACP)
            .nombreSeances(DEFAULT_NOMBRE_SEANCES);
        return demandeEntentePrealable;
    }

    @Before
    public void initTest() {
        demandeEntentePrealable = createEntity(em);
    }

    @Test
    @Transactional
    public void createDemandeEntentePrealable() throws Exception {
        int databaseSizeBeforeCreate = demandeEntentePrealableRepository.findAll().size();

        // Create the DemandeEntentePrealable
        DemandeEntentePrealableDTO demandeEntentePrealableDTO = demandeEntentePrealableMapper.toDto(demandeEntentePrealable);
        restDemandeEntentePrealableMockMvc.perform(post("/api/demande-entente-prealables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeEntentePrealableDTO)))
            .andExpect(status().isCreated());

        // Validate the DemandeEntentePrealable in the database
        List<DemandeEntentePrealable> demandeEntentePrealableList = demandeEntentePrealableRepository.findAll();
        assertThat(demandeEntentePrealableList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeEntentePrealable testDemandeEntentePrealable = demandeEntentePrealableList.get(demandeEntentePrealableList.size() - 1);
        assertThat(testDemandeEntentePrealable.getNumeroACP()).isEqualTo(DEFAULT_NUMERO_ACP);
        assertThat(testDemandeEntentePrealable.getNombreSeances()).isEqualTo(DEFAULT_NOMBRE_SEANCES);
    }

    @Test
    @Transactional
    public void createDemandeEntentePrealableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = demandeEntentePrealableRepository.findAll().size();

        // Create the DemandeEntentePrealable with an existing ID
        demandeEntentePrealable.setId(1L);
        DemandeEntentePrealableDTO demandeEntentePrealableDTO = demandeEntentePrealableMapper.toDto(demandeEntentePrealable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeEntentePrealableMockMvc.perform(post("/api/demande-entente-prealables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeEntentePrealableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DemandeEntentePrealable in the database
        List<DemandeEntentePrealable> demandeEntentePrealableList = demandeEntentePrealableRepository.findAll();
        assertThat(demandeEntentePrealableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroACPIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeEntentePrealableRepository.findAll().size();
        // set the field null
        demandeEntentePrealable.setNumeroACP(null);

        // Create the DemandeEntentePrealable, which fails.
        DemandeEntentePrealableDTO demandeEntentePrealableDTO = demandeEntentePrealableMapper.toDto(demandeEntentePrealable);

        restDemandeEntentePrealableMockMvc.perform(post("/api/demande-entente-prealables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeEntentePrealableDTO)))
            .andExpect(status().isBadRequest());

        List<DemandeEntentePrealable> demandeEntentePrealableList = demandeEntentePrealableRepository.findAll();
        assertThat(demandeEntentePrealableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreSeancesIsRequired() throws Exception {
        int databaseSizeBeforeTest = demandeEntentePrealableRepository.findAll().size();
        // set the field null
        demandeEntentePrealable.setNombreSeances(null);

        // Create the DemandeEntentePrealable, which fails.
        DemandeEntentePrealableDTO demandeEntentePrealableDTO = demandeEntentePrealableMapper.toDto(demandeEntentePrealable);

        restDemandeEntentePrealableMockMvc.perform(post("/api/demande-entente-prealables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeEntentePrealableDTO)))
            .andExpect(status().isBadRequest());

        List<DemandeEntentePrealable> demandeEntentePrealableList = demandeEntentePrealableRepository.findAll();
        assertThat(demandeEntentePrealableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDemandeEntentePrealables() throws Exception {
        // Initialize the database
        demandeEntentePrealableRepository.saveAndFlush(demandeEntentePrealable);

        // Get all the demandeEntentePrealableList
        restDemandeEntentePrealableMockMvc.perform(get("/api/demande-entente-prealables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeEntentePrealable.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroACP").value(hasItem(DEFAULT_NUMERO_ACP.toString())))
            .andExpect(jsonPath("$.[*].nombreSeances").value(hasItem(DEFAULT_NOMBRE_SEANCES)));
    }
    
    @Test
    @Transactional
    public void getDemandeEntentePrealable() throws Exception {
        // Initialize the database
        demandeEntentePrealableRepository.saveAndFlush(demandeEntentePrealable);

        // Get the demandeEntentePrealable
        restDemandeEntentePrealableMockMvc.perform(get("/api/demande-entente-prealables/{id}", demandeEntentePrealable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(demandeEntentePrealable.getId().intValue()))
            .andExpect(jsonPath("$.numeroACP").value(DEFAULT_NUMERO_ACP.toString()))
            .andExpect(jsonPath("$.nombreSeances").value(DEFAULT_NOMBRE_SEANCES));
    }

    @Test
    @Transactional
    public void getNonExistingDemandeEntentePrealable() throws Exception {
        // Get the demandeEntentePrealable
        restDemandeEntentePrealableMockMvc.perform(get("/api/demande-entente-prealables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDemandeEntentePrealable() throws Exception {
        // Initialize the database
        demandeEntentePrealableRepository.saveAndFlush(demandeEntentePrealable);

        int databaseSizeBeforeUpdate = demandeEntentePrealableRepository.findAll().size();

        // Update the demandeEntentePrealable
        DemandeEntentePrealable updatedDemandeEntentePrealable = demandeEntentePrealableRepository.findById(demandeEntentePrealable.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeEntentePrealable are not directly saved in db
        em.detach(updatedDemandeEntentePrealable);
        updatedDemandeEntentePrealable
            .numeroACP(UPDATED_NUMERO_ACP)
            .nombreSeances(UPDATED_NOMBRE_SEANCES);
        DemandeEntentePrealableDTO demandeEntentePrealableDTO = demandeEntentePrealableMapper.toDto(updatedDemandeEntentePrealable);

        restDemandeEntentePrealableMockMvc.perform(put("/api/demande-entente-prealables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeEntentePrealableDTO)))
            .andExpect(status().isOk());

        // Validate the DemandeEntentePrealable in the database
        List<DemandeEntentePrealable> demandeEntentePrealableList = demandeEntentePrealableRepository.findAll();
        assertThat(demandeEntentePrealableList).hasSize(databaseSizeBeforeUpdate);
        DemandeEntentePrealable testDemandeEntentePrealable = demandeEntentePrealableList.get(demandeEntentePrealableList.size() - 1);
        assertThat(testDemandeEntentePrealable.getNumeroACP()).isEqualTo(UPDATED_NUMERO_ACP);
        assertThat(testDemandeEntentePrealable.getNombreSeances()).isEqualTo(UPDATED_NOMBRE_SEANCES);
    }

    @Test
    @Transactional
    public void updateNonExistingDemandeEntentePrealable() throws Exception {
        int databaseSizeBeforeUpdate = demandeEntentePrealableRepository.findAll().size();

        // Create the DemandeEntentePrealable
        DemandeEntentePrealableDTO demandeEntentePrealableDTO = demandeEntentePrealableMapper.toDto(demandeEntentePrealable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeEntentePrealableMockMvc.perform(put("/api/demande-entente-prealables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandeEntentePrealableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DemandeEntentePrealable in the database
        List<DemandeEntentePrealable> demandeEntentePrealableList = demandeEntentePrealableRepository.findAll();
        assertThat(demandeEntentePrealableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDemandeEntentePrealable() throws Exception {
        // Initialize the database
        demandeEntentePrealableRepository.saveAndFlush(demandeEntentePrealable);

        int databaseSizeBeforeDelete = demandeEntentePrealableRepository.findAll().size();

        // Get the demandeEntentePrealable
        restDemandeEntentePrealableMockMvc.perform(delete("/api/demande-entente-prealables/{id}", demandeEntentePrealable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DemandeEntentePrealable> demandeEntentePrealableList = demandeEntentePrealableRepository.findAll();
        assertThat(demandeEntentePrealableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeEntentePrealable.class);
        DemandeEntentePrealable demandeEntentePrealable1 = new DemandeEntentePrealable();
        demandeEntentePrealable1.setId(1L);
        DemandeEntentePrealable demandeEntentePrealable2 = new DemandeEntentePrealable();
        demandeEntentePrealable2.setId(demandeEntentePrealable1.getId());
        assertThat(demandeEntentePrealable1).isEqualTo(demandeEntentePrealable2);
        demandeEntentePrealable2.setId(2L);
        assertThat(demandeEntentePrealable1).isNotEqualTo(demandeEntentePrealable2);
        demandeEntentePrealable1.setId(null);
        assertThat(demandeEntentePrealable1).isNotEqualTo(demandeEntentePrealable2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeEntentePrealableDTO.class);
        DemandeEntentePrealableDTO demandeEntentePrealableDTO1 = new DemandeEntentePrealableDTO();
        demandeEntentePrealableDTO1.setId(1L);
        DemandeEntentePrealableDTO demandeEntentePrealableDTO2 = new DemandeEntentePrealableDTO();
        assertThat(demandeEntentePrealableDTO1).isNotEqualTo(demandeEntentePrealableDTO2);
        demandeEntentePrealableDTO2.setId(demandeEntentePrealableDTO1.getId());
        assertThat(demandeEntentePrealableDTO1).isEqualTo(demandeEntentePrealableDTO2);
        demandeEntentePrealableDTO2.setId(2L);
        assertThat(demandeEntentePrealableDTO1).isNotEqualTo(demandeEntentePrealableDTO2);
        demandeEntentePrealableDTO1.setId(null);
        assertThat(demandeEntentePrealableDTO1).isNotEqualTo(demandeEntentePrealableDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(demandeEntentePrealableMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(demandeEntentePrealableMapper.fromId(null)).isNull();
    }
}
