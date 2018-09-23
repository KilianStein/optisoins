package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.Remplacante;
import nc.ki.optisoins.repository.RemplacanteRepository;
import nc.ki.optisoins.service.RemplacanteService;
import nc.ki.optisoins.service.dto.RemplacanteDTO;
import nc.ki.optisoins.service.mapper.RemplacanteMapper;
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
 * Test class for the RemplacanteResource REST controller.
 *
 * @see RemplacanteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class RemplacanteResourceIntTest {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RemplacanteRepository remplacanteRepository;

    @Autowired
    private RemplacanteMapper remplacanteMapper;
    
    @Autowired
    private RemplacanteService remplacanteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRemplacanteMockMvc;

    private Remplacante remplacante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RemplacanteResource remplacanteResource = new RemplacanteResource(remplacanteService);
        this.restRemplacanteMockMvc = MockMvcBuilders.standaloneSetup(remplacanteResource)
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
    public static Remplacante createEntity(EntityManager em) {
        Remplacante remplacante = new Remplacante()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN);
        return remplacante;
    }

    @Before
    public void initTest() {
        remplacante = createEntity(em);
    }

    @Test
    @Transactional
    public void createRemplacante() throws Exception {
        int databaseSizeBeforeCreate = remplacanteRepository.findAll().size();

        // Create the Remplacante
        RemplacanteDTO remplacanteDTO = remplacanteMapper.toDto(remplacante);
        restRemplacanteMockMvc.perform(post("/api/remplacantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remplacanteDTO)))
            .andExpect(status().isCreated());

        // Validate the Remplacante in the database
        List<Remplacante> remplacanteList = remplacanteRepository.findAll();
        assertThat(remplacanteList).hasSize(databaseSizeBeforeCreate + 1);
        Remplacante testRemplacante = remplacanteList.get(remplacanteList.size() - 1);
        assertThat(testRemplacante.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testRemplacante.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    public void createRemplacanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = remplacanteRepository.findAll().size();

        // Create the Remplacante with an existing ID
        remplacante.setId(1L);
        RemplacanteDTO remplacanteDTO = remplacanteMapper.toDto(remplacante);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemplacanteMockMvc.perform(post("/api/remplacantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remplacanteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Remplacante in the database
        List<Remplacante> remplacanteList = remplacanteRepository.findAll();
        assertThat(remplacanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = remplacanteRepository.findAll().size();
        // set the field null
        remplacante.setDateDebut(null);

        // Create the Remplacante, which fails.
        RemplacanteDTO remplacanteDTO = remplacanteMapper.toDto(remplacante);

        restRemplacanteMockMvc.perform(post("/api/remplacantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remplacanteDTO)))
            .andExpect(status().isBadRequest());

        List<Remplacante> remplacanteList = remplacanteRepository.findAll();
        assertThat(remplacanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = remplacanteRepository.findAll().size();
        // set the field null
        remplacante.setDateFin(null);

        // Create the Remplacante, which fails.
        RemplacanteDTO remplacanteDTO = remplacanteMapper.toDto(remplacante);

        restRemplacanteMockMvc.perform(post("/api/remplacantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remplacanteDTO)))
            .andExpect(status().isBadRequest());

        List<Remplacante> remplacanteList = remplacanteRepository.findAll();
        assertThat(remplacanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRemplacantes() throws Exception {
        // Initialize the database
        remplacanteRepository.saveAndFlush(remplacante);

        // Get all the remplacanteList
        restRemplacanteMockMvc.perform(get("/api/remplacantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remplacante.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }
    
    @Test
    @Transactional
    public void getRemplacante() throws Exception {
        // Initialize the database
        remplacanteRepository.saveAndFlush(remplacante);

        // Get the remplacante
        restRemplacanteMockMvc.perform(get("/api/remplacantes/{id}", remplacante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(remplacante.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRemplacante() throws Exception {
        // Get the remplacante
        restRemplacanteMockMvc.perform(get("/api/remplacantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRemplacante() throws Exception {
        // Initialize the database
        remplacanteRepository.saveAndFlush(remplacante);

        int databaseSizeBeforeUpdate = remplacanteRepository.findAll().size();

        // Update the remplacante
        Remplacante updatedRemplacante = remplacanteRepository.findById(remplacante.getId()).get();
        // Disconnect from session so that the updates on updatedRemplacante are not directly saved in db
        em.detach(updatedRemplacante);
        updatedRemplacante
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);
        RemplacanteDTO remplacanteDTO = remplacanteMapper.toDto(updatedRemplacante);

        restRemplacanteMockMvc.perform(put("/api/remplacantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remplacanteDTO)))
            .andExpect(status().isOk());

        // Validate the Remplacante in the database
        List<Remplacante> remplacanteList = remplacanteRepository.findAll();
        assertThat(remplacanteList).hasSize(databaseSizeBeforeUpdate);
        Remplacante testRemplacante = remplacanteList.get(remplacanteList.size() - 1);
        assertThat(testRemplacante.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testRemplacante.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingRemplacante() throws Exception {
        int databaseSizeBeforeUpdate = remplacanteRepository.findAll().size();

        // Create the Remplacante
        RemplacanteDTO remplacanteDTO = remplacanteMapper.toDto(remplacante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemplacanteMockMvc.perform(put("/api/remplacantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remplacanteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Remplacante in the database
        List<Remplacante> remplacanteList = remplacanteRepository.findAll();
        assertThat(remplacanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRemplacante() throws Exception {
        // Initialize the database
        remplacanteRepository.saveAndFlush(remplacante);

        int databaseSizeBeforeDelete = remplacanteRepository.findAll().size();

        // Get the remplacante
        restRemplacanteMockMvc.perform(delete("/api/remplacantes/{id}", remplacante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Remplacante> remplacanteList = remplacanteRepository.findAll();
        assertThat(remplacanteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remplacante.class);
        Remplacante remplacante1 = new Remplacante();
        remplacante1.setId(1L);
        Remplacante remplacante2 = new Remplacante();
        remplacante2.setId(remplacante1.getId());
        assertThat(remplacante1).isEqualTo(remplacante2);
        remplacante2.setId(2L);
        assertThat(remplacante1).isNotEqualTo(remplacante2);
        remplacante1.setId(null);
        assertThat(remplacante1).isNotEqualTo(remplacante2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemplacanteDTO.class);
        RemplacanteDTO remplacanteDTO1 = new RemplacanteDTO();
        remplacanteDTO1.setId(1L);
        RemplacanteDTO remplacanteDTO2 = new RemplacanteDTO();
        assertThat(remplacanteDTO1).isNotEqualTo(remplacanteDTO2);
        remplacanteDTO2.setId(remplacanteDTO1.getId());
        assertThat(remplacanteDTO1).isEqualTo(remplacanteDTO2);
        remplacanteDTO2.setId(2L);
        assertThat(remplacanteDTO1).isNotEqualTo(remplacanteDTO2);
        remplacanteDTO1.setId(null);
        assertThat(remplacanteDTO1).isNotEqualTo(remplacanteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(remplacanteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(remplacanteMapper.fromId(null)).isNull();
    }
}
