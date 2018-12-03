package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.CompteBancaire;
import nc.ki.optisoins.repository.CompteBancaireRepository;
import nc.ki.optisoins.service.CompteBancaireService;
import nc.ki.optisoins.service.dto.CompteBancaireDTO;
import nc.ki.optisoins.service.mapper.CompteBancaireMapper;
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
 * Test class for the CompteBancaireResource REST controller.
 *
 * @see CompteBancaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class CompteBancaireResourceIntTest {

    private static final String DEFAULT_NUMERO_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_COMPTE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_BANQUE = "BBBBBBBBBB";

    @Autowired
    private CompteBancaireRepository compteBancaireRepository;

    @Autowired
    private CompteBancaireMapper compteBancaireMapper;

    @Autowired
    private CompteBancaireService compteBancaireService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompteBancaireMockMvc;

    private CompteBancaire compteBancaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompteBancaireResource compteBancaireResource = new CompteBancaireResource(compteBancaireService);
        this.restCompteBancaireMockMvc = MockMvcBuilders.standaloneSetup(compteBancaireResource)
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
    public static CompteBancaire createEntity(EntityManager em) {
        CompteBancaire compteBancaire = new CompteBancaire()
            .numeroCompte(DEFAULT_NUMERO_COMPTE)
            .nomBanque(DEFAULT_NOM_BANQUE);
        return compteBancaire;
    }

    @Before
    public void initTest() {
        compteBancaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompteBancaire() throws Exception {
        int databaseSizeBeforeCreate = compteBancaireRepository.findAll().size();

        // Create the CompteBancaire
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);
        restCompteBancaireMockMvc.perform(post("/api/compte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteBancaireDTO)))
            .andExpect(status().isCreated());

        // Validate the CompteBancaire in the database
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeCreate + 1);
        CompteBancaire testCompteBancaire = compteBancaireList.get(compteBancaireList.size() - 1);
        assertThat(testCompteBancaire.getNumeroCompte()).isEqualTo(DEFAULT_NUMERO_COMPTE);
        assertThat(testCompteBancaire.getNomBanque()).isEqualTo(DEFAULT_NOM_BANQUE);
    }

    @Test
    @Transactional
    public void createCompteBancaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteBancaireRepository.findAll().size();

        // Create the CompteBancaire with an existing ID
        compteBancaire.setId(1L);
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteBancaireMockMvc.perform(post("/api/compte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteBancaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompteBancaire in the database
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroCompteIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteBancaireRepository.findAll().size();
        // set the field null
        compteBancaire.setNumeroCompte(null);

        // Create the CompteBancaire, which fails.
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        restCompteBancaireMockMvc.perform(post("/api/compte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteBancaireDTO)))
            .andExpect(status().isBadRequest());

        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomBanqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = compteBancaireRepository.findAll().size();
        // set the field null
        compteBancaire.setNomBanque(null);

        // Create the CompteBancaire, which fails.
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        restCompteBancaireMockMvc.perform(post("/api/compte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteBancaireDTO)))
            .andExpect(status().isBadRequest());

        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompteBancaires() throws Exception {
        // Initialize the database
        compteBancaireRepository.saveAndFlush(compteBancaire);

        // Get all the compteBancaireList
        restCompteBancaireMockMvc.perform(get("/api/compte-bancaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compteBancaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCompte").value(hasItem(DEFAULT_NUMERO_COMPTE.toString())))
            .andExpect(jsonPath("$.[*].nomBanque").value(hasItem(DEFAULT_NOM_BANQUE.toString())));
    }
    
    @Test
    @Transactional
    public void getCompteBancaire() throws Exception {
        // Initialize the database
        compteBancaireRepository.saveAndFlush(compteBancaire);

        // Get the compteBancaire
        restCompteBancaireMockMvc.perform(get("/api/compte-bancaires/{id}", compteBancaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compteBancaire.getId().intValue()))
            .andExpect(jsonPath("$.numeroCompte").value(DEFAULT_NUMERO_COMPTE.toString()))
            .andExpect(jsonPath("$.nomBanque").value(DEFAULT_NOM_BANQUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompteBancaire() throws Exception {
        // Get the compteBancaire
        restCompteBancaireMockMvc.perform(get("/api/compte-bancaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompteBancaire() throws Exception {
        // Initialize the database
        compteBancaireRepository.saveAndFlush(compteBancaire);

        int databaseSizeBeforeUpdate = compteBancaireRepository.findAll().size();

        // Update the compteBancaire
        CompteBancaire updatedCompteBancaire = compteBancaireRepository.findById(compteBancaire.getId()).get();
        // Disconnect from session so that the updates on updatedCompteBancaire are not directly saved in db
        em.detach(updatedCompteBancaire);
        updatedCompteBancaire
            .numeroCompte(UPDATED_NUMERO_COMPTE)
            .nomBanque(UPDATED_NOM_BANQUE);
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(updatedCompteBancaire);

        restCompteBancaireMockMvc.perform(put("/api/compte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteBancaireDTO)))
            .andExpect(status().isOk());

        // Validate the CompteBancaire in the database
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeUpdate);
        CompteBancaire testCompteBancaire = compteBancaireList.get(compteBancaireList.size() - 1);
        assertThat(testCompteBancaire.getNumeroCompte()).isEqualTo(UPDATED_NUMERO_COMPTE);
        assertThat(testCompteBancaire.getNomBanque()).isEqualTo(UPDATED_NOM_BANQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompteBancaire() throws Exception {
        int databaseSizeBeforeUpdate = compteBancaireRepository.findAll().size();

        // Create the CompteBancaire
        CompteBancaireDTO compteBancaireDTO = compteBancaireMapper.toDto(compteBancaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteBancaireMockMvc.perform(put("/api/compte-bancaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compteBancaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompteBancaire in the database
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompteBancaire() throws Exception {
        // Initialize the database
        compteBancaireRepository.saveAndFlush(compteBancaire);

        int databaseSizeBeforeDelete = compteBancaireRepository.findAll().size();

        // Get the compteBancaire
        restCompteBancaireMockMvc.perform(delete("/api/compte-bancaires/{id}", compteBancaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompteBancaire> compteBancaireList = compteBancaireRepository.findAll();
        assertThat(compteBancaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteBancaire.class);
        CompteBancaire compteBancaire1 = new CompteBancaire();
        compteBancaire1.setId(1L);
        CompteBancaire compteBancaire2 = new CompteBancaire();
        compteBancaire2.setId(compteBancaire1.getId());
        assertThat(compteBancaire1).isEqualTo(compteBancaire2);
        compteBancaire2.setId(2L);
        assertThat(compteBancaire1).isNotEqualTo(compteBancaire2);
        compteBancaire1.setId(null);
        assertThat(compteBancaire1).isNotEqualTo(compteBancaire2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteBancaireDTO.class);
        CompteBancaireDTO compteBancaireDTO1 = new CompteBancaireDTO();
        compteBancaireDTO1.setId(1L);
        CompteBancaireDTO compteBancaireDTO2 = new CompteBancaireDTO();
        assertThat(compteBancaireDTO1).isNotEqualTo(compteBancaireDTO2);
        compteBancaireDTO2.setId(compteBancaireDTO1.getId());
        assertThat(compteBancaireDTO1).isEqualTo(compteBancaireDTO2);
        compteBancaireDTO2.setId(2L);
        assertThat(compteBancaireDTO1).isNotEqualTo(compteBancaireDTO2);
        compteBancaireDTO1.setId(null);
        assertThat(compteBancaireDTO1).isNotEqualTo(compteBancaireDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(compteBancaireMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(compteBancaireMapper.fromId(null)).isNull();
    }
}
