package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.FeuilleSoins;
import nc.ki.optisoins.repository.FeuilleSoinsRepository;
import nc.ki.optisoins.service.FeuilleSoinsService;
import nc.ki.optisoins.service.dto.FeuilleSoinsDTO;
import nc.ki.optisoins.service.mapper.FeuilleSoinsMapper;
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
 * Test class for the FeuilleSoinsResource REST controller.
 *
 * @see FeuilleSoinsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class FeuilleSoinsResourceIntTest {

    @Autowired
    private FeuilleSoinsRepository feuilleSoinsRepository;

    @Autowired
    private FeuilleSoinsMapper feuilleSoinsMapper;

    @Autowired
    private FeuilleSoinsService feuilleSoinsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFeuilleSoinsMockMvc;

    private FeuilleSoins feuilleSoins;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeuilleSoinsResource feuilleSoinsResource = new FeuilleSoinsResource(feuilleSoinsService);
        this.restFeuilleSoinsMockMvc = MockMvcBuilders.standaloneSetup(feuilleSoinsResource)
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
    public static FeuilleSoins createEntity(EntityManager em) {
        FeuilleSoins feuilleSoins = new FeuilleSoins();
        return feuilleSoins;
    }

    @Before
    public void initTest() {
        feuilleSoins = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeuilleSoins() throws Exception {
        int databaseSizeBeforeCreate = feuilleSoinsRepository.findAll().size();

        // Create the FeuilleSoins
        FeuilleSoinsDTO feuilleSoinsDTO = feuilleSoinsMapper.toDto(feuilleSoins);
        restFeuilleSoinsMockMvc.perform(post("/api/feuille-soins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feuilleSoinsDTO)))
            .andExpect(status().isCreated());

        // Validate the FeuilleSoins in the database
        List<FeuilleSoins> feuilleSoinsList = feuilleSoinsRepository.findAll();
        assertThat(feuilleSoinsList).hasSize(databaseSizeBeforeCreate + 1);
        FeuilleSoins testFeuilleSoins = feuilleSoinsList.get(feuilleSoinsList.size() - 1);
    }

    @Test
    @Transactional
    public void createFeuilleSoinsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feuilleSoinsRepository.findAll().size();

        // Create the FeuilleSoins with an existing ID
        feuilleSoins.setId(1L);
        FeuilleSoinsDTO feuilleSoinsDTO = feuilleSoinsMapper.toDto(feuilleSoins);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeuilleSoinsMockMvc.perform(post("/api/feuille-soins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feuilleSoinsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FeuilleSoins in the database
        List<FeuilleSoins> feuilleSoinsList = feuilleSoinsRepository.findAll();
        assertThat(feuilleSoinsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFeuilleSoins() throws Exception {
        // Initialize the database
        feuilleSoinsRepository.saveAndFlush(feuilleSoins);

        // Get all the feuilleSoinsList
        restFeuilleSoinsMockMvc.perform(get("/api/feuille-soins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feuilleSoins.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFeuilleSoins() throws Exception {
        // Initialize the database
        feuilleSoinsRepository.saveAndFlush(feuilleSoins);

        // Get the feuilleSoins
        restFeuilleSoinsMockMvc.perform(get("/api/feuille-soins/{id}", feuilleSoins.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feuilleSoins.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFeuilleSoins() throws Exception {
        // Get the feuilleSoins
        restFeuilleSoinsMockMvc.perform(get("/api/feuille-soins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeuilleSoins() throws Exception {
        // Initialize the database
        feuilleSoinsRepository.saveAndFlush(feuilleSoins);

        int databaseSizeBeforeUpdate = feuilleSoinsRepository.findAll().size();

        // Update the feuilleSoins
        FeuilleSoins updatedFeuilleSoins = feuilleSoinsRepository.findById(feuilleSoins.getId()).get();
        // Disconnect from session so that the updates on updatedFeuilleSoins are not directly saved in db
        em.detach(updatedFeuilleSoins);
        FeuilleSoinsDTO feuilleSoinsDTO = feuilleSoinsMapper.toDto(updatedFeuilleSoins);

        restFeuilleSoinsMockMvc.perform(put("/api/feuille-soins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feuilleSoinsDTO)))
            .andExpect(status().isOk());

        // Validate the FeuilleSoins in the database
        List<FeuilleSoins> feuilleSoinsList = feuilleSoinsRepository.findAll();
        assertThat(feuilleSoinsList).hasSize(databaseSizeBeforeUpdate);
        FeuilleSoins testFeuilleSoins = feuilleSoinsList.get(feuilleSoinsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFeuilleSoins() throws Exception {
        int databaseSizeBeforeUpdate = feuilleSoinsRepository.findAll().size();

        // Create the FeuilleSoins
        FeuilleSoinsDTO feuilleSoinsDTO = feuilleSoinsMapper.toDto(feuilleSoins);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeuilleSoinsMockMvc.perform(put("/api/feuille-soins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feuilleSoinsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FeuilleSoins in the database
        List<FeuilleSoins> feuilleSoinsList = feuilleSoinsRepository.findAll();
        assertThat(feuilleSoinsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeuilleSoins() throws Exception {
        // Initialize the database
        feuilleSoinsRepository.saveAndFlush(feuilleSoins);

        int databaseSizeBeforeDelete = feuilleSoinsRepository.findAll().size();

        // Get the feuilleSoins
        restFeuilleSoinsMockMvc.perform(delete("/api/feuille-soins/{id}", feuilleSoins.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FeuilleSoins> feuilleSoinsList = feuilleSoinsRepository.findAll();
        assertThat(feuilleSoinsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeuilleSoins.class);
        FeuilleSoins feuilleSoins1 = new FeuilleSoins();
        feuilleSoins1.setId(1L);
        FeuilleSoins feuilleSoins2 = new FeuilleSoins();
        feuilleSoins2.setId(feuilleSoins1.getId());
        assertThat(feuilleSoins1).isEqualTo(feuilleSoins2);
        feuilleSoins2.setId(2L);
        assertThat(feuilleSoins1).isNotEqualTo(feuilleSoins2);
        feuilleSoins1.setId(null);
        assertThat(feuilleSoins1).isNotEqualTo(feuilleSoins2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeuilleSoinsDTO.class);
        FeuilleSoinsDTO feuilleSoinsDTO1 = new FeuilleSoinsDTO();
        feuilleSoinsDTO1.setId(1L);
        FeuilleSoinsDTO feuilleSoinsDTO2 = new FeuilleSoinsDTO();
        assertThat(feuilleSoinsDTO1).isNotEqualTo(feuilleSoinsDTO2);
        feuilleSoinsDTO2.setId(feuilleSoinsDTO1.getId());
        assertThat(feuilleSoinsDTO1).isEqualTo(feuilleSoinsDTO2);
        feuilleSoinsDTO2.setId(2L);
        assertThat(feuilleSoinsDTO1).isNotEqualTo(feuilleSoinsDTO2);
        feuilleSoinsDTO1.setId(null);
        assertThat(feuilleSoinsDTO1).isNotEqualTo(feuilleSoinsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(feuilleSoinsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(feuilleSoinsMapper.fromId(null)).isNull();
    }
}
