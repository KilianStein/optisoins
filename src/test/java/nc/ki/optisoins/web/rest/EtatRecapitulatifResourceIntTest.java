package nc.ki.optisoins.web.rest;

import nc.ki.optisoins.OptisoinsApp;

import nc.ki.optisoins.domain.EtatRecapitulatif;
import nc.ki.optisoins.repository.EtatRecapitulatifRepository;
import nc.ki.optisoins.service.EtatRecapitulatifService;
import nc.ki.optisoins.service.dto.EtatRecapitulatifDTO;
import nc.ki.optisoins.service.mapper.EtatRecapitulatifMapper;
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

import nc.ki.optisoins.domain.enumeration.TypePriseEnCharge;
/**
 * Test class for the EtatRecapitulatifResource REST controller.
 *
 * @see EtatRecapitulatifResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OptisoinsApp.class)
public class EtatRecapitulatifResourceIntTest {

    private static final TypePriseEnCharge DEFAULT_TYPE = TypePriseEnCharge.CAFAT_MUTUELLE;
    private static final TypePriseEnCharge UPDATED_TYPE = TypePriseEnCharge.LONGUE_MALADIE;

    @Autowired
    private EtatRecapitulatifRepository etatRecapitulatifRepository;

    @Autowired
    private EtatRecapitulatifMapper etatRecapitulatifMapper;
    
    @Autowired
    private EtatRecapitulatifService etatRecapitulatifService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtatRecapitulatifMockMvc;

    private EtatRecapitulatif etatRecapitulatif;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtatRecapitulatifResource etatRecapitulatifResource = new EtatRecapitulatifResource(etatRecapitulatifService);
        this.restEtatRecapitulatifMockMvc = MockMvcBuilders.standaloneSetup(etatRecapitulatifResource)
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
    public static EtatRecapitulatif createEntity(EntityManager em) {
        EtatRecapitulatif etatRecapitulatif = new EtatRecapitulatif()
            .type(DEFAULT_TYPE);
        return etatRecapitulatif;
    }

    @Before
    public void initTest() {
        etatRecapitulatif = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtatRecapitulatif() throws Exception {
        int databaseSizeBeforeCreate = etatRecapitulatifRepository.findAll().size();

        // Create the EtatRecapitulatif
        EtatRecapitulatifDTO etatRecapitulatifDTO = etatRecapitulatifMapper.toDto(etatRecapitulatif);
        restEtatRecapitulatifMockMvc.perform(post("/api/etat-recapitulatifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etatRecapitulatifDTO)))
            .andExpect(status().isCreated());

        // Validate the EtatRecapitulatif in the database
        List<EtatRecapitulatif> etatRecapitulatifList = etatRecapitulatifRepository.findAll();
        assertThat(etatRecapitulatifList).hasSize(databaseSizeBeforeCreate + 1);
        EtatRecapitulatif testEtatRecapitulatif = etatRecapitulatifList.get(etatRecapitulatifList.size() - 1);
        assertThat(testEtatRecapitulatif.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createEtatRecapitulatifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatRecapitulatifRepository.findAll().size();

        // Create the EtatRecapitulatif with an existing ID
        etatRecapitulatif.setId(1L);
        EtatRecapitulatifDTO etatRecapitulatifDTO = etatRecapitulatifMapper.toDto(etatRecapitulatif);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatRecapitulatifMockMvc.perform(post("/api/etat-recapitulatifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etatRecapitulatifDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EtatRecapitulatif in the database
        List<EtatRecapitulatif> etatRecapitulatifList = etatRecapitulatifRepository.findAll();
        assertThat(etatRecapitulatifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEtatRecapitulatifs() throws Exception {
        // Initialize the database
        etatRecapitulatifRepository.saveAndFlush(etatRecapitulatif);

        // Get all the etatRecapitulatifList
        restEtatRecapitulatifMockMvc.perform(get("/api/etat-recapitulatifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatRecapitulatif.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getEtatRecapitulatif() throws Exception {
        // Initialize the database
        etatRecapitulatifRepository.saveAndFlush(etatRecapitulatif);

        // Get the etatRecapitulatif
        restEtatRecapitulatifMockMvc.perform(get("/api/etat-recapitulatifs/{id}", etatRecapitulatif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etatRecapitulatif.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtatRecapitulatif() throws Exception {
        // Get the etatRecapitulatif
        restEtatRecapitulatifMockMvc.perform(get("/api/etat-recapitulatifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtatRecapitulatif() throws Exception {
        // Initialize the database
        etatRecapitulatifRepository.saveAndFlush(etatRecapitulatif);

        int databaseSizeBeforeUpdate = etatRecapitulatifRepository.findAll().size();

        // Update the etatRecapitulatif
        EtatRecapitulatif updatedEtatRecapitulatif = etatRecapitulatifRepository.findById(etatRecapitulatif.getId()).get();
        // Disconnect from session so that the updates on updatedEtatRecapitulatif are not directly saved in db
        em.detach(updatedEtatRecapitulatif);
        updatedEtatRecapitulatif
            .type(UPDATED_TYPE);
        EtatRecapitulatifDTO etatRecapitulatifDTO = etatRecapitulatifMapper.toDto(updatedEtatRecapitulatif);

        restEtatRecapitulatifMockMvc.perform(put("/api/etat-recapitulatifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etatRecapitulatifDTO)))
            .andExpect(status().isOk());

        // Validate the EtatRecapitulatif in the database
        List<EtatRecapitulatif> etatRecapitulatifList = etatRecapitulatifRepository.findAll();
        assertThat(etatRecapitulatifList).hasSize(databaseSizeBeforeUpdate);
        EtatRecapitulatif testEtatRecapitulatif = etatRecapitulatifList.get(etatRecapitulatifList.size() - 1);
        assertThat(testEtatRecapitulatif.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEtatRecapitulatif() throws Exception {
        int databaseSizeBeforeUpdate = etatRecapitulatifRepository.findAll().size();

        // Create the EtatRecapitulatif
        EtatRecapitulatifDTO etatRecapitulatifDTO = etatRecapitulatifMapper.toDto(etatRecapitulatif);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatRecapitulatifMockMvc.perform(put("/api/etat-recapitulatifs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etatRecapitulatifDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EtatRecapitulatif in the database
        List<EtatRecapitulatif> etatRecapitulatifList = etatRecapitulatifRepository.findAll();
        assertThat(etatRecapitulatifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtatRecapitulatif() throws Exception {
        // Initialize the database
        etatRecapitulatifRepository.saveAndFlush(etatRecapitulatif);

        int databaseSizeBeforeDelete = etatRecapitulatifRepository.findAll().size();

        // Get the etatRecapitulatif
        restEtatRecapitulatifMockMvc.perform(delete("/api/etat-recapitulatifs/{id}", etatRecapitulatif.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EtatRecapitulatif> etatRecapitulatifList = etatRecapitulatifRepository.findAll();
        assertThat(etatRecapitulatifList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatRecapitulatif.class);
        EtatRecapitulatif etatRecapitulatif1 = new EtatRecapitulatif();
        etatRecapitulatif1.setId(1L);
        EtatRecapitulatif etatRecapitulatif2 = new EtatRecapitulatif();
        etatRecapitulatif2.setId(etatRecapitulatif1.getId());
        assertThat(etatRecapitulatif1).isEqualTo(etatRecapitulatif2);
        etatRecapitulatif2.setId(2L);
        assertThat(etatRecapitulatif1).isNotEqualTo(etatRecapitulatif2);
        etatRecapitulatif1.setId(null);
        assertThat(etatRecapitulatif1).isNotEqualTo(etatRecapitulatif2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtatRecapitulatifDTO.class);
        EtatRecapitulatifDTO etatRecapitulatifDTO1 = new EtatRecapitulatifDTO();
        etatRecapitulatifDTO1.setId(1L);
        EtatRecapitulatifDTO etatRecapitulatifDTO2 = new EtatRecapitulatifDTO();
        assertThat(etatRecapitulatifDTO1).isNotEqualTo(etatRecapitulatifDTO2);
        etatRecapitulatifDTO2.setId(etatRecapitulatifDTO1.getId());
        assertThat(etatRecapitulatifDTO1).isEqualTo(etatRecapitulatifDTO2);
        etatRecapitulatifDTO2.setId(2L);
        assertThat(etatRecapitulatifDTO1).isNotEqualTo(etatRecapitulatifDTO2);
        etatRecapitulatifDTO1.setId(null);
        assertThat(etatRecapitulatifDTO1).isNotEqualTo(etatRecapitulatifDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(etatRecapitulatifMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(etatRecapitulatifMapper.fromId(null)).isNull();
    }
}
